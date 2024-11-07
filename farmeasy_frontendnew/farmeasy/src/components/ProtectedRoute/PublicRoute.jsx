import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";

const PublicRoute = () => {
  const token = useSelector((state) => state.auth.token);
  const role = useSelector((state) => state.auth.role); 

  if (token) {
    switch (role) {
      case "buyer":
        return <Navigate to="/buyer" />;
      case "seller":
        return <Navigate to="/seller" />;
      case "admin":
        return <Navigate to="/admin" />;
      default:
        return <Navigate to="/" />;
    }
  }

  return <Outlet />;
};

export default PublicRoute;
