import { createSlice } from "@reduxjs/toolkit";
import { v4 as uuidv4 } from "uuid";

const initialState = {
  items: [],
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart: (state, action) => {
      const existingItem = state.items.find(
        (item) => item.name === action.payload.name
      );
      if (existingItem) {
        existingItem.quantity += action.payload.quantity; // Increment quantity if item already exists
      } else {
        state.items.push({
          ...action.payload,
          uniqueId: uuidv4(), // Assign a unique ID to each new item
          rentalDuration: action.payload.rentalDuration || 0, // Default rental duration to 1 if not provided
        });
      }
    },
    removeFromCart: (state, action) => {
      state.items = state.items.filter(
        (item) => item.uniqueId !== action.payload.uniqueId
      ); // Remove item by unique ID
    },
    clearCart: (state) => {
      state.items = []; // Clear the cart
    },
    updateRentalDuration: (state, action) => {
      const item = state.items.find(
        (item) => item.uniqueId === action.payload.uniqueId
      );
      if (item) {
        item.rentalDuration = action.payload.duration;
      }
    },
  },
});

export const { addToCart, removeFromCart, clearCart, updateRentalDuration } =
  cartSlice.actions;
export default cartSlice.reducer;
