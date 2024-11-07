import React from "react";
import { Carousel, Card, Button } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "../../css/landing/landing.css";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";
import { useNavigate } from "react-router-dom";

function Landing() {
  const navigate = useNavigate();
  const handleOnClick = () => {
    navigate("/register");
  };
  return (
    <div>
      <Header />
      <div className="landing-con">
        {/* Carousel */}
        <div className="landing-carousel">
          <Carousel>
            <Carousel.Item>
              <img
                className="d-block w-100"
                src="../../../utility/modernmachines.jpg"
                alt="Machinery for Modern Farming"
              />
              <Carousel.Caption>
                <h3>Modern Farming Machinery</h3>
                <p>Empowering farmers with advanced tools and equipment.</p>
              </Carousel.Caption>
            </Carousel.Item>
            <Carousel.Item>
              <img
                className="d-block w-100"
                src="../../../utility/landinginsurance.jpg"
                alt="Insurance and Loan Services"
              />
              <Carousel.Caption>
                <h3>Insurance and Loan Services</h3>
                <p>
                  Supporting your farming journey with secure financial options.
                </p>
              </Carousel.Caption>
            </Carousel.Item>
            <Carousel.Item>
              <img
                className="d-block w-100"
                src="../../../utility/education.avif"
                alt="Agriculture Education and Awareness"
              />
              <Carousel.Caption>
                <h3>Education and Awareness</h3>
                <p>Learn the best practices for sustainable agriculture.</p>
              </Carousel.Caption>
            </Carousel.Item>
          </Carousel>
        </div>

        {/* FarmEasy Feature Cards */}
        <div className="landing-cards">
          <Card style={{ width: "18rem" }}>
            <Card.Img variant="top" src="../../../utility/machinebuy.png" />
            <Card.Body>
              <Card.Title>Buy or Rent Equipment</Card.Title>
              <Card.Text>
                Access a wide range of agricultural machinery for purchase or
                rent, tailored to meet your farming needs.
              </Card.Text>
            </Card.Body>
          </Card>
          <Card style={{ width: "18rem" }}>
            <Card.Img
              variant="top"
              src="../../../utility/eligibilitycheck.png"
            />
            <Card.Body>
              <Card.Title>Check Eligibility</Card.Title>
              <Card.Text>
                Easily verify your eligibility for loans and insurance designed
                specifically for farmers.
              </Card.Text>
            </Card.Body>
          </Card>
          <Card style={{ width: "18rem" }}>
            <Card.Img variant="top" src="../../../utility/modernfarm.png" />
            <Card.Body>
              <Card.Title>Learn Modern Farming</Card.Title>
              <Card.Text>
                Stay informed on sustainable agriculture practices, tools, and
                techniques.
              </Card.Text>
            </Card.Body>
          </Card>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Landing;
