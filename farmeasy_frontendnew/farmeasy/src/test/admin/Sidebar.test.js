import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import Sidebar from '../../components/admin/Sidebar';


test('renders FarmEasy title', () => {
  render(
    <Router>
    
        <Sidebar />
     
    </Router>
  );
  const titleElement = screen.getByText(/FarmEasy/i);
  expect(titleElement).toBeInTheDocument();
});
