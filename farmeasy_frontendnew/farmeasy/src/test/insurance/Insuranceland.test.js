import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import InsuranceLand from '../../components/insurance/Insuranceland';

test('renders apply button and handles click', () => {
  const handleApply = jest.fn();
  
  render(
    <MemoryRouter>
      <InsuranceLand title="Insurance Plan" description="Best insurance plan for you" onApply={handleApply} />
    </MemoryRouter>
  );

  // Check if the button is rendered
  const buttonElement = screen.getByRole('button', { name: /Apply Now/i });
  expect(buttonElement).toBeInTheDocument();

  // Simulate a click event
  fireEvent.click(buttonElement);

  // Check if the click handler was called
  expect(handleApply).toHaveBeenCalledTimes(1);
});
