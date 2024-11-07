import React from "react"; // Assuming you are using react-bootstrap for modal
import "bootstrap/dist/css/bootstrap.min.css";

function ProductCard({ product, onCardClick }) {
  return (
    <div className="mini-card" onClick={() => onCardClick(product)}>
      <div className="mini-card-image">
        <img src={product.imageUrl} alt={product.name} />
      </div>
      <div className="mini-rent-price">Rent: {product.rentalPrice}</div>
      <div className="mini-selling-price">Sell: {product.sellingPrice}</div>
      <div className="product-name">{product.name}</div>
    </div>
  );
}

export default ProductCard;
