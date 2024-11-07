import React, { useState } from "react";
import axios from "axios";
import "../../css/seller/selleradd.css"; // Import the CSS file
import Header from "../header/Header";
import Footer from "../subcomp/Footer";

const SellerAddNew = () => {
  const [product, setProduct] = useState({
    name: "",
    description: "",
    rentalPrice: "",
    purchasePrice: "",
    quantity: "",
    categoryName: "",
    type: "", // e.g., 'crop' or 'machinery'
    // imagePath: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct({ ...product, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:9000/crops/add",
        product
      );
      console.log("Product created:", response.data);
      // Optionally, reset the form or show a success message
      setProduct({
        name: "",
        description: "",
        rentalPrice: "",
        purchasePrice: "",
        quantity: "",
        categoryName: "",
        type: "",
        // imagePath: ''
      });
      alert("Product added successfully!"); // Alert on success
    } catch (error) {
      console.error("Error creating product:", error);
      alert("Error adding product. Please try again."); // Alert on error
    }
  };

  return (
    <div>
      <Header />
      <div className="seller-page">
        <h2 className="header">Add New Listing</h2>
        <form className="product-form" onSubmit={handleSubmit}>
          <input
            type="text"
            name="name"
            placeholder="Product Name"
            value={product.name}
            onChange={handleChange}
            required
          />
          <textarea
            name="description"
            placeholder="Description"
            value={product.description}
            onChange={handleChange}
            required
          />
          <input
            type="number"
            name="rentalPrice"
            placeholder="Rental Price"
            value={product.rentalPrice}
            onChange={handleChange}
            required
          />
          <input
            type="number"
            name="purchasePrice"
            placeholder="Purchase Price (optional)"
            value={product.purchasePrice}
            onChange={handleChange}
          />
          <input
            type="number"
            name="quantity"
            placeholder="Quantity"
            value={product.quantity}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="categoryName"
            placeholder="Category Name"
            value={product.categoryName}
            onChange={handleChange}
            required
          />
          <select
            name="type"
            value={product.type}
            onChange={handleChange}
            required
          >
            <option value="">Select Type</option>
            <option value="crop">Crop</option>
            <option value="machinery">Machinery</option>
          </select>
          {/* <input type="text" name="imagePath" placeholder="Image Path (optional)" value={product.imagePath} onChange={handleChange} /> */}
          <button type="submit" className="submit-button">
            Add Listing
          </button>
        </form>
      </div>
      <div>
        <Footer />
      </div>
    </div>
  );
};

export default SellerAddNew;
