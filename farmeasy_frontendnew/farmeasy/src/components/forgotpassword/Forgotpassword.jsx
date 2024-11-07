import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Form, Input, Button, message } from "antd";
import { MailOutlined, LockOutlined } from "@ant-design/icons";
import "../../css/forgotpassword/otp.css";

function ForgotPassword() {
  const [formData, setFormData] = useState({
    email: "",
    otp: "",
    password: "",
    repeatPassword: "",
  });

  const [otpSent, setOtpSent] = useState(false);
  const [isOtpVerified, setIsOtpVerified] = useState(false);

  const navigate = useNavigate();

  const handleEmailSubmit = async () => {
    if (!formData.email) {
      message.error("Email is required.");
      return;
    }
    try {
      const response = await axios.post(
        `http://localhost:8099/forgotPassword/verify`,
        { email: formData.email }
      );
      if (response.status === 200) {
        setOtpSent(true);
        message.success("OTP sent successfully!");
      }
    } catch (error) {
      console.error("Error sending OTP", error);
      message.error("Failed to send OTP. Please check the email address.");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8099/forgotPassword/verifyOtp`,
        { email: formData.email, otp: formData.otp }
      );

      if (response.status === 200) {
        message.success("OTP verified successfully!");
        setIsOtpVerified(true);
      }
    } catch (error) {
      console.error("Error verifying OTP", error);
      message.error(
        "Failed to verify OTP. Please check the OTP and try again."
      );
    }
  };

  const handlePasswordResetSubmit = async () => {
    if (formData.password !== formData.repeatPassword) {
      message.error("Passwords do not match.");
      return;
    }

    try {
      const response = await axios.post(
        `http://localhost:8099/forgotPassword/changePassword`,
        {
          email: formData.email,
          password: formData.password,
          repeatPassword: formData.repeatPassword,
        }
      );

      if (response.status === 200) {
        message.success("Password reset successfully!");
        navigate("/login");
      }
    } catch (error) {
      console.error("Error resetting password", error);
      message.error("Failed to reset password. Please try again.");
    }
  };

  return (
    <div className="otp-outer-container">
      <div className="otp-inner-container">
        <div className="otp-image-section">
          <img
            src="./utility/forgot-password.avif"
            className="otp-fp-image"
            alt="FarmEasy"
          />
        </div>

        <div className="otp-form-section">
          {!isOtpVerified ? (
            <Form
              className="otp-form"
              onFinish={otpSent ? handleSubmit : handleEmailSubmit}
            >
              <h2 className="otp-title">OTP Verification</h2>
              <Form.Item
                name="email"
                rules={[
                  { required: true, message: "Please input your email!" },
                ]}
              >
                <Input
                  prefix={<MailOutlined />}
                  placeholder="Email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                />
              </Form.Item>
              {otpSent && (
                <Form.Item
                  name="otp"
                  rules={[{ required: true, message: "Please input the OTP!" }]}
                >
                  <Input
                    prefix={<LockOutlined />}
                    placeholder="Enter OTP"
                    name="otp"
                    value={formData.otp}
                    onChange={handleChange}
                  />
                </Form.Item>
              )}
              <Form.Item>
                <Button type="primary" htmlType="submit" className="otp-btn">
                  {otpSent ? "Verify OTP" : "Send OTP"}
                </Button>
              </Form.Item>
            </Form>
          ) : (
            <Form className="sign-in-form" onFinish={handlePasswordResetSubmit}>
              <h2 className="title">Password Reset</h2>
              <Form.Item
                name="password"
                rules={[
                  { required: true, message: "Please input your password!" },
                ]}
              >
                <Input.Password
                  prefix={<LockOutlined />}
                  placeholder="Password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                />
              </Form.Item>
              <Form.Item
                name="repeatPassword"
                rules={[
                  { required: true, message: "Please confirm your password!" },
                ]}
              >
                <Input.Password
                  prefix={<LockOutlined />}
                  placeholder="Confirm Password"
                  name="repeatPassword"
                  value={formData.repeatPassword}
                  onChange={handleChange}
                />
              </Form.Item>
              <Form.Item>
                <Button type="primary" htmlType="submit" className="otp-btn">
                  Reset Password
                </Button>
              </Form.Item>
            </Form>
          )}
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;
