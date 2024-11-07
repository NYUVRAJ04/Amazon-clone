import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios"; // Import Axios
import "../../css/cart/rentcheckout.css";

function RentCheckout() {
  const cartItems = useSelector((state) => state.cart.items);
  const [durations, setDurations] = useState({}); // Store durations in an object
  const navigate = useNavigate();

  const handleDurationChange = (e, itemId) => {
    setDurations({
      ...durations,
      [itemId]: e.target.value,
    });
  };

  const calculateTotalRentalPrice = () => {
    return cartItems.reduce((total, item) => {
      const duration = durations[item.uniqueId] || 1; // Default to 1 month if not set
      return total + item.rentalPrice * item.quantity * duration;
    }, 0);
  };

  const buyer = useSelector((state) => state.auth.buyerId);
  const handlePlaceOrder = async () => {
    // Prepare the order data
    const orderData = {
      buyer: {
        buyerId: buyer, // Update this with the actual buyer ID
      },
      productName: cartItems.map((item) => item.name).join(", "), // Combine product names
      quantity: cartItems.reduce((total, item) => total + item.quantity, 0),
      totalPrice: calculateTotalRentalPrice(),
      purchaseType: "Rent", // Set appropriate purchase type here
      rentalDurations: cartItems.map((item) => ({
        productId: item.uniqueId,
        duration: durations[item.uniqueId] || 1,
      })),
    };

    try {
      const response = await axios.post(
        "http://localhost:9000/orders/placeorder",
        orderData
      ); // Replace with your actual API endpoint
      console.log(response.data); // Handle the response data as needed
      alert("Order placed successfully!");
      navigate("/"); // Redirect after placing the order
    } catch (error) {
      console.error("Error placing order:", error);
      alert("Failed to place the order. Please try again.");
    }
  };

  const handleGoBackToCart = () => {
    navigate("/cart");
  };

  return (
    <div className="rent-checkout-container">
      <h2>Checkout</h2>
      <div className="rent-checkout-details">
        <table className="rent-checkout-table">
          <thead>
            <tr>
              <th>Product Name</th>
              <th>Quantity</th>
              <th>Rent/Month</th>
              <th>Rental Duration (Months)</th>
              <th>Total Rental Price</th>
            </tr>
          </thead>
          <tbody>
            {cartItems.map((item) => (
              <tr key={item.uniqueId}>
                <td>{item.name}</td>
                <td>{item.quantity}</td>
                <td>{item.rentalPrice}</td>
                <td>
                  <input
                    type="number"
                    value={durations[item.uniqueId] || 1}
                    onChange={(e) => handleDurationChange(e, item.uniqueId)}
                    min="1"
                  />
                </td>
                <td>
                  {item.rentalPrice *
                    item.quantity *
                    (durations[item.uniqueId] || 1)}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="rent-checkout-summary">
          <p>Total Rental Price: {calculateTotalRentalPrice()}</p>
          <button className="btn btn-secondary" onClick={handleGoBackToCart}>
            Go Back to Cart
          </button>
          <button className="btn btn-primary" onClick={handlePlaceOrder}>
            Place Your Order
          </button>
        </div>
      </div>
    </div>
  );
}

export default RentCheckout;
