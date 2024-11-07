import React, { useState, useEffect } from "react";
import ResourcesHero from "./ResourcesHero";
import ArticleCard from "./ArticleCard";
import Header from "../header/Header";
import "../../css/resources/resource_page.css";
import Footer from "../subcomp/Footer";

function ResourcePage() {
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchArticles = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch("http://localhost:9006/api/awareness/all");
      if (!response.ok) throw new Error("Network response was not ok");
      const data = await response.json();
      setArticles(Array.isArray(data) ? data : [data]);
    } catch (error) {
      console.error("Error fetching articles:", error);
      setError("Failed to load articles. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchArticles();
  }, []);

  return (
    <div className="resource-layout">
      <Header />
      <div className="resources-hero">
        <ResourcesHero />
        <section className="resource-categories">
          <div className="heading">
            <h2>Modern Agricultural Resources</h2>
            <p>
              In today’s fast-evolving agricultural landscape, modern resources
              and technologies play a crucial role in enhancing productivity,
              sustainability, and efficiency. Below are some key areas of focus
              that are transforming the way we approach farming:
            </p>
          </div>
          <div className="cards-container-res">
            <div className="card-res precision-agriculture">
              <div className="icon-res precision-agriculture-icon">
                <img
                  src="../../../utility/precision.png"
                  alt="Precision Agriculture Icon"
                />
              </div>
              <h6>Precision Agriculture</h6>
            </div>
            <div className="card-res climate-smart-agriculture">
              <div className="icon-res climate-smart-agriculture-icon">
                <img
                  src="../../../utility/climate-smart.png"
                  alt="Climate-Smart Agriculture Icon"
                />
              </div>
              <h6>Climate-Smart Agriculture</h6>
            </div>
            <div className="card-res agroecology">
              <div className="icon-res agroecology-icon">
                <img
                  src="../../../utility/agriecology.png"
                  alt="Agroecology Icon"
                />
              </div>
              <h6>Agroecology</h6>
            </div>
            <div className="card-res smart-farming-technologies">
              <div className="icon-res smart-farming-technologies-icon">
                <img
                  src="../../../utility/smart-farming-technology.png"
                  alt="Smart Farming Technologies Icon"
                />
              </div>
              <h6>Smart Farming Technologies</h6>
            </div>
            <div className="card-res sustainable-resource-management">
              <div className="icon-res sustainable-resource-management-icon">
                <img
                  src="../../../utility/sustainable-res-mgmt.png"
                  alt="Sustainable Resource Management Icon"
                />
              </div>
              <h6>Sustainable Resource Management</h6>
            </div>
          </div>
        </section>

        {/* Sustainable Farming Practices with Video Background */}
        <div className="resource-item video-background">
          <iframe
            className="background-video"
            src="https://www.youtube.com/embed/iloAQmroRK0?autoplay=1&mute=1&loop=1&controls=0&playlist=iloAQmroRK0"
            title="Sustainable Farming Practices Video Background"
            frameBorder="0"
            allow="autoplay; encrypted-media"
            allowFullScreen
          ></iframe>
          <div className="video-overlay"></div>
        </div>

        {/* Articles Section */}
        <section className="resource-category">
          <h2>Articles</h2>
          <p>
            Read insightful articles on modern farming techniques, best
            practices, and more.
          </p>

          {loading && <p>Loading articles...</p>}
          {error && (
            <div className="error-message">
              <p>{error}</p>
              <button onClick={fetchArticles}>Retry</button>
            </div>
          )}

          {!loading && !error && (
            <div className="resource-list">
              {articles.map((article) => (
                <ArticleCard key={article.id} article={article} />
              ))}
            </div>
          )}
        </section>
      </div>
      <Footer />
    </div>
  );
}

export default ResourcePage;
