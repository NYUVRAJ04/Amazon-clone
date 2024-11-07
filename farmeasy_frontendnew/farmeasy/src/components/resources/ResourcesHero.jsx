import React from "react";
import { Carousel } from "react-bootstrap";

const ResourcesHero = () => {
  return (
    <div className="resources-hero">
      <Carousel interval={3000} pause="hover" controls={false}>
        <Carousel.Item>
          <img
            className="d-block w-100 res-img"
            src="../../../utility/resourceimg1.jpg"
            alt="First slide"
          />
          <Carousel.Caption>
            <h3>Welcome to Modern Agriculture Awareness</h3>
            <p>
              Discover articles, videos, and infographics to boost your yield.
            </p>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item>
          <img
            className="d-block w-100"
            src="../../../utility/resourceimg2.jpg"
            alt="Second slide"
          />
          <Carousel.Caption>
            <h3>Learn with Videos</h3>
            <p>Engaging video content to enhance your farming techniques.</p>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item>
          <img
            className="d-block w-100"
            src="../../../utility/resourceimg3.jpg"
            alt="Third slide"
          />
          <Carousel.Caption>
            <h3>Explore Articles</h3>
            <p>
              In-depth articles providing insights and tips for modern farming.
            </p>
          </Carousel.Caption>
        </Carousel.Item>
      </Carousel>
    </div>
  );
};

export default ResourcesHero;
