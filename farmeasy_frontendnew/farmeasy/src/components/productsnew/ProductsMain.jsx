import React, { useState, useEffect } from "react";
import axios from "axios";
import ProductsSearch from "./ProductsSearch";
import ProductCard from "./ProductCard";
import Header from "../header/Header";
import "../../css/productsupdated/productsmain.css";
import ProductDetailModal from "./ProductDetailModal";
import { FaShoppingCart } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import Footer from "../subcomp/Footer";

function ProductsMain() {
  const token = useSelector((state) => state.auth.token);
  console.log(token);
  const [searchTerm, setSearchTerm] = useState("");
  const [allProducts, setAllProducts] = useState([]);
  const [results, setResults] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null); // For modal
  const [showModal, setShowModal] = useState(false); // For modal visibility
  const navigate = useNavigate();

  // Fetch all products when the component mounts
  useEffect(() => {
    const fetchAllProducts = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8099/auth/products",
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        console.log("allproducts :" + response.data);
        setAllProducts(response.data);
      } catch (error) {
        console.error("Error fetching all products:", error);
      }
    };
    fetchAllProducts();
  }, []);

  const handleCardClick = (product) => {
    setSelectedProduct(product);
    setShowModal(true); // Open modal
  };

  const handleClose = () => {
    setShowModal(false);
    setSelectedProduct(null); // Clear selected product
  };

  // Search products based on search term
  const handleSearch = async () => {
    try {
      if (searchTerm.trim() === "") {
        setResults([]); // Clear results if search term is empty
      } else {
        const response = await axios.get(
          "http://localhost:8099/auth/searchproducts",
          {
            params: { searchTerm },
            headers: {
              Authorization: `Bearer ${token}`, // Include the token in the headers
            },
          }
        );
        setResults(response.data);
      }
    } catch (error) {
      console.error("Error fetching search results:", error);
    }
  };

  // Choose to display either search results or all products
  const itemsToDisplay = results.length > 0 ? results : allProducts;

  return (
    <div>
      <Header />
      <div className="container-pro">
        <div className="products-search-bar">
          <div className="search-container">
            <ProductsSearch
              setSearchTerm={setSearchTerm}
              handleSearch={handleSearch}
            />
          </div>
          <div
            className="cart-icon"
            onClick={() => {
              navigate("/cartnew");
              console.log("Cart icon clicked");
            }}
          >
            <FaShoppingCart size={24} />
          </div>
        </div>
        <div className="products-display">
          <div className="product-cards">
            {itemsToDisplay.map((product) => (
              <ProductCard
                key={product.id}
                product={product}
                onCardClick={handleCardClick}
              />
            ))}
          </div>
          {/* Product Detail Modal */}
          <ProductDetailModal
            show={showModal}
            handleClose={handleClose}
            product={selectedProduct}
          />
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default ProductsMain;
