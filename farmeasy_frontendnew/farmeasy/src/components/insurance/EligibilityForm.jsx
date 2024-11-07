import React, { useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";
import { Button, Form, Input, Modal, Checkbox, Tooltip, Result } from "antd"; 
import "../../css/insurance/insuranceform.css";
import { InfoCircleOutlined } from "@ant-design/icons";

const EligibilityForm = () => {
  const [currentStep, setCurrentStep] = useState(1);
  const [isCriteriaModalOpen, setIsCriteriaModalOpen] = useState(false);
  const [isSuccessModalOpen, setIsSuccessModalOpen] = useState(false);
  const [isFailureModalOpen, setIsFailureModalOpen] = useState(false); 
  const [isNotEligibleModalOpen, setIsNotEligibleModalOpen] = useState(false); // New state for not eligible modal
  const [token] = useSelector((state) => [state.auth.token]);
  const [id] = useSelector((state) => [state.auth.buyerId]);

  const [buyer, setBuyer] = useState({
    name: "",
    email: "",
    creditScore: "",
    yearsInFarming: "",
    annualIncome: "",
    landArea: "",
  });

  const [insurance, setInsurance] = useState({
    buyerId: id,
    insuranceType: "",
    coverageAmount: "",
    assetValue: "",
    previousClaims: false,
  });

  const [errors, setErrors] = useState({});
  const [unsatisfiedCriteria, setUnsatisfiedCriteria] = useState([]);

  const handleBuyerChange = (name, value) => {
    setBuyer((prev) => ({ ...prev, [name]: value }));
    validateField(name, value);
  };

  const handleInsuranceChange = (name, value) => {
    setInsurance((prev) => ({ ...prev, [name]: value }));
    validateField(name, value);
  };

  const validateField = (name, value) => {
    let errorMsg = "";
    switch (name) {
      case "name":
        if (!/^[A-Za-z\s]+$/.test(value)) {
          errorMsg = "Name should only contain letters and spaces.";
        }
        break;
      case "email":
        if (!/\S+@\S+\.\S+/.test(value)) {
          errorMsg = "Email is invalid.";
        }
        break;
      case "creditScore":
        if (value < 300 || value > 850) {
          errorMsg = "Credit score should be between 300 and 850.";
        }
        break;
      case "yearsInFarming":
      case "annualIncome":
      case "landArea":
      case "coverageAmount":
      case "assetValue":
        if (value < 0) {
          errorMsg = `${name.replace(
            /([A-Z])/g,
            " $1"
          )} should be a positive number.`;
        }
        break;
      case "insuranceType":
        if (!/^[A-Za-z\s]+$/.test(value)) {
          errorMsg = "Insurance type should only contain letters and spaces.";
        }
        break;
      default:
        break;
    }
    setErrors((prev) => ({ ...prev, [name]: errorMsg }));
  };

  const handleNext = async () => {
    if (Object.values(errors).some((error) => error)) {
      return;
    }
    try {
      const response = await axios.put(
        `http://localhost:8099/auth/updatebuyer/${id}`,
        buyer,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("Buyer details updated successfully:", response.data);
      setCurrentStep((prevStep) => prevStep + 1);
    } catch (error) {
      console.error("Error updating buyer details:", error);
    }
  };

  const handleSubmit = async () => {
    if (Object.values(errors).some((error) => error)) {
      return;
    }
    try {
      const response = await axios.post(
        "http://localhost:8099/auth/insurancestatus",
        insurance,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      const status = response.data.status;
      if (status === "APPROVED") {
        setIsSuccessModalOpen(true);
      } else {
        setUnsatisfiedCriteria(response.data.unsatisfiedCriteria || []);
        setIsNotEligibleModalOpen(true); // Open not eligible modal
      }
    } catch (error) {
      console.error("Error adding insurance details:", error);
      setUnsatisfiedCriteria([
        "An error occurred while submitting your details.",
      ]);
      setIsFailureModalOpen(true);
    }
  };

  return (
    <div className="o-icon">
      <Header />
      <div className="eligibility-criteria-header" style={{ position: "relative" }}>
        <Tooltip
          title={
            <div>
              <p>Here are the eligibility criteria for the insurance:</p>
              <ul>
                <li>Minimum credit score: 300</li>
                <li>Maximum credit score: 850</li>
                <li>Years in farming should be a positive number</li>
                <li>Annual income should be a positive number</li>
                <li>Land area should be a positive number</li>
                <li>Insurance type should only contain letters and spaces</li>
                <li>Coverage amount should be a positive number</li>
                <li>Asset value should be a positive number</li>
              </ul>
            </div>
          }
          overlayStyle={{ maxWidth: 300 }}
          placement="bottomRight"
        >
          <InfoCircleOutlined
            style={{
              position: "absolute",
              top: 10,
              right: 10,
              fontSize: "24px",
              color: "#1890ff",
            }}
          />
        </Tooltip>
      </div>

      <Modal
        title="Eligibility Result"
        visible={isSuccessModalOpen}
        onCancel={() => setIsSuccessModalOpen(false)}
        footer={[
          <Button key="close" onClick={() => setIsSuccessModalOpen(false)}>
            Close
          </Button>,
        ]}
      >
        <Result
          status="success"
          title="Congratulations!"
          subTitle="You are eligible for the insurance."
        />
      </Modal>

      {/* Failure Modal */}
      <Modal
        title="Eligibility Result"
        visible={isFailureModalOpen}
        onCancel={() => setIsFailureModalOpen(false)}
        footer={[
          <Button key="close" onClick={() => setIsFailureModalOpen(false)}>
            Close
          </Button>,
        ]}
      >
        <Result
          status="error"
          title="Submission Failed"
          subTitle="There was an issue with your submission. Please check the criteria."
        />
      </Modal>

      {/* Not Eligible Modal */}
      <Modal
        title="Eligibility Result"
        visible={isNotEligibleModalOpen}
        onCancel={() => setIsNotEligibleModalOpen(false)}
        footer={[
          <Button key="close" onClick={() => setIsNotEligibleModalOpen(false)}>
            Close
          </Button>,
        ]}
      >
        <Result
          status="warning" // You can change this to "error" if you prefer
          title="Not Eligible"
          subTitle="Unfortunately, you do not meet the eligibility criteria for the insurance."
        />
      </Modal>

      <div className="insurance-application-form-container">
        <h1>Application Form</h1>
        <Form
          layout="vertical"
          onFinish={currentStep === 2 ? handleSubmit : handleNext}
          className="insurance-application-form"
        >
          {currentStep === 1 && (
            <div>
              <h2>Step 1: Enter Your Farm Details</h2>
              <Form.Item
                label="Name"
                required
                validateStatus={errors.name ? "error" : ""}
                help={errors.name}
              >
                <Input
                  value={buyer.name}
                  onChange={(e) => handleBuyerChange("name", e.target.value)}
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item
                label="Email"
                required
                validateStatus={errors.email ? "error" : ""}
                help={errors.email}
              >
                <Input
                  type="email"
                  value={buyer.email}
                  onChange={(e) => handleBuyerChange("email", e.target.value)}
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item
                label="Credit Score"
                required
                validateStatus={errors.creditScore ? "error" : ""}
                help={errors.creditScore}
              >
                <Input
                  type="number"
                  min={300}
                  max={850}
                  value={buyer.creditScore}
                  onChange={(e) =>
                    handleBuyerChange("creditScore", e.target.value)
                  }
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item
                label="Years in Farming"
                required
                validateStatus={errors.yearsInFarming ? "error" : ""}
                help={errors.yearsInFarming}
              >
                <Input
                  type="number"
                  min={0}
                  value={buyer.yearsInFarming}
                  onChange={(e) =>
                    handleBuyerChange("yearsInFarming", e.target.value)
                  }
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item
                label="Annual Income"
                required
                validateStatus={errors.annualIncome ? "error" : ""}
                help={errors.annualIncome}
              >
                <Input
                  type="number"
                  min={0}
                  value={buyer.annualIncome}
                  onChange={(e) =>
                    handleBuyerChange("annualIncome", e.target.value)
                  }
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item
                label="Land Area (in acres)"
                required
                validateStatus={errors.landArea ? "error" : ""}
                help={errors.landArea}
              >
                <Input
                  type="number"
                  min={0}
                  value={buyer.landArea}
                  onChange={(e) =>
                    handleBuyerChange("landArea", e.target.value)
                  }
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item>
                <div className="button-container">
                  <Button
                    type="button"
                    htmlType="submit"
                    className="insurance-submit-button"
                  >
                    Next
                  </Button>
                </div>
              </Form.Item>
            </div>
          )}

          {currentStep === 2 && (
            <div>
              <h2>Step 2: Enter Insurance Details</h2>
              <Form.Item
                label="Insurance Type"
                required
                validateStatus={errors.insuranceType ? "error" : ""}
                help={errors.insuranceType}
              >
                <Input
                  value={insurance.insuranceType}
                  onChange={(e) =>
                    handleInsuranceChange("insuranceType", e.target.value)
                  }
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item
                label="Coverage Amount"
                required
                validateStatus={errors.coverageAmount ? "error" : ""}
                help={errors.coverageAmount}
              >
                <Input
                  type="number"
                  min={0}
                  value={insurance.coverageAmount}
                  onChange={(e) =>
                    handleInsuranceChange("coverageAmount", e.target.value)
                  }
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item
                label="Asset Value"
                required
                validateStatus={errors.assetValue ? "error" : ""}
                help={errors.assetValue}
              >
                <Input
                  type="number"
                  min={0}
                  value={insurance.assetValue}
                  onChange={(e) =>
                    handleInsuranceChange("assetValue", e.target.value)
                  }
                  className="custom-input"
                  required
                />
              </Form.Item>
              <Form.Item>
                <Checkbox
                  checked={insurance.previousClaims}
                  onChange={(e) =>
                    handleInsuranceChange("previousClaims", e.target.checked)
                  }
                >
                  Previous Claims
                </Checkbox>
              </Form.Item>
              <Form.Item>
                <div className="button-container">
                  <Button
                    type="button"
                    htmlType="submit"
                    className="insurance-submit-button"
                  >
                    Submit
                  </Button>
                </div>
              </Form.Item>
            </div>
          )}
        </Form>
      </div>
      <Footer />
    </div>
  );
};

export default EligibilityForm;
