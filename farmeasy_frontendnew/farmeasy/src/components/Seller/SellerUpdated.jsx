import React, { useEffect, useState } from "react";
import SellerProductCard from "./SellerProductCard";
import Header from "../header/Header";
import { FaPlus } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import Footer from "../subcomp/Footer";
import EditProductModal from "./EditProductModal";
import axios from "axios";

function SellerUpdated() {
  const navigate = useNavigate();
  const [products, setProducts] = useState([]); // State to hold products
  const [loading, setLoading] = useState(true); // State to track loading status
  const [error, setError] = useState(null); // State to hold error messages
  const [activeTab, setActiveTab] = useState("machineries"); // State to track active tab
  const [showModal, setShowModal] = useState(false); // State to control modal visibility
  const [selectedProduct, setSelectedProduct] = useState(null); // State to hold selected product for editing

  // Function to fetch machineries
  const fetchMachineries = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch(
        "http://localhost:9008/api/machinery/allmachineries"
      ); // Replace with your API endpoint
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = await response.json();
      setProducts(data); // Set the fetched data to products state
    } catch (error) {
      setError(error.message); // Set error message if fetching fails
    } finally {
      setLoading(false); // Set loading to false after fetching
    }
  };

  // Function to fetch crops
  const fetchCrops = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch("http://localhost:9008/api/crops/allcrops"); // Replace with your API endpoint
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = await response.json();
      setProducts(data); // Set the fetched data to products state
    } catch (error) {
      setError(error.message); // Set error message if fetching fails
    } finally {
      setLoading(false); // Set loading to false after fetching
    }
  };

  // useEffect for fetching machineries
  useEffect(() => {
    if (activeTab === "machineries") {
      fetchMachineries();
    }
  }, [activeTab]); // Only run when activeTab changes to "machineries"

  // useEffect for fetching crops
  useEffect(() => {
    if (activeTab === "crops") {
      fetchCrops();
    }
  }, [activeTab]); // Only run when activeTab changes to "crops"

  // Check if loading
  if (loading) {
    return <div>Loading...</div>; // Show loading indicator
  }

  // Check for errors
  if (error) {
    return <div>Error: {error}</div>; // Show error message
  }

  const handleEdit = (product) => {
    setSelectedProduct(product);
    setShowModal(true);
  };

  const handleDelete = async (productId) => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch(
        `http://localhost:9008/api/${
          activeTab === "machineries" ? "machinery" : "crops"
        }/${productId}`,
        {
          method: "DELETE",
        }
      );
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      // Refetch the products after deletion
      if (activeTab === "machineries") {
        fetchMachineries();
      } else {
        fetchCrops();
      }
    } catch (error) {
      setError(error.message); // Set error message if deletion fails
    } finally {
      setLoading(false); // Set loading to false after deletion
    }
  };

  const handleAddProduct = () => {
    navigate("/addproduct");
  };

  const handleSave = async (formData, updatedProductData) => {
    try {
      console.log(formData)
      const response = await axios.put(
        `http://localhost:9008/api/${
          activeTab === "machineries" ? "machinery" : "crops"
        }/${updatedProductData.id}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log("Product updated successfully", response.data);
      // Refetch the products after updating
      if (activeTab === "machineries") {
        fetchMachineries();
      } else {
        fetchCrops();
      }
      setShowModal(false);
    } catch (error) {
      console.error("Error updating product", error);
      // Handle error (e.g., show an error message)
    }
  };

  return (
    <div className="seller-main">
      <Header />
      <div className="seller-hero">
        <img
          src="../../../utility/sellerhero.jpg"
          alt=""
          style={{ height: "400px", width: "100%" }}
        />
      </div>

      {/* Navigation Bar */}
      <div className="seller-navbar mt-3">
        <div className="nav-buttons">
          <button
            className={`nav-button ${
              activeTab === "machineries" ? "active" : ""
            }`}
            onClick={() => setActiveTab("machineries")}
          >
            Machineries
          </button>
          <button
            className={`nav-button ${activeTab === "crops" ? "active" : ""}`}
            onClick={() => setActiveTab("crops")}
          >
            Crops
          </button>
        </div>
        <div className="add-button">
          <button className="add-product-button" onClick={handleAddProduct}>
            <FaPlus /> {/* Render the plus icon */}
          </button>
        </div>
      </div>

      <div className="seller-container-listings">
        <div className="seller-title">Your Listings</div>
        <div className="seller-cards">
          <div className="seller-listings">
            {products.map((product) => (
              <SellerProductCard
                key={product.id}
                product={product} // your existing handler
                onEdit={() => handleEdit(product)} // handler for editing
                onDelete={() => handleDelete(product.id)} // handler for deleting
              />
              // Pass product as a prop
            ))}
          </div>
        </div>
      </div>
      <Footer />

      {/* Edit Product Modal */}
      {selectedProduct && (
        <EditProductModal
          show={showModal}
          handleClose={() => setShowModal(false)}
          product={selectedProduct}
          handleSave={handleSave}
        />
      )}
    </div>
  );
}

export default SellerUpdated;
