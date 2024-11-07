import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import EligibilityCongrats from '../../components/eligibility/EligibilityCongrats';

test('renders congratulatory message', () => {
  const message = "You are eligible for the insurance!";
  render(<EligibilityCongrats message={message} onClose={() => {}} />);
  const messageElement = screen.getByText(/You are eligible for the insurance!/i);
  expect(messageElement).toBeInTheDocument();
});
