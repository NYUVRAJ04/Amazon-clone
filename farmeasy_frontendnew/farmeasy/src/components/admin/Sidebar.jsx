import React from "react";
import { NavLink } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faTachometerAlt,
  faUsers,
  faUserTie,
} from "@fortawesome/free-solid-svg-icons";
import "../../css/admin/sidebar.css";

function Sidebar() {
  return (
    <div className="sidebar">
      <h2>FarmEasy</h2>
      <nav>
        <ul>
          <li>
            <NavLink to="/admin" activeClassName="active">
              <FontAwesomeIcon icon={faTachometerAlt} /> Dashboard
            </NavLink>
          </li>
          <li>
            <NavLink to="/sellerdata" activeClassName="active">
              <FontAwesomeIcon icon={faUserTie} /> Sellers
            </NavLink>
          </li>
          <li>
            <NavLink to="/buyerdata" activeClassName="active">
              <FontAwesomeIcon icon={faUsers} /> Buyers
            </NavLink>
          </li>
          {/* Uncomment and add icons as needed */}
          {/* <li>
            <NavLink to="/admindashboard" activeClassName="active">
              <FontAwesomeIcon icon={faFileAlt} /> Insurance Applications
            </NavLink>
          </li>
          <li>
            <NavLink to="/admindashboard" activeClassName="active">
              <FontAwesomeIcon icon={faChartLine} /> Sales
            </NavLink>
          </li> */}
        </ul>
      </nav>
    </div>
  );
}

export default Sidebar;
