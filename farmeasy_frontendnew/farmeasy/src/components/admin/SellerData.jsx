import React, { useEffect, useState } from "react";
import "../../css/admin/sellerdata.css";
import Sidebar from "./Sidebar";
import { Pagination } from "react-bootstrap";

function SellerData() {
  const [sellers, setSellers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const sellersPerPage = 10; // Set the number of sellers per page

  // Fetch seller data from the API
  useEffect(() => {
    const fetchSellers = async () => {
      try {
        const response = await fetch("http://localhost:9006/admin/allsellers"); // Replace with your actual API endpoint
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();
        setSellers(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchSellers();
  }, []);

  // Render loading state, error message, or table
  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  // Pagination logic
  const indexOfLastSeller = currentPage * sellersPerPage;
  const indexOfFirstSeller = indexOfLastSeller - sellersPerPage;
  const currentSellers = sellers.slice(indexOfFirstSeller, indexOfLastSeller);

  const totalPages = Math.ceil(sellers.length / sellersPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div>
      <Sidebar />
      <div className="data-con">
        <h2>Seller Data</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Seller ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Business Name</th>
            </tr>
          </thead>
          <tbody>
            {currentSellers.map((seller) => (
              <tr key={seller.sellerId}>
                <td>{seller.sellerId}</td>
                <td>{seller.user.name}</td>
                <td>{seller.user.email}</td>
                <td>{seller.businessName}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <Pagination className="justify-content-center">
          {[...Array(totalPages)].map((_, index) => (
            <Pagination.Item
              key={index + 1}
              active={index + 1 === currentPage}
              onClick={() => handlePageChange(index + 1)}
            >
              {index + 1}
            </Pagination.Item>
          ))}
        </Pagination>
      </div>
    </div>
  );
}

export default SellerData;
