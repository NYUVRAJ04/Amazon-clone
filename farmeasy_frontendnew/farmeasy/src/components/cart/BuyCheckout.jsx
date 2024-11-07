import React from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios"; // Import Axios
import "../../css/cart/buycheckout.css";

function BuyCheckout() {
  const cartItems = useSelector((state) => state.cart.items);
  const navigate = useNavigate();

  const calculateTotalPrice = () => {
    return cartItems.reduce((total, item) => {
      return total + item.purchasePrice * item.quantity;
    }, 0);
  };

  const buyer = useSelector((state) => state.auth.buyerId);

  console.log(cartItems[0].purchasePrice);
  const handlePlaceOrder = async () => {
    // Prepare the order data
    const orderData = {
      buyer: {
        buyerId: buyer, // Update this with the actual buyer ID
      },
      productName: cartItems.map((item) => item.name).join(", "), // Combine product names
      quantity: cartItems.reduce((total, item) => total + item.quantity, 0),
      totalPrice: calculateTotalPrice(),
      purchaseType: "Buy", // Set appropriate purchase type here
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

  const handlePayClick = (ride) => {
    const options = {
      key: "rzp_test_vv1FCZvuDRF6lQ",
      amount: Math.round(ride * 100),
      currency: "INR",
      name: "FarmEasy",
      description: "One Stop Solution For Farmers",
      handler: async function (response) {
        const paymentId = response.razorpay_payment_id;
        console.log("Payment successful with ID:", paymentId);
        // try {
        //   const updateResponse = await axios.put(
        //     `${PENDINGPAYMENTS_ENDPOINTS.UPDATE_RIDE_STATUS}${ride.requestId}`,
        //     { status: "COMPLETED" },
        //     { headers: { Authorization: `Bearer ${token}` } }
        //   );
        //   console.log("Update response:", updateResponse);
        //   fetchPayementDetails();
        //   Swal.fire(
        //     PENDINGPAYMENTS_MESSAGES.SUCCESS,
        //     PENDINGPAYMENTS_MESSAGES.PAYMENT_SUCCESS,
        //     "success"
        //   );
        //   const data = {
        //     paymentId: paymentId,
        //     bookingId: ride.requestId,
        //     customerEmail: email,
        //     transporterEmail: ride.transporterInfo.email,
        //     totalPrice: ride.totalPrice,
        //     paymentStatus: "SUCCESS",
        //   };
        //   console.log("Data to be saved:", data);
        //   const response = await axios.post(
        //     PENDINGPAYMENTS_ENDPOINTS.SAVE_PAYMENT,
        //     data,
        //     {
        //       headers: { Authorization: `Bearer ${token}` },
        //     }
        //   );
        //   console.log(response);
        // } catch (error) {
        //   console.error(PENDINGPAYMENTS_MESSAGES.ERROR_UPDATING_STATUS, error);
        //   Swal.fire(
        //     PENDINGPAYMENTS_MESSAGES.ERROR,
        //     PENDINGPAYMENTS_MESSAGES.RECORD_PAYMENT_ERROR,
        //     "error"
        //   );
        // }
      },
      theme: {
        color: "gold",
      },
    };
    const pay = new window.Razorpay(options);
    pay.open();
  };
  return (
    <div className="buy-checkout-container">
      <h2>Checkout</h2>
      <div className="buy-checkout-details">
        <table className="buy-checkout-table">
          <thead>
            <tr>
              <th>Product Name</th>
              <th>Quantity</th>
              <th>Price</th>
              <th>Total Price</th>
            </tr>
          </thead>
          <tbody>
            {cartItems.map((item) => (
              <tr key={item.uniqueId}>
                <td>{item.name}</td>
                <td>{item.quantity}</td>
                <td>{item.purchasePrice}</td>
                <td>{calculateTotalPrice()}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="buy-checkout-summary">
          <p>Total Price: {calculateTotalPrice()}</p>
          <button className="btn btn-secondary" onClick={handleGoBackToCart}>
            Go Back to Cart
          </button>
          <button className="btn btn-primary" onClick={handlePlaceOrder}>
            Place Your Order
          </button>
          <button
            className="btn btn-primary"
            onClick={() => handlePayClick(cartItems[0].purchasePrice)}
          >
            Pay Now
          </button>
        </div>
      </div>
    </div>
  );
}

export default BuyCheckout;
