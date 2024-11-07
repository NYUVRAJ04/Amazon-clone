// src/app/store.js
import { configureStore } from "@reduxjs/toolkit";
import { persistStore, persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage"; // defaults to localStorage for web
import authReducer from "../redux/authSlice";
import cartReducer from "../redux/cartSlice"

const persistConfig = {
  key: "root",
  storage,
};

const persistedReducer = persistReducer(persistConfig, authReducer);
// Create persisted reducer for cart
const persistedCartReducer = persistReducer(persistConfig, cartReducer);

export const store = configureStore({
  reducer: {
    auth: persistedReducer,
    cart: persistedCartReducer,
  },
});

export const persistor = persistStore(store);
