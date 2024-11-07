import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FaArrowRight } from "react-icons/fa";
import ProductDetailModal from "./ProductDetailModal";
import { useSelector } from "react-redux";
import "../../css/productsupdated/minicard.css"; // Ensure this path is correct

function ProductMachineryCard() {
  const token = useSelector((state) => state.auth.token);
  console.log(token);
  const [products, setProducts] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [showModal, setShowModal] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          "http://localhost:8099/auth/allmachineries",
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        const data = await response.json();
        console.log(data);
        setProducts(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [token]);

  const handleNext = () => {
    setCurrentIndex((prevIndex) => {
      const newIndex = (prevIndex + 5) % products.length;
      console.log("New Index:", newIndex);
      return newIndex;
    });
  };

  const handleCardClick = (product) => {
    setSelectedProduct(product);
    setShowModal(true);
  };

  const handleClose = () => {
    setShowModal(false);
    setSelectedProduct(null);
  };

  return (
    <div>
      <h2 className="explore-heading">Explore Machineries</h2>
      <div className="product-container">
        <div className="product-cards">
          {products.slice(currentIndex, currentIndex + 5).map((product) => (
            <div
              key={product.id}
              className="mini-card"
              onClick={() => handleCardClick(product)}
            >
              <div className="mini-card-image">
                <img src={product.imageUrl} alt={product.name} />
              </div>
              <div className="mini-rent-price">Rent: {product.rentalPrice}</div>
              <div className="mini-selling-price">
                Sell: {product.sellingPrice}
              </div>
              <div className="product-name">{product.name}</div>
            </div>
          ))}
        </div>
        <div className="arrow-container" onClick={handleNext}>
          <FaArrowRight className="arrow-icon" />
        </div>
      </div>
      <div className="explore-all">
        <Link to="/productsmain">
          Explore All <span className="arrow">→</span>
        </Link>
      </div>

      {selectedProduct && (
        <ProductDetailModal
          show={showModal}
          handleClose={handleClose}
          product={selectedProduct}
        />
      )}
    </div>
  );
}

export default ProductMachineryCard;
