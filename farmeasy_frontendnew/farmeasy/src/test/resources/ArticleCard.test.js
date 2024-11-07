const React = require('react');
const { render, screen, fireEvent } = require('@testing-library/react');
const { MemoryRouter } = require('react-router-dom');
require('@testing-library/jest-dom');
const ArticleCard = require('../../components/resources/ArticleCard').default;

const mockArticle = {
  title: 'Sample Article',
  description: 'This is a sample article description.',
  fileUrl: 'http://example.com/sample.pdf',
  downloadable: true
};

describe('ArticleCard Component', () => {
  test('renders ArticleCard component without crashing', () => {
    render(
      <MemoryRouter>
        <ArticleCard article={mockArticle} />
      </MemoryRouter>
    );
  });

  test('renders article title and description', () => {
    render(
      <MemoryRouter>
        <ArticleCard article={mockArticle} />
      </MemoryRouter>
    );
    expect(screen.getByText(mockArticle.title)).toBeInTheDocument();
    expect(screen.getByText(mockArticle.description)).toBeInTheDocument();
  });

  test('handles download button click', () => {
    window.open = jest.fn();
    render(
      <MemoryRouter>
        <ArticleCard article={mockArticle} />
      </MemoryRouter>
    );
    const downloadButton = screen.getByRole('button', { name: /Download/i });
    fireEvent.click(downloadButton);
    expect(window.open).toHaveBeenCalledWith(mockArticle.fileUrl, '_blank');
  });

  test('handles learn more button click', () => {
    const mockNavigate = jest.fn();
    jest.mock('react-router-dom', () => ({
      ...jest.requireActual('react-router-dom'),
      useNavigate: () => mockNavigate
    }));
    render(
      <MemoryRouter>
        <ArticleCard article={mockArticle} />
      </MemoryRouter>
    );
    const learnMoreButton = screen.getByRole('button', { name: /Learn More/i });
    fireEvent.click(learnMoreButton);
    expect(mockNavigate).toHaveBeenCalledWith('/resourcespage', { state: { article: mockArticle } });
  });
});
