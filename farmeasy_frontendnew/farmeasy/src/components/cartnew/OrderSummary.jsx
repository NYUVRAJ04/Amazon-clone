import React from "react";
import { useLocation, Link } from "react-router-dom";
import "../../css/cart/ordersummary.css";

function OrderSummary() {
  const location = useLocation();
  const { orderSummary } = location.state;

  if (!orderSummary) {
    return <p>No order summary available.</p>;
  }

  return (
    <div className="order-summary">
      <h2>Order Summary</h2>
      <p>Buyer ID: {orderSummary.buyerId}</p>
      <p>Payment ID: {orderSummary.paymentId}</p>
      <p>Total Price: ₹{orderSummary.totalPrice}</p>
      <p>Order Type: {orderSummary.orderType}</p>
      <h3>Items:</h3>
      <ul>
        {orderSummary.items.map((item) => (
          <li key={item.uniqueId}>
            <span className="item-details">{item.name}</span>
            <span className="item-quantity">Quantity: {item.quantity}</span>
            <span className="item-duration">
              Rental Duration: {item.rentalDuration} months
            </span>
           
          </li>
        ))}
      </ul>
      <div className="total">Total Price: ₹{orderSummary.totalPrice}</div>
      <div className="back-to-home">
        <Link to="/">Go back to Home</Link>
      </div>
    </div>
  );
}

export default OrderSummary;
