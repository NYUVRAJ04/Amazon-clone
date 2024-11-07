import React from "react";
import "../../css/resources/resourcehome.css";
import { Link } from "react-router-dom";

function ResourcesHomeCard() {
  return (
    <div className="modern-agri-card">
      <div className="modern-agri-content">
        <h2>Explore Modern Agriculture Resources</h2>
        <p>
          Stay informed and enhance your farming techniques with our
          comprehensive resources.
        </p>
        <Link to="/resources" className="learn-more-link">
          Learn More
        </Link>
      </div>
    </div>
  );
}

export default ResourcesHomeCard;
