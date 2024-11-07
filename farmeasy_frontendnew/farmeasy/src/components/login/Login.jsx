import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { login, setAuth, setToken } from "../../redux/authSlice";
import axios from "axios";
import { Form, Input, Button, Typography, message } from "antd";
import { UserOutlined, LockOutlined } from "@ant-design/icons";
import "../../css/login/login.css";

const { Title } = Typography;

function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const REGISTER_ROUTE = process.env.REACT_APP_REGISTER_ROUTE;
  const SELLER_LOGIN_ROUTE = process.env.REACT_APP_SELLER_LOGIN_ROUTE;
  const FORGOT_PASSWORD_ROUTE = process.env.REACT_APP_FORGOT_PASSWORD_ROUTE;

  const [error, setError] = useState("");
  const [response, setResponse] = useState(null);

  const handleSubmit = async (values) => {
    try {
      const response = await axios.post("http://localhost:8099/auth/login", {
        email: values.email,
        password: values.password,
      });

      const logindata = response.data;
      console.log(logindata);

      if (logindata.token) {
        dispatch(setAuth(logindata));
        console.log(logindata);
      }

      console.log("Role:" + logindata.role);
      console.log("Login successful, token:", logindata.token);
      setError("");
      setResponse(logindata);
      if (logindata.role === "buyer") {
        navigate("/buyer");
      } else if (logindata.role === "seller") {
        navigate("/seller");
      } else if (logindata.role === "admin") {
        navigate("/admin");
      }
    } catch (error) {
      console.error("Login failed:", error);
      setError(error.response?.data || "Login failed. Please try again.");
      setResponse(null);
      message.error("Login failed. Please try again." + error.response.data);
    }
  };

  const handleRegisterClick = () => {
    navigate(REGISTER_ROUTE);
  };

  const handleSellerLogin = () => {
    navigate(SELLER_LOGIN_ROUTE);
  };

  const handleForgotPassword = () => {
    navigate(FORGOT_PASSWORD_ROUTE);
  };

  return (
    <div className="login-container">
      <div className="inner-container shadow-lg">
        <div className="container-fluid p-4">
          <div className="login-title text-center mb-4">
            <Title level={4}>FarmEasy</Title>
          </div>
          <div className="row">
            <div className="col-md-6 d-flex justify-content-center align-items-center icon-container">
              <UserOutlined style={{ fontSize: "80px" }} />
            </div>
            <div className="col-md-6 form-container">
              <Form
                name="login"
                initialValues={{ remember: true }}
                onFinish={handleSubmit}
              >
                <Form.Item
                  name="email"
                  rules={[
                    { required: true, message: "Please input your Email!" },
                  ]}
                >
                  <Input
                    prefix={<UserOutlined />}
                    placeholder="Email"
                    size="large"
                  />
                </Form.Item>
                <Form.Item
                  name="password"
                  rules={[
                    { required: true, message: "Please input your Password!" },
                  ]}
                >
                  <Input.Password
                    prefix={<LockOutlined />}
                    placeholder="Password"
                    size="large"
                  />
                </Form.Item>
                <Form.Item>
                  <Button
                    type="button"
                    htmlType="submit"
                    size="large"
                    className="btn-success"
                    block
                  >
                    Login
                  </Button>
                </Form.Item>
              </Form>
              <div className="text-center register">
                <p className="mb-0">
                  Not registered?{" "}
                  <a href="/register" onClick={handleRegisterClick}>
                    Register here
                  </a>
                </p>
                <p>
                  <a href="/forgotpassword" onClick={handleForgotPassword}>
                    Forgot password?
                  </a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
