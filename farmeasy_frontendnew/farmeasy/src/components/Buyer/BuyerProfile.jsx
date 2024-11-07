import React, { useEffect, useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import "../../css/buyer/buyerprofile.css";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";

const BuyerProfile = () => {
  const token = useSelector((state) => state.auth.token);
  const buyerId = useSelector((state) => state.auth.buyerId);
  const [buyerData, setBuyerData] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({});
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const fetchBuyerData = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8099/auth/buyer/${buyerId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setBuyerData(response.data);
        setFormData(response.data);
      } catch (error) {
        console.error("Error fetching buyer data:", error);
      }
    };

    fetchBuyerData();
  }, [token, buyerId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]:
        name === "hasLoanDefaults" ||
        name === "isEligibleForInsurance" ||
        name === "isEligibleForLoan"
          ? value === "Yes"
          : value,
    });
  };

  const handleSave = async () => {
    try {
      await axios.put(
        `http://localhost:8099/auth/updatebuyer/${buyerId}`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setBuyerData(formData);
      setIsEditing(false);
      setIsModalOpen(true); // Open the modal on successful update
    } catch (error) {
      console.error("Error updating buyer data:", error);
    }
  };

  const closeModal = () => {
    setIsModalOpen(false); // Close the modal
  };

  if (!buyerData) {
    return <div className="farmeasy-loading">Loading...</div>;
  }

  return (
    <div>
      <Header />
      <div className="farmeasy-profile-container">
        <h1 className="farmeasy-profile-title">Buyer Profile</h1>
        <div className="farmeasy-profile-emoji">👩‍🌾</div>
        <div className="farmeasy-profile-card">
          <form className="farmeasy-profile-form">
            <div className="farmeasy-form-group">
              <label>
                <strong>Buyer ID:</strong>
                <input
                  type="text"
                  name="buyerId"
                  className="farmeasy-input"
                  value={formData.buyerId}
                  readOnly
                  disabled
                />
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>User Email:</strong>
                <input
                  type="email"
                  name="user.email"
                  className="farmeasy-input"
                  value={formData.user?.email || ""}
                  readOnly
                  disabled
                />
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>Annual Income:</strong>
                <input
                  type="number"
                  name="annualIncome"
                  className="farmeasy-input"
                  value={formData.annualIncome}
                  onChange={handleChange}
                  disabled={!isEditing}
                />
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>Credit Score:</strong>
                <input
                  type="number"
                  name="creditScore"
                  className="farmeasy-input"
                  value={formData.creditScore}
                  onChange={handleChange}
                  disabled={!isEditing}
                />
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>Has Loan Defaults:</strong>
                <select
                  name="hasLoanDefaults"
                  className="farmeasy-select"
                  value={formData.hasLoanDefaults ? "Yes" : "No"}
                  onChange={(e) =>
                    handleChange({
                      target: {
                        name: "hasLoanDefaults",
                        value: e.target.value,
                      },
                    })
                  }
                  disabled={!isEditing}
                >
                  <option value="Yes">Yes</option>
                  <option value="No">No</option>
                </select>
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>Eligible for Insurance:</strong>
                <select
                  name="isEligibleForInsurance"
                  className="farmeasy-select"
                  value={formData.isEligibleForInsurance ? "Yes" : "No"}
                  disabled
                >
                  <option value="Yes">Yes</option>
                  <option value="No">No</option>
                </select>
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>Eligible for Loan:</strong>
                <select
                  name="isEligibleForLoan"
                  className="farmeasy-select"
                  value={formData.isEligibleForLoan ? "Yes" : "No"}
                  disabled
                >
                  <option value="Yes">Yes</option>
                  <option value="No">No</option>
                </select>
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>Land Area:</strong>
                <input
                  type="number"
                  name="landArea"
                  className="farmeasy-input"
                  value={formData.landArea}
                  onChange={handleChange}
                  disabled={!isEditing}
                />
              </label>
            </div>
            <div className="farmeasy-form-group">
              <label>
                <strong>Years in Farming:</strong>
                <input
                  type="number"
                  name="yearsInFarming"
                  className="farmeasy-input"
                  value={formData.yearsInFarming}
                  onChange={handleChange}
                  disabled={!isEditing}
                />
              </label>
            </div>
            {isEditing ? (
              <button
                type="button"
                className="farmeasy-button-save"
                onClick={handleSave}
              >
                Save
              </button>
            ) : (
              <button
                type="button"
                className="farmeasy-button-edit"
                onClick={() => setIsEditing(true)}
              >
                Edit
              </button>
            )}
          </form>
        </div>
      </div>

      {isModalOpen && (
        <div className="farmeasy-modal">
          <div className="farmeasy-modal-content">
            <span className="farmeasy-modal-close" onClick={closeModal}>
              &times;
            </span>
            <h2>Update Successful</h2>
            <p>Your data has been updated successfully!</p>
          </div>
        </div>
      )}

      <Footer />
    </div>
  );
};

export default BuyerProfile;
