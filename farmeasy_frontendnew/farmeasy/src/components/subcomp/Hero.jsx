import React from "react";
import "../../css/header/hero.css"; // Make sure to import the CSS file

function Hero() {
  return (
    <div>
      <div
        id="carouselExampleAutoplaying"
        className="carousel slide"
        data-bs-ride="carousel"
        data-bs-interval="4000"
      >
        <div className="carousel-inner">
          <div className="carousel-item active">
            <img
              src="./utility/heroimg1.jpg"
              className="d-block w-100 carousel-img"
              alt="..."
            />
          </div>
          <div className="carousel-item">
            <img
              src="./utility/heroimg2.jpg"
              className="d-block w-100 carousel-img"
              alt="..."
            />
          </div>
          <div className="carousel-item">
            <img
              src="./utility/heroimg3.avif"
              className="d-block w-100 carousel-img"
              alt="..."
            />
          </div>
        </div>
        <button
          className="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleAutoplaying"
          data-bs-slide="prev"
        >
          <span
            className="carousel-control-prev-icon"
            aria-hidden="true"
          ></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button
          className="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleAutoplaying"
          data-bs-slide="next"
        >
          <span
            className="carousel-control-next-icon"
            aria-hidden="true"
          ></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>
    </div>
  );
}

export default Hero;
