import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import BuyerData from '../../components/admin/BuyerData';

// Mock the fetch function
global.fetch = jest.fn();

describe('BuyerData', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  test('renders loading state', () => {
    fetch.mockImplementation(() => new Promise(() => {})); // Never resolves

    render(<BuyerData />);
    expect(screen.getByText(/Loading.../i)).toBeInTheDocument();
  });

  test('renders error state', async () => {
    fetch.mockImplementation(() =>
      Promise.reject(new Error('Network response was not ok'))
    );

    render(<BuyerData />);
    const errorElement = await waitFor(() => screen.getByText(/Error:/i));
    expect(errorElement).toBeInTheDocument();
  });

  test('renders buyer data table when data is fetched successfully', async () => {
    const mockBuyers = [
      {
        buyerId: '1',
        user: { name: 'John Doe', email: 'john@example.com' },
        annualIncome: 50000,
        creditScore: 700,
        landArea: 100,
        yearsInFarming: 5,
      },
      // Add more mock buyers as needed
    ];

    fetch.mockImplementation(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockBuyers),
      })
    );

    render(<BuyerData />);
    await waitFor(() => expect(screen.getByText(/Buyer Data/i)).toBeInTheDocument());

    // Check if the table headers are rendered
    expect(screen.getByText(/Buyer ID/i)).toBeInTheDocument();
    expect(screen.getByText(/Name/i)).toBeInTheDocument();
    expect(screen.getByText(/Email/i)).toBeInTheDocument();
    expect(screen.getByText(/Annual Income/i)).toBeInTheDocument();
    expect(screen.getByText(/Credit Score/i)).toBeInTheDocument();
    expect(screen.getByText(/Land Area/i)).toBeInTheDocument();
    expect(screen.getByText(/Years in Farming/i)).toBeInTheDocument();

    // Check if the buyer data is rendered
    expect(screen.getByText('1')).toBeInTheDocument();
    expect(screen.getByText('John Doe')).toBeInTheDocument();
    expect(screen.getByText('john@example.com')).toBeInTheDocument();
    expect(screen.getByText('50000')).toBeInTheDocument();
    expect(screen.getByText('700')).toBeInTheDocument();
    expect(screen.getByText('100')).toBeInTheDocument();
    expect(screen.getByText('5')).toBeInTheDocument();
  });
});
