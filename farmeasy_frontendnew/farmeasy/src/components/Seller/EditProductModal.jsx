import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";

function EditProductModal({ show, handleClose, product, handleSave }) {
  const [productData, setProductData] = useState({
    ...product,
    machineryavailability: product.machineryavailability || "Available",
    cropavailability: product.cropavailability || "Available",
  });

  const [category, setCategory] = useState(product.category || "");
  const [file, setFile] = useState(null);

  useEffect(() => {
    setProductData({
      ...product,
      machineryavailability: product.machineryavailability || "Available",
      cropavailability: product.cropavailability || "Available",
    });
    setCategory(product.category || "");
  }, [product]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleCategoryChange = (e) => {
    setCategory(e.target.value);
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleAvailabilityChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = () => {
    const updatedProductData = { ...productData };
    const formData = new FormData();

    if (category === "Machinery") {
      delete updatedProductData.cropavailability;
      formData.append("file", file);
      formData.append("machinery", JSON.stringify(updatedProductData));
    } else if (category === "Crops") {
      delete updatedProductData.machineryavailability;
      formData.append("file", file);
      formData.append("crops", JSON.stringify(updatedProductData));
    }
    handleSave(formData, updatedProductData);
  };

  return (
    <Modal show={show} onHide={handleClose} className="custom-modal">
      <Modal.Header closeButton>
        <Modal.Title>Edit Product</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formProductName">
            <Form.Label>Product Name</Form.Label>
            <Form.Control
              type="text"
              name="name"
              value={productData.name}
              onChange={handleInputChange}
              className="custom-input"
            />
          </Form.Group>
          <Form.Group controlId="formCategory">
            <Form.Label>Category</Form.Label>
            <Form.Control
              as="select"
              name="category"
              value={category}
              onChange={handleCategoryChange}
              className="custom-select"
            >
              <option value="select">Select category</option>
              <option value="Machinery">Machinery</option>
              <option value="Crops">Crops</option>
            </Form.Control>
          </Form.Group>
          <Form.Group controlId="formDescription">
            <Form.Label>Description</Form.Label>
            <Form.Control
              as="textarea"
              name="description"
              value={productData.description}
              onChange={handleInputChange}
              className="custom-textarea"
            />
          </Form.Group>
          <Form.Group controlId="formRentalPrice">
            <Form.Label>Rental Price</Form.Label>
            <Form.Control
              type="number"
              name="rentalPrice"
              value={productData.rentalPrice}
              onChange={handleInputChange}
              className="custom-input"
            />
          </Form.Group>
          <Form.Group controlId="formSellingPrice">
            <Form.Label>Selling Price</Form.Label>
            <Form.Control
              type="number"
              name="sellingPrice"
              value={productData.sellingPrice}
              onChange={handleInputChange}
              className="custom-input"
            />
          </Form.Group>
          <Form.Group controlId="formCategoryName">
            <Form.Label>Category Name</Form.Label>
            <Form.Control
              type="text"
              name="categoryName"
              value={productData.categoryName}
              onChange={handleInputChange}
              className="custom-input"
            />
          </Form.Group>
          <Form.Group controlId="formImageUrl">
            <Form.Label>Image URL</Form.Label>
            <Form.Control
              type="text"
              name="imageUrl"
              value={productData.imageUrl}
              onChange={handleInputChange}
              className="custom-input"
            />
          </Form.Group>
          <Form.Group controlId="formFile">
            <Form.Label>Upload Image</Form.Label>
            <Form.Control
              type="file"
              name="file"
              onChange={handleFileChange}
              className="custom-input"
            />
          </Form.Group>
          {category === "Machinery" && (
            <Form.Group controlId="formMachineryAvailability">
              <Form.Label>Machinery Availability</Form.Label>
              <Form.Control
                as="select"
                name="machineryavailability"
                value={productData.machineryavailability}
                onChange={handleAvailabilityChange}
                className="custom-select"
              >
                <option value="Available">Available</option>
                <option value="Rented">Rented</option>
                <option value="Sold">Sold</option>
              </Form.Control>
            </Form.Group>
          )}
          {category === "Crops" && (
            <Form.Group controlId="formCropAvailability">
              <Form.Label>Crop Availability</Form.Label>
              <Form.Control
                as="select"
                name="cropavailability"
                value={productData.cropavailability}
                onChange={handleAvailabilityChange}
                className="custom-select"
              >
                <option value="Available">Available</option>
                <option value="Sold">Sold</option>
              </Form.Control>
            </Form.Group>
          )}
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default EditProductModal;
