import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import Landing from '../../components/landing/Landing';

const mockStore = configureStore([]);
const initialState = {
  auth: {
    isAuthenticated: false,
    name: '',
    role: ''
  }
};
const store = mockStore(initialState);

test('renders Landing component without crashing', () => {
  render(
    <Provider store={store}>
      <MemoryRouter>
        <Landing />
      </MemoryRouter>
    </Provider>
  );
});

test('renders carousel with images and indicators', () => {
  render(
    <Provider store={store}>
      <MemoryRouter>
        <Landing />
      </MemoryRouter>
    </Provider>
  );
  const indicators = screen.getAllByRole('button');
  const images = screen.getAllByRole('img');
  expect(indicators.length).toBe(3);
  expect(images.length).toBe(3);
});

test('navigates to register page on "Get Started" click', () => {
  const { container } = render(
    <Provider store={store}>
      <MemoryRouter>
        <Landing />
      </MemoryRouter>
    </Provider>
  );
  const getStartedButtons = screen.getAllByText('Get Started');
  fireEvent.click(getStartedButtons[0]);
  expect(container.innerHTML).toMatch('/register');
});

test('renders buyer section content correctly', () => {
  render(
    <Provider store={store}>
      <MemoryRouter>
        <Landing />
      </MemoryRouter>
    </Provider>
  );
  const buyerHeading = screen.getByText('Explore more');
  const buyerParagraph = screen.getByText(/As a buyer, you can register on our platform/i);
  expect(buyerHeading).toBeInTheDocument();
  expect(buyerParagraph).toBeInTheDocument();
});

test('renders seller section content correctly', () => {
  render(
    <Provider store={store}>
      <MemoryRouter>
        <Landing />
      </MemoryRouter>
    </Provider>
  );
  const sellerHeading = screen.getByText('Become A Seller');
  const sellerParagraph = screen.getByText(/As a seller, you can register on our platform/i);
  expect(sellerHeading).toBeInTheDocument();
  expect(sellerParagraph).toBeInTheDocument();
});
