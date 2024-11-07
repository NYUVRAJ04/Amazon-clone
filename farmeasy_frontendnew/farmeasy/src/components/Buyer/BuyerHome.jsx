import React from "react";
import "../../css/buyer/buyerhome.css";
import Footer from "../subcomp/Footer";
import ResourcesHomeCard from "../resources/ResourcesHomeCard";
import ProductMachineryCard from "../productsnew/ProductMachinery";
import ProductCropCard from "../productsnew/ProductCrop";
import BuyerHero from "./BuyerHero";
import Header from "../header/Header";

function BuyerHome() {
  return (
    <div className="buyer-home">
      <Header />
      <BuyerHero />
      <div className="cards-container">
        <div className="machinery-cards">
          <ProductMachineryCard />
        </div>
        <div className="crop-cards">
          <ProductCropCard />
        </div>
        <div className="resource-home-card-main">
          <ResourcesHomeCard />
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default BuyerHome;
