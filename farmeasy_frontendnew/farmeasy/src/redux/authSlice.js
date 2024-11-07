import { createSlice } from "@reduxjs/toolkit";

// Retrieve auth data from localStorage if available
const initialState = {
  token: localStorage.getItem("token") || null,
  name: localStorage.getItem("name") || null,
  role: localStorage.getItem("role") || null,
  buyerId: localStorage.getItem("buyerId")
    ? parseInt(localStorage.getItem("buyerId"))
    : null,
  sellerId: localStorage.getItem("sellerId")
    ? parseInt(localStorage.getItem("sellerId"))
    : null,
  isAuthenticated: localStorage.getItem("token") ? true : false, // Determine if user is authenticated based on token
  message: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    // Action to set authentication data
    setAuth: (state, action) => {
      const { token, name, role, buyerId, sellerId } = action.payload;
      state.token = token;
      state.name = name;
      state.role = role;
      state.buyerId = buyerId;
      state.sellerId = sellerId;
      state.isAuthenticated = true; // Set authenticated state to true
      state.message = null;

      // Persist data to localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("name", name);
      localStorage.setItem("role", role);
      if (buyerId) localStorage.setItem("buyerId", buyerId);
      if (sellerId) localStorage.setItem("sellerId", sellerId);
    },
    // Action to set an error message
    setAuthError: (state, action) => {
      state.message = action.payload;
      state.isAuthenticated = false; // Set authenticated state to false if there is an error
    },
    // Action to clear authentication data (logout)
    clearAuth: (state) => {
      state.token = null;
      state.name = null;
      state.role = null;
      state.buyerId = null;
      state.sellerId = null;
      state.isAuthenticated = false; // Set authenticated state to false on logout
      state.message = null;

      // Remove data from localStorage
      localStorage.removeItem("token");
      localStorage.removeItem("name");
      localStorage.removeItem("role");
      localStorage.removeItem("buyerId");
      localStorage.removeItem("sellerId");
    },
  },
});

export const { setAuth, setAuthError, clearAuth } = authSlice.actions;

export default authSlice.reducer;
