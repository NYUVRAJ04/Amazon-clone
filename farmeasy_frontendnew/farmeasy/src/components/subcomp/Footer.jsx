import React from "react";
import "../../css/subcomponents/footer.css";
function Footer() {
  return (
    <div>
      <div className="footer-container col-footer  py-4">
        <div className="container-fluid text-white">
          <div className="row">
            <div className="col-md-4 mb-3">
              <h5>About Us</h5>
              <ul className="list-unstyled">
                <li>Providing access to modern machinery</li>
                <li>Eligibility for loans and insurance.</li>
                <li>Modern agricultural resources</li>
              </ul>
            </div>
            <div className="col-md-4 mb-3 text-center ">
              <h5>FarmEasy</h5>
              <ul className="list-unstyled">
                <li>Bengaluru,Karnataka</li>
                <li> Phone: +91 1234567890</li>
                <li> Email: farm.easy@gmail.com</li>
              </ul>
            </div>
            <div className="footer-a col-md-4 mb-3  text-center d-flex  flex-column ">
              <a className="footer-link" href="/about">
                About Us
              </a>
              <a className="footer-link" href="/services">
                Services
              </a>
              <a className="footer-link" href="/contact">
                Contact Us
              </a>
              <a className="footer-link" href="/privacy-policy">
                Privacy Policy
              </a>
            </div>
          </div>
          <div className="text-center py-3 bg-darker">
            <p className="mb-0">
              &copy; {new Date().getFullYear()} FarmEasy. All rights reserved.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Footer;
