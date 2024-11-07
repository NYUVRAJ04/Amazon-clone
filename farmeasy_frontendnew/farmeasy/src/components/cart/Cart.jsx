import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { addToCart, removeFromCart } from "../../redux/cartSlice";
import { useNavigate } from "react-router-dom";
import { FaArrowLeft, FaHome } from "react-icons/fa";
import "../../css/cart/cart.css";

function Cart() {
  const dispatch = useDispatch();
  const cartItems = useSelector((state) => state.cart.items);
  const navigate = useNavigate();

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

  const handleBuy = () => {
    navigate("/buy");
  };

  const handleRent = () => {
    navigate("/rent");
  };

  const handleGoBack = () => {
    navigate(-1); // Go back to the previous page
  };

  const handleGoHome = () => {
    navigate("/"); // Navigate to the home page
  };

  return (
    <div className="cart-container">
      <div className="cart-header">
        <FaArrowLeft className="icon" onClick={handleGoBack} />
        <h3>Cart</h3>
        <FaHome className="icon" onClick={handleGoHome} />
      </div>
      <div className="cart-items">
        {cartItems.length === 0 ? (
          <div>No items in the cart.</div>
        ) : (
          <>
            <table className="cart-table">
              <thead>
                <tr>
                  <th>Image</th>
                  <th>Title</th>
                  <th>Price/unit</th>
                  <th>Rent/Month</th>
                  <th>Quantity</th>
                </tr>
              </thead>
              <tbody>
                {cartItems.map((item) => (
                  <tr key={item.uniqueId} className="cart-row">
                    <td data-label="Image">
                      <img
                        src={item.image}
                        alt={item.title}
                        className="cart-item-image"
                      />
                    </td>
                    <td data-label="Title">{item.name}</td>
                    <td data-label="Price">{item.sellingPrice}</td>
                    <td data-label="Rental Price">{item.rentalPrice}</td>
                    <td data-label="Quantity" className="cart-quantity">
                      <button onClick={() => handleDecrement(item)}>-</button>
                      <span>{item.quantity}</span>
                      <button onClick={() => handleIncrement(item)}>+</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <div className="cart-actions">
              <button className="btn btn-primary" onClick={handleBuy}>
                Buy
              </button>
              <button className="btn btn-secondary" onClick={handleRent}>
                Rent
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default Cart;
