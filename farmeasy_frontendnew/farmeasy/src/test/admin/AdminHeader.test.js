import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import AdminHeader from '../../components/admin/AdminHeader';

test('renders user icon', () => {
  render(<AdminHeader />);
  const userIcon = screen.getByTestId('fa-user-icon');
  expect(userIcon).toBeInTheDocument();
});
