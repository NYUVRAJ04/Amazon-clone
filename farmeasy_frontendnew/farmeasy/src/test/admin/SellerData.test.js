import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import SellerData from '../../components/admin/SellerData';

// Mock the fetch function
global.fetch = jest.fn();

describe('SellerData', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  test('renders loading state initially', () => {
    fetch.mockImplementation(() => new Promise(() => {})); // Never resolves

    render(<SellerData />);
    expect(screen.getByText(/Loading.../i)).toBeInTheDocument();
  });

  test('renders error state', async () => {
    fetch.mockImplementation(() =>
      Promise.reject(new Error('Network response was not ok'))
    );

    render(<SellerData />);
    const errorElement = await waitFor(() => screen.getByText(/Error:/i));
    expect(errorElement).toBeInTheDocument();
  });

  test('renders seller data table when data is fetched successfully', async () => {
    const mockSellers = [
      {
        sellerId: '1',
        user: { name: 'Jane Doe', email: 'jane@example.com' },
        businessName: 'Jane\'s Farm',
      },
      // Add more mock sellers as needed
    ];

    fetch.mockImplementation(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockSellers),
      })
    );

    render(<SellerData />);
    await waitFor(() => expect(screen.getByText(/Seller Data/i)).toBeInTheDocument());

    // Check if the table headers are rendered
    expect(screen.getByText(/Seller ID/i)).toBeInTheDocument();
    expect(screen.getByText(/Name/i)).toBeInTheDocument();
    expect(screen.getByText(/Email/i)).toBeInTheDocument();
    expect(screen.getByText(/Business Name/i)).toBeInTheDocument();

    // Check if the seller data is rendered
    expect(screen.getByText('1')).toBeInTheDocument();
    expect(screen.getByText('Jane Doe')).toBeInTheDocument();
    expect(screen.getByText('jane@example.com')).toBeInTheDocument();
    expect(screen.getByText('Jane\'s Farm')).toBeInTheDocument();
  });
});
