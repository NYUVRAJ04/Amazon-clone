import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";

function ProductsSearch({ setSearchTerm, handleSearch }) {
  const onInputChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const onSearchClick = (e) => {
    e.preventDefault();
    handleSearch(); // Call the search function from ProductsMain
  };

  return (
    <form onSubmit={onSearchClick} className="products-search-bar">
      <input
        type="text"
        id="search"
        onChange={onInputChange}
        placeholder="Enter product name or category"
      />
      <button type="submit" className="btn-search">
        <FontAwesomeIcon icon={faSearch} />
      </button>
    </form>
  );
}

export default ProductsSearch;
