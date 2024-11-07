import React from "react";
import Sidebar from "./Sidebar";
import { Bar, Pie } from "react-chartjs-2";
import { useState, useEffect } from "react";
import axios from "axios";
import "../../css/admin/admin.css";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import Header from "../header/Header";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

function AdminDashboard() {
  const [barData, setBarData] = useState({
    labels: [],
    datasets: [],
  });
  const [pieData, setPieData] = useState({ labels: [], datasets: [] });
  const [stats, setStats] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get("http://localhost:9006/admin/stats");

      const stats = response.data;
      console.log(stats);

      setStats(stats);

      setBarData({
        labels: ["Loan Applications", "Insurance Applications"],
        datasets: [
          {
            label: "Statistics",
            data: [
              stats.totalLoanApplications,
              stats.totalInsuranceApplications,
            ],
            backgroundColor: "rgba(75, 192, 192, 0.6)",
            borderColor: "rgba(75, 192, 192, 1)",
            borderWidth: 1,
          },
        ],
      });

      setPieData({
        labels: ["Buyers", "Sellers"],
        datasets: [
          {
            label: "Buyers Roles",
            data: [stats.totalFarmers, stats.totalSellers],
            backgroundColor: [
              "rgba(255, 99, 132, 0.6)",
              "rgba(54, 162, 235, 0.6)",
              "rgba(255, 206, 86, 0.6)",
            ],
            borderColor: [
              "rgba(255, 99, 132, 1)",
              "rgba(54, 162, 235, 1)",
              "rgba(255, 206, 86, 1)",
            ],
            borderWidth: 1,
          },
        ],
      });
    };

    fetchData();
  }, []);

  const barOptions = {
    responsive: true,
    maintainAspectRatio: false, // Allow the chart to resize
    scales: {
      x: {
        beginAtZero: true,
      },
      y: {
        beginAtZero: true,
      },
    },
    barThickness: 50, // Adjust this value to reduce the bar width
  };

  const pieOptions = {
    responsive: true,
    maintainAspectRatio: false, // Allow the chart to resize
  };

  return (
    <div className="admin-dashboard">
      <div className="admin-header">
        <Header />
      </div>
      <Sidebar />
      <div className="admin-content">
        <div>
          <h1>Admin Dashboard</h1>
        </div>
        <div className="stats-cards">
          <div className="card-admin">
            <h3>Total Loan Applications</h3>
            <p>{stats.totalLoanApplications}</p>
          </div>
          <div className="card-admin">
            <h3>Total Insurance Applications</h3>
            <p>{stats.totalInsuranceApplications}</p>
          </div>
          <div className="card-admin">
            <h3>Total Buyers</h3>
            <p>{stats.totalFarmers}</p>
          </div>
          <div className="card-admin">
            <h3>Total Sellers</h3>
            <p>{stats.totalSellers}</p>
          </div>
        </div>
        <div className="charts-section">
          <div className="chart">
            <h2>Applications</h2>
            <div style={{ position: "relative", height: "400px" }}>
              <Bar data={barData} options={barOptions} />
            </div>
          </div>
          <div className="chart">
            <div style={{ position: "relative", height: "400px" }}>
              <Pie data={pieData} options={pieOptions} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;
