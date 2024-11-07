import React, { useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import { Form, Input, Button, Checkbox, Tooltip, Modal, Result } from "antd";
import { InfoCircleOutlined } from "@ant-design/icons";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";
import "../../css/loan/loanform.css";

const LoanForm = () => {
  const id = useSelector((state) => state.auth.buyerId);
  const token = useSelector((state) => state.auth.token);

  const [formData, setFormData] = useState({
    buyerId: id,
    amountRequested: 50000,
    purpose: "Buy new farming equipment",
    annualIncome: 45000,
    incomeVerification: "Bank statement",
    creditScore: 650,
    debtToIncomeRatio: 0.25,
    collateral: "Farm land",
    yearsInFarming: 5,
    landArea: 3.5,
    hasLoanDefaults: false,
  });

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState({
    status: "",
    title: "",
    description: "",
  });

  const [form] = Form.useForm(); // Use Ant Design form hooks for validation and submission

  // Handle form value changes and ensure numeric fields are properly handled
  const handleValuesChange = (changedValues, allValues) => {
    // Convert specific fields to numbers as necessary
    const updatedValues = { ...allValues };

    if (changedValues.amountRequested) {
      updatedValues.amountRequested = Number(changedValues.amountRequested);
    }
    if (changedValues.creditScore) {
      updatedValues.creditScore = Number(changedValues.creditScore);
    }
    if (changedValues.annualIncome) {
      updatedValues.annualIncome = Number(changedValues.annualIncome);
    }
    if (changedValues.debtToIncomeRatio) {
      updatedValues.debtToIncomeRatio = Number(changedValues.debtToIncomeRatio);
    }
    if (changedValues.yearsInFarming) {
      updatedValues.yearsInFarming = Number(changedValues.yearsInFarming);
    }
    if (changedValues.landArea) {
      updatedValues.landArea = Number(changedValues.landArea);
    }

    setFormData(updatedValues); // Update formData with new values
  };

  // Handle form submission
  const handleSubmit = async () => {
    try {
      // Trigger form validation manually
      const values = await form.validateFields();
      setFormData(values); // Set the validated values to formData

      // Make API call to submit the loan application
      const response = await axios.post(
        `http://localhost:8099/auth/loanstatus`,
        values,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      const { eligible } = response.data; // Assume API returns "eligible" field
      if (eligible) {
        setModalContent({
          status: "success",
          title: "Loan Application Approved",
          description:
            "Congratulations! Your loan application has been approved.",
        });
      } else {
        setModalContent({
          status: "warning",
          title: "Loan Application Not Approved",
          description:
            "Unfortunately, your loan application did not meet the eligibility criteria.",
        });
      }
      setIsModalOpen(true); // Show modal with results
    } catch (error) {
      console.error("Error submitting loan application:", error);
      setModalContent({
        status: "error",
        title: "Error",
        description: "Failed to submit loan application. Please try again.",
      });
      setIsModalOpen(true);
    }
  };

  return (
    <div>
      <Header />
      {/* Info Icon Positioned at Top Right */}
      <div style={{ position: "relative" }}>
        <Tooltip
          title={
            <div>
              <p>
                <strong>Eligibility Criteria:</strong>
              </p>
              <p>1. Minimum credit score: 600</p>
              <p>2. Minimum annual income: $30,000</p>
              <p>3. Minimum years in farming: 2 years</p>
              <p>4. Minimum land area: 2.0 acres</p>
              <p>5. No previous loan defaults</p>
            </div>
          }
          overlayStyle={{ maxWidth: 300 }} // Adjust tooltip width as needed
          placement="bottomRight" // Change placement to avoid overlap
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
      <div className="loan-container">
        <div className="loan-form-container">
          <h2>Loan Application Form</h2>
          <Form
            form={form} // Using Ant Design form
            onFinish={handleSubmit} // Submit handler
            initialValues={formData} // Initial form values
            layout="vertical" // Vertical form layout
            onValuesChange={handleValuesChange} // Handle values change
          >
            <Form.Item
              label="Buyer ID"
              name="buyerId"
              rules={[{ required: true, type: "number" }]}
            >
              <Input
                type="number"
                name="buyerId"
                value={formData.buyerId}
                disabled
              />
            </Form.Item>

            <Form.Item
              label="Amount Requested"
              name="amountRequested"
              rules={[{ required: true, type: "number", min: 1 }]}
            >
              <Input
                type="number"
                value={formData.amountRequested}
                onChange={(e) =>
                  handleValuesChange(
                    { amountRequested: e.target.value },
                    formData
                  )
                }
              />
            </Form.Item>

            <Form.Item
              label="Purpose"
              name="purpose"
              rules={[{ required: true }]}
            >
              <Input value={formData.purpose} />
            </Form.Item>

            <Form.Item
              label="Income Verification"
              name="incomeVerification"
              rules={[{ required: true }]}
            >
              <Input value={formData.incomeVerification} />
            </Form.Item>

            <Form.Item
              label="Credit Score"
              name="creditScore"
              rules={[{ required: true, type: "number", min: 300, max: 850 }]}
            >
              <Input
                type="number"
                value={formData.creditScore}
                onChange={(e) =>
                  handleValuesChange({ creditScore: e.target.value }, formData)
                }
              />
            </Form.Item>

            <Form.Item
              label="Debt-to-Income Ratio"
              name="debtToIncomeRatio"
              rules={[{ required: true, type: "number", min: 0, max: 1 }]}
            >
              <Input
                type="number"
                value={formData.debtToIncomeRatio}
                onChange={(e) =>
                  handleValuesChange(
                    { debtToIncomeRatio: e.target.value },
                    formData
                  )
                }
              />
            </Form.Item>

            <Form.Item
              label="Collateral"
              name="collateral"
              rules={[{ required: true }]}
            >
              <Input value={formData.collateral} />
            </Form.Item>

            <Form.Item
              label="Years in Farming"
              name="yearsInFarming"
              rules={[{ required: true, type: "number", min: 0 }]}
            >
              <Input
                type="number"
                value={formData.yearsInFarming}
                onChange={(e) =>
                  handleValuesChange(
                    { yearsInFarming: e.target.value },
                    formData
                  )
                }
              />
            </Form.Item>

            <Form.Item
              label="Land Area (in acres)"
              name="landArea"
              rules={[{ required: true, type: "number", min: 0 }]}
            >
              <Input
                type="number"
                value={formData.landArea}
                onChange={(e) =>
                  handleValuesChange({ landArea: e.target.value }, formData)
                }
              />
            </Form.Item>

            <Form.Item name="hasLoanDefaults" valuePropName="checked">
              <Checkbox
                checked={formData.hasLoanDefaults}
                onChange={(e) =>
                  handleValuesChange(
                    { hasLoanDefaults: e.target.checked },
                    formData
                  )
                }
              >
                Has Loan Defaults
              </Checkbox>
            </Form.Item>

            <Form.Item
              label="Annual Income"
              name="annualIncome"
              rules={[{ required: true, type: "number", min: 0 }]}
            >
              <Input
                type="number"
                value={formData.annualIncome}
                onChange={(e) =>
                  handleValuesChange({ annualIncome: e.target.value }, formData)
                }
              />
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit">
                Submit
              </Button>
            </Form.Item>
          </Form>
        </div>
      </div>
      <Footer />

      {/* Modal for displaying submission status */}
      <Modal
        title={modalContent.title}
        visible={isModalOpen}
        onCancel={() => setIsModalOpen(false)}
        footer={[
          <Button key="close" onClick={() => setIsModalOpen(false)}>
            Close
          </Button>,
        ]}
      >
        <Result
          status={modalContent.status}
          title={modalContent.title}
          subTitle={modalContent.description}
        />
      </Modal>
    </div>
  );
};

export default LoanForm;
