import React from "react"; // Assuming you are using react-bootstrap for modal
import "bootstrap/dist/css/bootstrap.min.css";
import { FaTrashAlt, FaEdit } from "react-icons/fa"; // Import icons from react-icons
import "../../css/seller/sellercard.css";

function SellerProductCard({ product, onEdit, onDelete }) {
  return (
    <div className="seller-card">
      <div className="seller-card-image">
        <img src={product.imageUrl} alt={product.name} />
      </div>
      <div className="seller-rent-price">Rent: {product.rentalPrice}</div>
      <div className="seller-selling-price">Sell: {product.sellingPrice}</div>
      <div className="product-name">{product.name}</div>

      {/* Delete icon positioned at the top right */}
      <button
        className="delete-button"
        onClick={(e) => {
          e.stopPropagation();
          onDelete(product.id);
        }}
      >
        <FaTrashAlt /> {/* Use the trash icon from react-icons */}
      </button>

      {/* Edit icon positioned at the bottom left */}
      <button
        className="edit-button"
        onClick={(e) => {
          e.stopPropagation();
          onEdit(product.id);
        }}
      >
        <FaEdit /> {/* Use the edit icon from react-icons */}
      </button>
    </div>
  );
}

export default SellerProductCard;
