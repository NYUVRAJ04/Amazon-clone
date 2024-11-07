import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import ContactUs from '../../components/landing/Contactus';

test('renders initial form fields', () => {
  render(<ContactUs />);
  const nameField = screen.getByPlaceholderText(/Your Name/i);
  const emailField = screen.getByPlaceholderText(/Your Email/i);
  const phoneField = screen.getByPlaceholderText(/Your Phone Number/i);
  const messageField = screen.getByPlaceholderText(/Write your message here/i);

  expect(nameField).toBeInTheDocument();
  expect(emailField).toBeInTheDocument();
  expect(phoneField).toBeInTheDocument();
  expect(messageField).toBeInTheDocument();
});
