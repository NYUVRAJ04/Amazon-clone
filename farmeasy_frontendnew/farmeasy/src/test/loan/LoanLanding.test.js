import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import '@testing-library/jest-dom';
import LoanLanding from '../../components/loan/LoanLanding';

const mockStore = configureStore([]);
const initialState = {
  auth: {
    isAuthenticated: false,
    name: '',
    role: ''
  }
};
const store = mockStore(initialState);

describe('LoanLanding Component', () => {
  test('renders LoanLanding component without crashing', () => {
    render(
      <Provider store={store}>
        <MemoryRouter>
          <LoanLanding />
        </MemoryRouter>
      </Provider>
    );
  });

  test('renders Check Loan Eligibility button and handles click', () => {
    render(
      <Provider store={store}>
        <MemoryRouter>
          <LoanLanding />
        </MemoryRouter>
      </Provider>
    );
    const buttonElement = screen.getByText(/Check Loan Eligibility/i);
    expect(buttonElement).toBeInTheDocument();
    fireEvent.click(buttonElement);
    // You can add more assertions here if needed
  });

  test('renders loan sections content correctly', () => {
    render(
      <Provider store={store}>
        <MemoryRouter>
          <LoanLanding />
        </MemoryRouter>
      </Provider>
    );
    const headings = [
      'Why Agricultural Loans are Essential?',
      'Documents Required for Loan Eligibility',
      'Minimum Eligibility Criteria for Loan'
    ];
    headings.forEach(heading => {
      expect(screen.getByText(heading)).toBeInTheDocument();
    });
  });

  test('renders specific content in loan sections correctly', () => {
    render(
      <Provider store={store}>
        <MemoryRouter>
          <LoanLanding />
        </MemoryRouter>
      </Provider>
    );
    const content = [
      'Investment in Modern Equipment:',
      'Access to Quality Seeds and Fertilizers:',
      'Expanding Farmland and Infrastructure:',
      'Supporting Livestock and Dairy Farming:',
      'Building Resilience Against Market Fluctuations:',
      'Emergency Funding for Crop Loss:'
    ];
    content.forEach(text => {
      expect(screen.getByText(text)).toBeInTheDocument();
    });
  });
});
