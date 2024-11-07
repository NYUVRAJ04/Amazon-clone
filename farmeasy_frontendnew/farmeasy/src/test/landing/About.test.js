import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import About from '../../components/landing/About';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';

const mockStore = configureStore([]);

test('renders About FarmEasy title', () => {
  const store = mockStore({
    auth: { isAuthenticated: false, name: '', role: '' }
  });

  render(
    <Provider store={store}>
      <Router>
        <About />
      </Router>
    </Provider>
  );
  const titleElement = screen.getByText(/About FarmEasy/i);
  expect(titleElement).toBeInTheDocument();
});
