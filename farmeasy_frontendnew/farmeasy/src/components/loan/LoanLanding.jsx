import React from "react";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";
import "../../css/insurance/insurance.css";
import { useNavigate } from "react-router-dom";

function LoanLanding() {
  const navigate = useNavigate();
  const handleLoanForm = () => {
    navigate("/loanform");
  };

  return (
    <div>
      <Header />
      <div className="insurance-o-con">
        <div className="hero-container">
          <div className="hero-content text-center">
            <h2>Secure Your Farm's Future with the Right Loan</h2>
            <p>Check Your Loan Eligibility Instantly</p>
            <button
              className="btn btn-primary"
              type="button"
              onClick={handleLoanForm}
            >
              Check Loan Eligibility
            </button>
          </div>
        </div>
        <main className="container my-5">
          <div className="row">
            <div className="col w-100 mb-3">
              <h4 className="text-center">
                Why Agricultural Loans are Essential?
              </h4>
            </div>
          </div>
          <div className="row">
            <div className="col-md-4">
              <div className="content-box">
                <h5>Investment in Modern Equipment:</h5>
                <p>
                  Agricultural loans provide farmers with the financial support
                  needed to invest in modern machinery and tools, enhancing
                  productivity and reducing manual labor.
                </p>
                <p>
                  With FarmEasy, farmers can access loan options tailored to
                  their equipment and machinery needs.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Access to Quality Seeds and Fertilizers:</h5>
                <p>
                  Loans help farmers invest in high-quality seeds, fertilizers,
                  and pesticides to ensure healthy crop production and increased
                  yields.
                </p>
                <p>
                  FarmEasy's loan services make it easy for farmers to secure
                  funds for these essential inputs.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Expanding Farmland and Infrastructure:</h5>
                <p>
                  Expanding farm size or building infrastructure like storage
                  units and irrigation systems often requires large capital
                  investments.
                </p>
                <p>
                  Loans help farmers take on these larger projects and grow
                  their farming business sustainably.
                </p>
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-md-4">
              <div className="content-box">
                <h5>Supporting Livestock and Dairy Farming:</h5>
                <p>
                  Loans aren't just for crop farmers—livestock and dairy farmers
                  can use loans to purchase feed, expand their herds, and invest
                  in modern facilities.
                </p>
                <p>
                  FarmEasy offers flexible loan options to support diverse
                  agricultural needs, including livestock and dairy farming.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Building Resilience Against Market Fluctuations:</h5>
                <p>
                  Market conditions can be unpredictable. Loans help farmers
                  maintain steady operations during periods of low prices or
                  unfavorable market conditions.
                </p>
                <p>
                  FarmEasy ensures farmers have access to loan options that
                  offer flexibility during tough times.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Emergency Funding for Crop Loss:</h5>
                <p>
                  Unforeseen events such as droughts, floods, and pest
                  infestations can destroy crops. Agricultural loans provide a
                  safety net, offering financial support during difficult
                  periods.
                </p>
                <p>
                  With FarmEasy’s loan services, farmers can get quick access to
                  emergency funds when they need them most.
                </p>
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col w-100 ">
              <h5>Documents Required for Loan Eligibility</h5>
              <ul>
                <li>
                  <strong>Personal Identification Documents:</strong> Aadhaar
                  Card, Voter ID, PAN Card, Passport (optional), Driving
                  License.
                </li>
                <li>
                  <strong>Land Ownership Documents:</strong> Land Title/Deed,
                  Land Records, Khasra/Khatuni, Land Lease Agreement (if
                  applicable).
                </li>
                <li>
                  <strong>Bank Statements:</strong> Bank Passbook Copy, Loan
                  History, Bank Account Details.
                </li>
                <li>
                  <strong>Financial Documents:</strong> Income Tax Returns,
                  Revenue Records (if available).
                </li>
                <li>
                  <strong>Previous Loan Documents (if applicable):</strong> Loan
                  Sanction Letter, Loan Repayment History, Subsidy Details.
                </li>
                <li>
                  <strong>
                    Machinery Loan Documents (for equipment loans):
                  </strong>{" "}
                  Purchase Invoice, Machinery Ownership/Rental Certificate.
                </li>
                <li>
                  <strong>Crop Loan Documents:</strong> Loan Application Form,
                  Loan Sanction Document.
                </li>
                <li>
                  <strong>Farm Insurance Documents (if applicable):</strong>{" "}
                  Insurance Policy Copy, Claim History.
                </li>
                <li>
                  <strong>Photographs:</strong> Passport Size Photographs, Farm
                  Photographs (if required).
                </li>
                <li>
                  <strong>Others (Optional):</strong> Income Certificate,
                  Certificate of Eligibility for Subsidy.
                </li>
              </ul>
            </div>
          </div>

          <div className="row">
            <div className="col w-100">
              <h5>Minimum Eligibility Criteria for Loan</h5>
              <ul>
                <li>
                  <strong>Farm Ownership:</strong> Must own or lease farmland
                  and demonstrate a minimum of 2 years in farming.
                </li>
                <li>
                  <strong>Loan Purpose:</strong> Must provide details on how the
                  loan will be used (e.g., equipment, seeds, livestock, etc.).
                </li>
                <li>
                  <strong>Credit Score:</strong> A minimum credit score of 600
                  is required for loan approval.
                </li>
                <li>
                  <strong>Geographic Location:</strong> Loans may be restricted
                  to specific areas based on the lender's policies.
                </li>
                <li>
                  <strong>Farm Size:</strong> A minimum land area of 2 acres is
                  required.
                </li>
                <li>
                  <strong>Financial Stability:</strong> Farmers must demonstrate
                  financial viability with an annual income of at least ₹30,000.
                </li>
                <li>
                  <strong>Compliance with Regulations:</strong> Must adhere to
                  local agricultural laws and environmental guidelines.
                </li>
                <li>
                  <strong>Loan Repayment Capacity:</strong> Farmers need to show
                  how they will repay the loan based on farm income or other
                  revenue streams.
                </li>
                <li>
                  <strong>No Previous Loan Defaults:</strong> Must not have a
                  history of loan defaults.
                </li>
                <li>
                  <strong>Document Submission:</strong> Submission of
                  appropriate financial, personal, and farm-related documents.
                </li>
              </ul>
            </div>
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default LoanLanding;
