import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { addToCart } from "../../redux/cartSlice";
import { v4 as uuidv4 } from "uuid";
import "../../css/productsupdated/productdetailmodal.css";

function ProductDetailModal({ show, handleClose, product }) {
  const [successMessage, setSuccessMessage] = useState("");
  const dispatch = useDispatch();

  const handleAddToCart = (product) => {
    console.log(product);
    dispatch(
      addToCart({
        uniqueId: uuidv4(),
        name: product.name,
        image: product.imageUrl,
        rentalPrice: product.rentalPrice,
        description: product.description,
        sellingPrice: product.sellingPrice,
        quantity: 1, // Default quantity
      })
    );
    setSuccessMessage(`${product.name} added to cart successfully!`);
    setTimeout(() => setSuccessMessage(""), 3000); // Clear message after 3 seconds
  };

  if (!product) return null;
  return (
    <Modal show={show} onHide={handleClose} className="no-scrollbar" size="lg">
      <Modal.Header closeButton>
        <Modal.Title>{product.name}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="product-img-des">
          <img
            src={product.imageUrl}
            alt={product.name}
            className="modal-image"
          />
          <div className="product-details">
            <p>Category: {product.categoryName}</p>
            <p>Description: {product.description}</p>
            <p>Rental Price: ₹{product.rentalPrice}</p>
            <p>Price: ₹{product.sellingPrice}</p>
          </div>
        </div>
        <div className="modal-footer-custom">
          <Button variant="warning" onClick={() => handleAddToCart(product)}>
            Add to Cart
          </Button>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
      {successMessage && (
        <div className="success-message">{successMessage}</div>
      )}
    </Modal>
  );
}

export default ProductDetailModal;
