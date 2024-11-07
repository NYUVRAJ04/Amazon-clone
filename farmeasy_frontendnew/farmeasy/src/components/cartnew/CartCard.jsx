import React from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  addToCart,
  removeFromCart,
  updateRentalDuration,
} from "../../redux/cartSlice"; // Adjust the import path based on your setup
import "../../css/cart/cartcard.css";

function CartCard({ selectedPriceType }) {
  const dispatch = useDispatch();
  const cartItems = useSelector((state) => state.cart.items);

  const handleIncrement = (item) => {
    dispatch(addToCart({ ...item, quantity: 1 }));
  };

  const handleDecrement = (item) => {
    if (item.quantity > 1) {
      dispatch(addToCart({ ...item, quantity: -1 }));
    } else {
      dispatch(removeFromCart({ uniqueId: item.uniqueId }));
    }
  };

  const handleDurationChange = (item, duration) => {
    const updatedDuration = selectedPriceType === "selling" ? 0 : duration;
    dispatch(
      updateRentalDuration({
        uniqueId: item.uniqueId,
        duration: updatedDuration,
      })
    );
  };

  return (
    <div className="cart-card-container">
      {cartItems.length > 0 ? (
        cartItems.map((item) => {
          // Calculate total prices
          const totalRentalPrice =
            item.rentalPrice * item.quantity * item.rentalDuration;
          const totalSellingPrice = item.sellingPrice * item.quantity;

          return (
            <div className="cart-card row" key={item.uniqueId}>
              <div className="col-12 col-md-3">
                <img
                  src={item.image}
                  alt={item.name}
                  className="cart-item-image"
                />
              </div>

              <div className="col-12 col-md-3 cart-card-content">
                <h3 className="cart-card-title">{item.name}</h3>
                <p> {item.description}</p>
              </div>

              <div className="col-12 col-md-2 quantity-controls">
                <label className="quantity-label">Quantity</label>
                <div className="quantity-buttons">
                  <button onClick={() => handleDecrement(item)}>-</button>
                  <span>{item.quantity}</span>
                  <button onClick={() => handleIncrement(item)}>+</button>
                </div>
              </div>
              <div className="col-12 col-md-2 rent-duration">
                <label className="quantity-label">Rent Duration (days)</label>
                <input
                  type="number"
                  value={
                    selectedPriceType === "selling" ? 0 : item.rentalDuration
                  }
                  onChange={(e) => handleDurationChange(item, e.target.value)}
                  min="1"
                  className="duration-input"
                  disabled={selectedPriceType === "selling"}
                />
              </div>

              <div className="col-12 col-md-2 price-details">
                <label className="quantity-label">Price</label>
                <p className="cart-card-price">
                  <strong>Rental Price:</strong> ₹{totalRentalPrice}
                </p>
                <p className="cart-card-price">
                  <strong>Selling Price:</strong> ₹{totalSellingPrice}
                </p>
              </div>
            </div>
          );
        })
      ) : (
        <p>Your cart is empty.</p>
      )}
    </div>
  );
}

export default CartCard;
