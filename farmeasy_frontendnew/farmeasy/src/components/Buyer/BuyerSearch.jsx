import React from "react";
import { useState } from "react";
import "../../css/buyer/buyersearch.css";

function BuyerSearch() {
  const [searchTerm, setSearchTerm] = useState("");

  const handleInputChange = (event) => {
    setSearchTerm(event.target.value);
  };
  return (
    <div className="search-bar-con">
      <div>
        <input
          type="text"
          placeholder="search ..."
          value={searchTerm}
          onChange={handleInputChange}
          className="search-input"
        ></input>
      </div>
    </div>
  );
}

export default BuyerSearch;
