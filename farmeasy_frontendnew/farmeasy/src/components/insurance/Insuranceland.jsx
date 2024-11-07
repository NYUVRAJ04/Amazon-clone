import React from "react";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";
import "../../css/insurance/insurance.css";
import { useNavigate } from "react-router-dom";

function Insuranceland() {
  const navigate = useNavigate();
  const handledetailsform = () => {
    navigate("/insuranceform");
  };
  return (
    <div>
      <Header />
      <div className="insurance-o-con">
        <div className="hero-container">
          <div className="hero-content text-center">
            <h2>Protect Your Crops With the Right Insurance</h2>
            <p>Know Your Eligibility Within Minutes</p>
            <button
              className="btn btn-primary"
              type="button"
              onClick={handledetailsform}
            >
              Check Eligibility
            </button>
          </div>
        </div>
        <main className="container my-5">
          <div className="row">
            <div className="col w-100 mb-3">
              <h4 className="text-center ">Why Insurance is Required?</h4>
            </div>
          </div>
          <div className="row">
            <div className="col-md-4">
              <div className="content-box">
                <h5>Protection Against Natural Disasters:</h5>
                <p>
                  Farming is highly dependent on weather conditions, which can
                  be unpredictable. Natural disasters like floods, droughts,
                  cyclones, and hailstorms can severely damage crops, leading to
                  substantial financial losses for farmers.
                </p>
                <p>
                  Insurance provides farmers with financial support in the event
                  of these disasters, enabling them to recover and continue
                  their farming activities without falling into debt or extreme
                  poverty.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Safeguarding Investments in Machinery and Equipment:</h5>
                <p>
                  Many farmers invest significant amounts of money into
                  purchasing or renting agricultural machinery through FarmEasy.
                  Damage to machinery, either due to accidents or unforeseen
                  circumstances, could result in huge financial burdens.
                </p>
                <p>
                  Insurance helps cover the cost of repairs or replacement,
                  ensuring that farmers don’t lose access to the machinery they
                  need to maintain productivity.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Stabilizing Income and Reducing Uncertainty:</h5>
                <p>
                  Agricultural yields are subject to several variables,
                  including weather, pests, and market price fluctuations. Even
                  with a good harvest, market prices might not always be
                  favorable.
                </p>
                <p>
                  Insurance can offer coverage for income losses, providing a
                  safety net that helps stabilize farmers' earnings and reduces
                  the risk of financial distress.
                </p>
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-md-4">
              <div className="content-box">
                <h5>
                  Encouraging Investment and Adoption of New Technologies:
                </h5>
                <p>
                  Farmers are often hesitant to adopt modern farming
                  technologies and techniques due to the perceived risk of
                  failure or lack of familiarity. Insurance can provide a layer
                  of security that encourages them to take advantage of these
                  advancements, which can improve yields and overall efficiency.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Loan and Credit Security:</h5>
                <p>
                  Many farmers rely on loans to purchase seeds, fertilizers, and
                  equipment. In case of crop failure, they may struggle to repay
                  their loans, putting them at risk of losing their land or
                  assets.
                </p>
                <p>
                  Insurance provides coverage in such situations, ensuring that
                  farmers can continue repaying their loans even when faced with
                  adverse conditions, reducing the chances of defaulting and
                  improving their creditworthiness.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Promoting Financial Stability and Sustainability:</h5>
                <p>
                  By offering insurance coverage, FarmEasy ensures that farmers
                  have a safety net, enabling them to manage financial risks
                  effectively. This promotes long-term sustainability in
                  agriculture and helps build resilient farming communities.
                </p>
                <p>
                  Financial security allows farmers to focus on increasing
                  productivity and improving farm operations without constantly
                  fearing financial collapse.
                </p>
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-md-4">
              <div className="content-box">
                <h5>Supporting Farmers During Market Volatility:</h5>
                <p>
                  Insurance can also play a role in covering the risks
                  associated with fluctuating market prices for crops and
                  agricultural products.
                </p>
                <p>
                  When market prices drop significantly, insurance can
                  compensate farmers for the difference, ensuring they don’t
                  suffer drastic income reductions.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Protecting Against Health Risks and Labor Loss:</h5>
                <p>
                  Farming is physically demanding, and injuries or illnesses can
                  prevent farmers from working their fields, leading to losses
                  in crop production and income.
                </p>
                <p>
                  Health insurance ensures that farmers have access to medical
                  care and can get back to work quickly, while income protection
                  policies can cover periods when a farmer is unable to work due
                  to injury or illness.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="content-box">
                <h5>Building Trust with Financial Institutions and Buyers:</h5>
                <p>
                  Insured farmers are often viewed as lower-risk by financial
                  institutions, enabling them to access loans and financial
                  products more easily.
                </p>
                <p>
                  Similarly, buyers and suppliers may prefer working with
                  farmers who have insurance coverage, as it signals greater
                  stability and reliability in the farming operation.
                </p>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col w-100 insurance">
              <h5>Documents Required for Insurance Eligibility</h5>
              <ul>
                <li>
                  <strong>Personal Identification Documents:</strong> Aadhaar
                  Card, Voter ID or PAN Card, Passport (optional), Driving
                  License.
                </li>
                <li>
                  <strong>Land Ownership Documents:</strong> Land Title/Deed,
                  Land Records, Khasra/Khatuni, Land Lease Agreement (if
                  applicable).
                </li>
                <li>
                  <strong>Agricultural Proof:</strong> Certificate of Farming,
                  Crop Cultivation Record, Revenue Records (if available).
                </li>
                <li>
                  <strong>Bank Details:</strong> Bank Passbook Copy, Cancelled
                  Cheque, Loan Sanction Letter (if applicable).
                </li>
                <li>
                  <strong>Insurance History (if applicable):</strong> Previous
                  Insurance Policy, Insurance Claim History.
                </li>
                <li>
                  <strong>
                    Farm Machinery Documents (for machinery insurance):
                  </strong>{" "}
                  Machinery Invoice or Purchase Receipt, Ownership Certificate,
                  Rental Agreement (if applicable).
                </li>
                <li>
                  <strong>Crop Loan Documents (if applicable):</strong> Loan
                  Application Form, Loan Sanction Document, Subsidy Details.
                </li>
                <li>
                  <strong>Health Insurance Documents (if available):</strong>{" "}
                  Medical Records, Previous Health Insurance Policy, Doctor’s
                  Certificate (if required).
                </li>
                <li>
                  <strong>Photographs:</strong> Passport Size Photographs,
                  Photographs of Crops or Machinery (for verification purposes).
                </li>
                <li>
                  <strong>Others (Optional):</strong> Income Certificate,
                  Subsidy Certificates, Certificate of Eligibility.
                </li>
              </ul>
            </div>
          </div>
          <div className="row">
            <div className="col w-100 insurance">
              <h5 className="text-align-center">
                Minimum Eligibility Criteria for Insurance
              </h5>
              <ul>
                <li>
                  <strong>Farm Ownership or Tenancy:</strong> Must own the farm
                  or have a legal tenancy agreement.
                </li>
                <li>
                  <strong>Crop or Livestock Type:</strong> Eligibility depends
                  on specific crops or livestock being insured.
                </li>
                <li>
                  <strong>Geographic Location:</strong> Insurance coverage may
                  be limited to specific regions.
                </li>
                <li>
                  <strong>Farm Size:</strong> Must have at least 5 acres of
                  land.
                </li>
                <li>
                  <strong>Years in Farming:</strong> Minimum of 5 years of
                  farming experience is required.
                </li>
                <li>
                  <strong>Financial Stability:</strong> Must have a minimum
                  annual income of ₹10,000.
                </li>
                <li>
                  <strong>No Loan Defaults:</strong> Applicant must not have any
                  previous loan defaults.
                </li>
                <li>
                  <strong>Production Practices:</strong> Must follow specific
                  agricultural practices or guidelines.
                </li>
                <li>
                  <strong>Application and Documentation:</strong> Completion of
                  an insurance application and submission of necessary documents
                  is required.
                </li>
                <li>
                  <strong>Compliance with Legal Requirements:</strong> Must
                  comply with local agricultural laws and regulations.
                </li>
                <li>
                  <strong>Health and Safety Regulations:</strong> Must meet
                  health and safety standards.
                </li>
                <li>
                  <strong>Participation in Training Programs:</strong> May be
                  required to participate in training related to risk
                  management.
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

export default Insuranceland;
