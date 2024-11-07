import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Registration from "./components/registration/Registration";
import About from "./components/landing/About";
import ForgotPassword from "./components/forgotpassword/Forgotpassword";
import Login from "./components/login/Login";
import ContactUs from "./components/landing/Contactus";
import Insuranceland from "./components/insurance/Insuranceland";
import EligibilityForm from "./components/insurance/EligibilityForm";
import LoanLanding from "./components/loan/LoanLanding";
import LoanForm from "./components/loan/LoanForm";
import ResourcePage from "./components/resources/ResourcePage";
import AdminDashboard from "./components/admin/AdminDashboard";
import Resources from "./components/resources/ResourcePage";
import SellerData from "./components/admin/SellerData";
import BuyerData from "./components/admin/BuyerData";
import SellerAddNew from "./components/Seller/SellerAddNew";
import BuyerHome from "./components/Buyer/BuyerHome";
import ProductsMain from "./components/productsnew/ProductsMain";
import CartUpdated from "./components/cartnew/CartUpdated";
import OrderSummary from "./components/cartnew/OrderSummary";
import PDFViewer from "./components/resources/ArticlePage";
import SellerUpdated from "./components/Seller/SellerUpdated";
import AddProductForm from "./components/Seller/AddProductForm";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";
import PublicRoute from "./components/ProtectedRoute/PublicRoute";
import BuyerProfile from "./components/Buyer/BuyerProfile";
import Landing from "./components/landing/Landing";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route element={<PublicRoute />}>
            <Route path="/register" element={<Registration />} />
            <Route path="/login" element={<Login />} />
            <Route path="/forgotpassword" element={<ForgotPassword />} />
          </Route>
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<ContactUs />} />

          <Route element={<ProtectedRoute allowedRoles={["buyer"]} />}>
            <Route path="/buyer" element={<BuyerHome />} />
            <Route path="/buyerprofile" element={<BuyerProfile />} />
            <Route path="/insuranceform" element={<EligibilityForm />} />
            <Route path="/insurance/eligibility" element={<Insuranceland />} />
            <Route path="/loan/eligibility" element={<LoanLanding />} />
            <Route path="/loanform" element={<LoanForm />} />
            <Route path="/resource" element={<ResourcePage />} />
            <Route path="/productsmain" element={<ProductsMain />} />
            <Route path="/cartnew" element={<CartUpdated />} />
            <Route path="/order-summary" element={<OrderSummary />} />
            <Route path="/resourcespage" element={<PDFViewer />} />
          </Route>

          <Route element={<ProtectedRoute allowedRoles={["seller"]} />}>
            <Route path="/seller" element={<SellerUpdated />} />
            <Route path="/SellerAddNew" element={<SellerAddNew />} />
            <Route path="/addproduct" element={<AddProductForm />} />
          </Route>

          <Route element={<ProtectedRoute allowedRoles={["admin"]} />}>
            <Route path="/admin" element={<AdminDashboard />} />
            <Route path="/sellerdata" element={<SellerData />} />
            <Route path="/buyerdata" element={<BuyerData />} />
          </Route>

          <Route path="/resources" element={<Resources />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
