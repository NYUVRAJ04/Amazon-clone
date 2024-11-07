import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { clearAuth } from "../../redux/authSlice";
import { Navbar, Nav, NavDropdown } from "react-bootstrap";
import { FaUserCircle } from "react-icons/fa";
import "bootstrap/dist/css/bootstrap.min.css";
import "../../css/header/header.css";

function Header() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const username = useSelector((state) => state.auth.name);
  const role = useSelector((state) => state.auth.role); // Assuming the role is stored here

  const handleLogout = () => {
    dispatch(clearAuth());
    navigate("/");
  };

  const handleHomeClick = (event) => {
    event.preventDefault(); // Prevent default link behavior
    if (isAuthenticated) {
      if (role === "buyer") {
        navigate("/buyer");
      } else if (role === "seller") {
        navigate("/seller");
      }
    } else {
      navigate("/");
    }
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="custom-navbar">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/" onClick={handleHomeClick}>
          FarmEasy
        </Link>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Link className="nav-link" to="/" onClick={handleHomeClick}>
              Home
            </Link>
            <Link className="nav-link" to="/about">
              About
            </Link>
            <Link className="nav-link" to="/contact">
              Contact Us
            </Link>
          </Nav>

          {/* Conditional Links for Buyers Only */}
          {isAuthenticated && role === "buyer" && (
            <Nav className="ms-auto">
              <Link className="nav-link" to="/resources">
                Resources
              </Link>
              <Link className="nav-link" to="/productsmain">
                Products
              </Link>

              {/* Services Dropdown */}
              <NavDropdown title="Services" id="services-dropdown">
                <NavDropdown.Item as={Link} to="/insurance/eligibility">
                  Insurance Eligibility
                </NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/loan/eligibility">
                  Loan Eligibility
                </NavDropdown.Item>
              </NavDropdown>
            </Nav>
          )}

          {/* User Profile and Auth Links */}
          <Nav>
            {isAuthenticated ? (
              <NavDropdown
                title={
                  <>
                    <FaUserCircle size={20} className="me-2" /> {username}
                  </>
                }
                id="profile-dropdown"
              >
                {role === "buyer" && (
                  <>
                    <NavDropdown.Item as={Link} to="/buyerprofile">
                      Profile
                    </NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/cartnew">
                      Cart
                    </NavDropdown.Item>
                    {/* <NavDropdown.Item as={Link} to="/orders">
                      My Orders
                    </NavDropdown.Item> */}
                  </>
                )}
                <NavDropdown.Item onClick={handleLogout}>
                  Logout
                </NavDropdown.Item>
              </NavDropdown>
            ) : (
              <>
                <Link className="nav-link" to="/login">
                  Login
                </Link>
                <Link className="nav-link" to="/register">
                  Register
                </Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </div>
    </Navbar>
  );
}

export default Header;
