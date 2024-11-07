import React, { useEffect, useState } from "react";
import "../../css/admin/sellerdata.css";
import Sidebar from "./Sidebar";
import { Pagination } from "react-bootstrap";

function BuyerData() {
  const [buyers, setBuyers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const buyersPerPage = 10;

  useEffect(() => {
    const fetchBuyers = async () => {
      try {
        const response = await fetch("http://localhost:9006/admin/getallbuyers");
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();
        setBuyers(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchBuyers();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  // Pagination logic
  const indexOfLastBuyer = currentPage * buyersPerPage;
  const indexOfFirstBuyer = indexOfLastBuyer - buyersPerPage;
  const currentBuyers = buyers.slice(indexOfFirstBuyer, indexOfLastBuyer);

  const totalPages = Math.ceil(buyers.length / buyersPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div>
      <Sidebar />
      <div className="data-con">
        <h2>Buyer Data</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Buyer ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Annual Income</th>
              <th>Credit Score</th>
              <th>Land Area</th>
              <th>Years in Farming</th>
            </tr>
          </thead>
          <tbody>
            {currentBuyers.map((buyer) => (
              <tr key={buyer.buyerId}>
                <td>{buyer.buyerId}</td>
                <td>{buyer.user.name}</td>
                <td>{buyer.user.email}</td>
                <td>{buyer.annualIncome}</td>
                <td>{buyer.creditScore}</td>
                <td>{buyer.landArea}</td>
                <td>{buyer.yearsInFarming}</td>
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

export default BuyerData;
