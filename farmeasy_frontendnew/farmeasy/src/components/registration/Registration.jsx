import React, { useState } from "react";
import { motion } from "framer-motion";
import { FaUser, FaEnvelope, FaPhone, FaLock } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useDispatch } from "react-redux";
import { Form, Input, Button, Select, message } from "antd";
import "../../css/registration/register.css";

const { Option } = Select;

function RegistrationForm() {
  const dispatch = useDispatch();
  const LOGIN_ROUTE = process.env.REACT_APP_LOGIN_ROUTE;

  const [form] = Form.useForm();
  const navigate = useNavigate();

  const handleSubmit = async (values) => {
    try {
      const response = await axios.post(
        "http://localhost:8099/auth/register",
        values
      );

      if (response.status === 201) {
        message.success("Registration successful. Please log in.");
        navigate(LOGIN_ROUTE);
      } else {
        console.error("Unexpected status code:", response.status);
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status === 400) {
          message.error("User already exists.");
        } else {
          console.error("Registration failed:", error.response.data);
          message.error("Registration failed. Please try again.");
        }
      }
    }
  };

  return (
    <div className="raga-oc">
      <motion.div
        className="ragaregister-container"
        initial={{ opacity: 0, scale: 0.8 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.6 }}
      >
        <h2 className="ragaheader">Registration </h2>
        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
          className="ragaform"
        >
          <Form.Item
            name="name"
            label="Name"
            rules={[{ required: true, message: "Name is required." }]}
            className="ragaform-group"
          >
            <Input
              prefix={<FaUser className="ragaform-icon" />}
              placeholder="Enter your name"
            />
          </Form.Item>
          <Form.Item
            name="email"
            label="Email"
            rules={[
              { required: true, message: "Email is required." },
              { type: "email", message: "Invalid email address." },
            ]}
            className="ragaform-group"
          >
            <Input
              prefix={<FaEnvelope className="ragaform-icon" />}
              placeholder="Enter your email"
            />
          </Form.Item>
          <Form.Item
            name="phone"
            label="Phone Number"
            rules={[
              { required: true, message: "Phone number is required." },
              {
                pattern: /^[0-9]{10}$/,
                message: "Invalid phone number. It should be 10 digits.",
              },
            ]}
            className="ragaform-group"
          >
            <Input
              prefix={<FaPhone className="ragaform-icon" />}
              placeholder="Enter your phone number"
            />
          </Form.Item>
          <Form.Item
            name="password"
            label="Password"
            rules={[
              { required: true, message: "Password is required." },
              {
                pattern: /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$/,
                message:
                  "Password must be at least 8 characters long and contain at least one number and one special character.",
              },
            ]}
            className="ragaform-group"
          >
            <Input.Password
              prefix={<FaLock className="ragaform-icon" />}
              placeholder="Enter your password"
            />
          </Form.Item>
          <Form.Item
            name="confirmPassword"
            label="Confirm Password"
            dependencies={["password"]}
            rules={[
              { required: true, message: "Please confirm your password." },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue("password") === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error("Passwords do not match."));
                },
              }),
            ]}
            className="ragaform-group"
          >
            <Input.Password
              prefix={<FaLock className="ragaform-icon" />}
              placeholder="Confirm your password"
            />
          </Form.Item>
          <Form.Item
            name="role"
            label="Role"
            rules={[{ required: true, message: "Please select a role." }]}
            className="ragaform-group"
          >
            <Select placeholder="Select a role">
              <Option value="buyer">Buyer</Option>
              <Option value="seller">Seller</Option>
              <Option value="admin">Admin</Option>
            </Select>
          </Form.Item>
          <div className="raga-btn-con"> 
            <Form.Item className="ragaform-group raga-button">
              <Button
                type="success"
                htmlType="submit"
                className="ragaregister-button"
              >
                Register
              </Button>
            </Form.Item>
            <div className="raga-login-prompt">
              <p>
                Already registered?{" "}
                <a href="/login" className="raga-login-link">
                  Click here to log in
                </a>
              </p>
            </div>
          </div>
        </Form>
      </motion.div>
    </div>
  );
}

export default RegistrationForm;
