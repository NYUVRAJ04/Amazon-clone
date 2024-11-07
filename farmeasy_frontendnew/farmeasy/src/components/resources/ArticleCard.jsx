import React from "react";
import { useNavigate } from "react-router-dom";
import { FaDownload } from "react-icons/fa";
import "../../css/resources/articlecard.css";

function ArticleCard({ article }) {
  const navigate = useNavigate();

  const handleDownload = () => {
    if (article.fileUrl.startsWith("http")) {
      window.open(article.fileUrl, "_blank");
    } else {
      console.error("Invalid file URL");
    }
  };

  const handleLearnMore = () => {
    navigate("/resourcespage", { state: { article: article } });
  };

  console.log("Article file URL:", article.fileUrl);

  return (
    <div className="resource-item">
      <h3>{article.title}</h3>
      <p>{article.description}</p>
      {/* {article.downloadable && (
        <button onClick={handleDownload} className="download-button">
          <FaDownload className="me-2" /> 
          Download
        </button>
      )} */}
      <button onClick={handleLearnMore} className="learnmore">
        Learn More
      </button>
    </div>
  );
}

export default ArticleCard;
