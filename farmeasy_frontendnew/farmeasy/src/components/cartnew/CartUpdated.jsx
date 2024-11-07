import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { FaArrowLeft, FaHome } from "react-icons/fa";
import CartCard from "./CartCard";
import { clearCart } from "../../redux/cartSlice";
import "../../css/cart/updatedcard.css";
import axios from "axios";
import { Modal, Spin } from "antd"; // Import Modal and Spin from Ant Design

function CartUpdated() {
  const cartItems = useSelector((state) => state.cart.items);
  const token = useSelector((state) => state.auth.token);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const buyerId = useSelector((state) => state.auth.buyerId);
  const [selectedPriceType, setSelectedPriceType] = useState("");
  const [isProcessing, setIsProcessing] = useState(false); // State for payment processing
  const [paymentStatus, setPaymentStatus] = useState(""); // State for payment status

  // Calculate total rental and selling prices
  const totalRentalPrice = cartItems.reduce(
    (acc, item) => acc + item.rentalPrice * item.quantity * item.rentalDuration,
    0
  );
  const totalSellingPrice = cartItems.reduce(
    (acc, item) => acc + item.sellingPrice * item.quantity,
    0
  );

  const handlePayClick = (totalPrice) => {
    const options = {
      key: "rzp_test_vv1FCZvuDRF6lQ",
      amount: Math.round(totalPrice * 100),
      currency: "INR",
      name: "FarmEasy",
      description: "One Stop Solution For Farmers",
      handler: async function (response) {
        setIsProcessing(true); // Set processing to true
        const paymentId = response.razorpay_payment_id;

        // Generate order summary
        const orderSummary = {
          items: cartItems.map((item) => ({
            ...item,
            rentalDuration:
              selectedPriceType === "selling" ? 0 : item.rentalDuration,
          })),
          totalPrice,
          paymentId,
          selectedPriceType,
          orderType: selectedPriceType === "rental" ? "Rent" : "Buy",
          buyerId,
          orderDate: new Date().toISOString(),
        };

        try {
          const res = await axios.post(
            "http://localhost:8099/auth/placeorder",
            orderSummary,
            {
              headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
              },
            }
          );

          // Check for response status
          if (res.status !== 200) {
            throw new Error(`HTTP error! status: ${res.status}`);
          }

          // Clear the cart
          dispatch(clearCart());
          // Set payment success status
          setPaymentStatus("success");
          setTimeout(() => {
            setIsProcessing(false); // Hide processing modal
            navigate("/order-summary", { state: { orderSummary } });
          }, 2000); // Delay for demonstration
        } catch (error) {
          console.error("Failed to send order summary:", error);
          setPaymentStatus("error");
          setTimeout(() => {
            setIsProcessing(false); // Hide processing modal
          }, 2000); // Delay for demonstration
        }
      },
      theme: {
        color: "gold",
      },
    };
    const pay = new window.Razorpay(options);
    pay.open();
  };

  return (
    <div className="cart-main">
      <div className="header-container">
        <FaArrowLeft className="icon" onClick={() => navigate(-1)} />
        <h3 className="app-title">FarmEasy</h3>
        <FaHome className="icon" onClick={() => navigate("/")} />
      </div>
      <div className="cart-con">
        <div className="cart-title">
          <p>Review Your Cart</p>
        </div>
        <hr />
        <div className="cart-content-main">
          <p>Free delivery and free returns</p>
          <hr />
          <div className="cart-items-main">
            <CartCard selectedPriceType={selectedPriceType} />
            <hr />
          </div>

          {/* Display Total Prices */}
          <div className="total-price-section">
            <div className="total-prices">
              <h5>
                <label>
                  <input
                    type="radio"
                    name="checkout"
                    value="rental"
                    checked={selectedPriceType === "rental"}
                    onChange={() => setSelectedPriceType("rental")}
                  />
                  {` Total Rental Price: ₹${totalRentalPrice}`}
                </label>
              </h5>
              <h5>
                <label>
                  <input
                    type="radio"
                    name="checkout"
                    value="selling"
                    checked={selectedPriceType === "selling"}
                    onChange={() => setSelectedPriceType("selling")}
                  />
                  {` Total Price: ₹${totalSellingPrice}`}
                </label>
              </h5>
            </div>
            <div className="checkout-button-container">
              <button
                className="checkout-button"
                onClick={() =>
                  handlePayClick(
                    selectedPriceType === "rental"
                      ? totalRentalPrice
                      : totalSellingPrice
                  )
                }
              >
                Pay Now
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Payment Processing Modal */}
      <Modal
        title="Processing Payment"
        visible={isProcessing}
        footer={null}
        closable={false}
      >
        <div style={{ textAlign: "center", padding: "20px" }}>
          <Spin size="large" /> {/* Show loading spinner */}
          <p>
            {paymentStatus === "success"
              ? "Payment Successful!"
              : "Payment Processing!"}
          </p>
          {paymentStatus === "error" && <p>Please try again.</p>}
        </div>
      </Modal>
    </div>
  );
}

export default CartUpdated;
