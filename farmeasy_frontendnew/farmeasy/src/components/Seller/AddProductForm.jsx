import React, { useState } from "react";
import axios from "axios";
import "../../css/seller/addproductform.css";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";
import { Modal, Button } from "antd";

function AddProductForm() {
  const [productDTO, setProductDTO] = useState({
    category: "",
    categoryName: "",
    description: "",
    name: "",
    rentalPrice: 0,
    sellingPrice: 0,
    availability: "Available", // Default value
  });
  const [file, setFile] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [isModalVisible, setIsModalVisible] = useState(false);
  console.log(productDTO);
  console.log(file);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProductDTO((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("file", file);
    console.log(formData);
    formData.append("product", JSON.stringify(productDTO));
    console.log(file);

    try {
      const response = await axios.post(
        "http://localhost:9008/api/products/add",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      setModalMessage("Product added successfully");
      setIsModalVisible(true);
      setTimeout(() => {
        setIsModalVisible(false);
        // Redirect to seller home
        window.location.href = "/seller";
      }, 2000);

      console.log("Form submitted successfully", response.data);
      // Handle success (e.g., show a success message, redirect, etc.)
    } catch (error) {
      setModalMessage("Error submitting form");
      setIsModalVisible(true);
      setTimeout(() => {
        setIsModalVisible(false);
      }, 2000);
      console.error("Error submitting form", error);
      // Handle error (e.g., show an error message)
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div>
      <Header />
      <form className="add-product-form" onSubmit={handleSubmit}>
        <h2>Add Product</h2>
        <div className="form-group">
          <div>
            <label className="label">Product Name</label>
          </div>
          <input
            type="text"
            name="name"
            className="input-field"
            value={productDTO.name}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label className="label">Category</label>
          <select
            name="category"
            className="input-field"
            value={productDTO.category}
            onChange={handleInputChange}
            required
          >
            <option value="">Select Category</option>
            <option value="Machinery">Machinery</option>
            <option value="Crop">Crop</option>
          </select>
        </div>
        <div className="form-group">
          <label className="label">Description</label>
          <textarea
            name="description"
            className="input-field"
            value={productDTO.description}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label className="label">Rental Price</label>
          <input
            type="number"
            name="rentalPrice"
            className="input-field"
            value={productDTO.rentalPrice}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label className="label">Selling Price</label>
          <input
            type="number"
            name="sellingPrice"
            className="input-field"
            value={productDTO.sellingPrice}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label className="label">Category Name</label>
          <input
            type="text"
            name="categoryName"
            className="input-field"
            value={productDTO.categoryName}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label className="label">Availability</label>
          <select
            name="availability"
            className="input-field"
            value={productDTO.availability}
            onChange={handleInputChange}
            required
          >
            <option value="Available">Available</option>
            <option value="Rented">Rented</option>
            <option value="Sold">Sold</option>
          </select>
        </div>
        <div className="form-group">
          <label className="label">Image Upload</label>
          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            required
          />
        </div>
        <div className="button-container">
          <button
            type="submit"
            className="submit-button"
            disabled={isSubmitting}
          >
            {isSubmitting ? "Submitting..." : "Submit"}
            {/* Submit */}
          </button>
          {/* <button
            type="button"
            className="cancel-button"
            onClick={() => {
              
            }}
          >
            Cancel
          </button> */}
        </div>
      </form>
      <Footer />
      <Modal
        title="Submission Status"
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={[
          <Button
            key="ok"
            type="primary"
            onClick={() => setIsModalVisible(false)}
          >
            OK
          </Button>,
        ]}
      >
        <p>{modalMessage}</p>
      </Modal>
    </div>
  );
}

export default AddProductForm;
