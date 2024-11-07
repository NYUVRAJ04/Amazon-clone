import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { BrowserRouter as Router } from "react-router-dom";
import Header from "../../components/header/Header";
import { Provider } from "react-redux";
import configureStore from "redux-mock-store";
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import { Provider } from "react-redux";
import { BrowserRouter as Router } from "react-router-dom";
import configureStore from "redux-mock-store";
import Header from "../header/Header"; // Adjust the import path as necessary

const mockStore = configureStore([]);

describe("Header Component", () => {
  let store;

  beforeEach(() => {
    store = mockStore({
      auth: {
        isAuthenticated: false,
        name: "",
        role: "",
      },
    });
  });

  test("renders without crashing", () => {
    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );
    expect(screen.getByText("FarmEasy")).toBeInTheDocument();
  });

  test("shows login and register links when not authenticated", () => {
    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );
    expect(screen.getByText("Login")).toBeInTheDocument();
    expect(screen.getByText("Register")).toBeInTheDocument();
  });

  test("shows user dropdown when authenticated as buyer", () => {
    store = mockStore({
      auth: {
        isAuthenticated: true,
        name: "John Doe",
        role: "buyer",
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );

    expect(screen.getByText("John Doe")).toBeInTheDocument();
    expect(screen.getByText("My Cart")).toBeInTheDocument();
    expect(screen.getByText("My Orders")).toBeInTheDocument();
  });

  test("shows buyer-specific links when authenticated as buyer", () => {
    store = mockStore({
      auth: {
        isAuthenticated: true,
        name: "John Doe",
        role: "buyer",
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );

    expect(screen.getByText("Resources")).toBeInTheDocument();
    expect(screen.getByText("Products")).toBeInTheDocument();
    expect(screen.getByText("Insurance Eligibility")).toBeInTheDocument();
    expect(screen.getByText("Loan Eligibility")).toBeInTheDocument();
  });

  test("navigates to buyer home on home click when authenticated as buyer", () => {
    store = mockStore({
      auth: {
        isAuthenticated: true,
        name: "John Doe",
        role: "buyer",
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );

    fireEvent.click(screen.getByText("FarmEasy"));
    expect(screen.getByText("FarmEasy")).toHaveAttribute("href", "/buyer/home");
  });

  test("navigates to seller home on home click when authenticated as seller", () => {
    store = mockStore({
      auth: {
        isAuthenticated: true,
        name: "Jane Doe",
        role: "seller",
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );

    fireEvent.click(screen.getByText("FarmEasy"));
    expect(screen.getByText("FarmEasy")).toHaveAttribute(
      "href",
      "/seller/home"
    );
  });

  test("logs out and navigates to home on logout click", () => {
    store = mockStore({
      auth: {
        isAuthenticated: true,
        name: "John Doe",
        role: "buyer",
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );

    fireEvent.click(screen.getByText("Logout"));
    expect(store.getActions()).toContainEqual(clearAuth());
    expect(screen.getByText("FarmEasy")).toHaveAttribute("href", "/");
  });
});

// const mockStore = configureStore([]);

test("renders FarmEasy brand name", () => {
  const store = mockStore({
    auth: { isAuthenticated: false, name: "", role: "" },
  });

  render(
    <Provider store={store}>
      <Router>
        <Header />
      </Router>
    </Provider>
  );
  const brandElement = screen.getByText(/FarmEasy/i);
  expect(brandElement).toBeInTheDocument();
});
