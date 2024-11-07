import React from "react";
import { useNavigate } from "react-router-dom";
import Header from "../header/Header";
import Hero from "../subcomp/Hero";
import "../../css/about/about.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSeedling,
  faHandHoldingWater,
  faTractor,
} from "@fortawesome/free-solid-svg-icons"; // Icons
import Footer from "../subcomp/Footer";

function About() {
  const navigate = useNavigate();
  const handleLogin = () => {
    navigate("/login");
  };

  return (
    <div>
      <Header />
      <Hero />
      <main>
        <section>
          <div className="about-container">
            <div className="container">
              <div className="row align-items-center">
                {/* Left Column: Content */}
                <div className="col-md-6">
                  <h1 className="about-title">About FarmEasy</h1>
                  <p className="about-description">
                    Welcome to <strong>FarmEasy</strong>, a comprehensive
                    platform designed to empower farmers and agricultural
                    stakeholders. At FarmEasy, we aim to bridge the gap between
                    modern technology and traditional farming by providing easy
                    access to machinery, insurance, and loans.
                  </p>
                </div>
                {/* Right Column: Video */}

                <div className="col-md-6">
                  <iframe
                    className="embed-responsive-item"
                    width="100%"
                    height="315"
                    src="./utility/" // Replace with your YouTube video ID
                    title="YouTube video"
                    frameBorder="0"
                    allowFullScreen
                  ></iframe>
                </div>
              </div>
            </div>
          </div>

          <div className="vision-container">
            <div className="container text-center">
              <h2 className="vision-title">Our Vision</h2>
              <div className="row align-items-center">
                {/* Video Section with iframe */}
                <div className="col-md-6">
                  <iframe
                    className="embed-responsive-item"
                    width="100%"
                    height="315"
                    src="./utility/about2.mp4" // Replace with your YouTube video ID
                    title="YouTube video"
                    frameBorder="0"
                    allowFullScreen
                  ></iframe>
                </div>
                {/* Text Section */}
                <div className="col-md-6">
                  <p className="vision-description">
                    We believe in a future where every farmer has the tools and
                    knowledge they need to succeed. Our goal is to improve
                    agricultural productivity and financial sustainability by
                    offering innovative solutions to the challenges faced by the
                    farming community.
                  </p>
                </div>
              </div>
            </div>
          </div>

          <div className="container-full-width  what-we-offer-con ">
            <h2 className="text-center mb-4">What We Offer</h2>
            <div className="row">
              {/* Card 1 */}
              <div className="col-md-4 d-flex">
                <div className="card flex-fill">
                  <div className="card-body text-center">
                    <img
                      src="./utility/machinerymarketplace.png" // Replace with your actual icon file path
                      alt="Machinery Icon"
                      className="mb-3"
                      style={{ width: "50px", height: "50px" }} // Adjust size as necessary
                    />
                    <h5 className="card-title">Machinery Marketplace</h5>
                    <p className="card-text">
                      Buy or rent a wide range of agricultural machinery
                      tailored to your farming needs.
                    </p>
                  </div>
                </div>
              </div>
              {/* Card 2 */}
              <div className="col-md-4 d-flex">
                <div className="card flex-fill">
                  <div className="card-body text-center">
                    <img
                      src="./utility/eligibility.png" // Replace with your actual icon file path
                      alt="Insurance Icon"
                      className="mb-3"
                      style={{ width: "50px", height: "50px" }}
                    />
                    <h5 className="card-title">Insurance & Loan Eligibility</h5>
                    <p className="card-text">
                      Easily check your eligibility for insurance coverage and
                      agricultural loans, ensuring that you're financially
                      prepared for any eventuality.
                    </p>
                  </div>
                </div>
              </div>
              {/* Card 3 */}
              <div className="col-md-4 d-flex">
                <div className="card flex-fill">
                  <div className="card-body text-center mb-3">
                    <img
                      src="./utility/awareness.png" // Replace with your actual icon file path
                      alt="Education Icon"
                      className="mb-3"
                      style={{ width: "50px", height: "50px" }}
                    />
                    <h5 className="card-title">Awareness & Education</h5>
                    <p className="card-text">
                      Stay updated with the latest agricultural practices,
                      modern techniques, and government schemes to enhance your
                      yield and profits.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div className="container-full-width  bg-why-con ">
            <h2 className="text-center mb-4">Why Choose FarmEasy?</h2>
            <div className="row align-items-center">
              {/* Left Side: Carousel with 5 Images */}
              <div className="col-md-6">
                <div
                  id="carouselExample"
                  className="carousel slide"
                  data-bs-ride="carousel"
                  data-bs-interval="4000" // 4 seconds slide interval
                >
                  <div className="carousel-inner">
                    <div className="carousel-item active">
                      <img
                        src="./utility/access-why.avif" // Replace with your image path
                        className="d-block w-100"
                        alt="Accessibility "
                      />
                    </div>
                    <div className="carousel-item">
                      <img
                        src="./utility/support-why.avif" // Replace with your image path
                        className="d-block w-100"
                        alt="Farm Image 2"
                      />
                    </div>
                    <div className="carousel-item">
                      <img
                        src="./utility/awarenes.avif" // Replace with your image path
                        className="d-block w-100"
                        alt="Farm Image 3"
                      />
                    </div>
                  </div>

                  {/* Carousel controls (optional) */}
                  <button
                    className="carousel-control-prev"
                    type="button"
                    data-bs-target="#carouselExample"
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
                    data-bs-target="#carouselExample"
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

              {/* Right Side: Content */}
              <div className="col-md-6">
                <div className="animate__animated animate__fadeInRight">
                  <p>
                    We understand that lack of funds and knowledge are two of
                    the biggest barriers to successful farming. That’s why
                    FarmEasy is built with farmers in mind, offering simple,
                    accessible solutions to complex problems. By combining
                    technology with on-ground expertise, we aim to uplift the
                    farming community and promote sustainable agricultural
                    practices.
                  </p>
                  <ul>
                    <li>
                      <strong>Access to Modern Technology</strong>
                    </li>
                    <li>
                      <strong>Financial Support and Guidance</strong>
                    </li>
                    <li>
                      <strong>Expertise and Knowledge Sharing</strong>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <div className="container-fluid bg-light-green py-5">
            <div className="container">
              <div className="row align-items-center">
                <div className="col-md-12 text-center">
                  <h2 className="text-success animate__animated animate__fadeInDown mb-4">
                    Our Mission
                  </h2>
                  <p className="lead animate__animated animate__fadeInUp">
                    Our mission is to make farming easier, smarter, and more
                    profitable for everyone. Through FarmEasy, we are committed
                    to supporting the growth of the agricultural sector by
                    ensuring farmers have access to the right resources at the
                    right time.
                  </p>
                </div>
              </div>
              <div className="row mt-4 text-center">
                {/* Icon 1: Smart Farming */}
                <div className="col-md-4 animate__animated animate__zoomIn">
                  <FontAwesomeIcon
                    icon={faSeedling}
                    size="4x"
                    className="text-success mb-3"
                  />
                  <h5 className="text-success">Smart Farming</h5>
                  <p>
                    Advanced technology and sustainable farming practices to
                    maximize productivity.
                  </p>
                </div>
                {/* Icon 2: Support */}
                <div className="col-md-4 animate__animated animate__zoomIn delay-1s">
                  <FontAwesomeIcon
                    icon={faHandHoldingWater}
                    size="4x"
                    className="text-success mb-3"
                  />
                  <h5 className="text-success">Support</h5>
                  <p>
                    Financial and resource support for farmers to thrive in the
                    modern agriculture landscape.
                  </p>
                </div>
                {/* Icon 3: Right Resources */}
                <div className="col-md-4 animate__animated animate__zoomIn delay-2s">
                  <FontAwesomeIcon
                    icon={faTractor}
                    size="4x"
                    className="text-success mb-3"
                  />
                  <h5 className="text-success">Right Resources</h5>
                  <p>
                    Ensuring timely access to machinery, knowledge, and
                    financial support for better yields.
                  </p>
                </div>
              </div>
              <div className="row mt-5">
                <div className="col-md-12 text-center">
                  <p className="lead animate__animated animate__fadeInUp">
                    Join us in building a stronger, more sustainable
                    agricultural future!
                  </p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </main>
      <Footer />
    </div>
  );
}

export default About;
