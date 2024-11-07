import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
// import PDFViewer from "./PDFViewer"; // Commented out the PDFViewer component
import "../../css/resources/articlepage.css";
import Header from "../header/Header";
import Footer from "../subcomp/Footer";
import axios from "axios";

function ArticlePage() {
  const location = useLocation();
  const { article } = location.state || {};
  const [extractedText, setExtractedText] = useState("");
  const [videoUrls, setVideoUrls] = useState([]);

  useEffect(() => {
    if (article && article.id) {
      // Fetch the video URLs based on the article ID
      axios
        .get(
          `http://localhost:9006/api/media-files/awareness-content/${article.id}`
        )
        .then((response) => {
          console.log(response.data);
          setVideoUrls(response.data.map((file) => file.fileUrl));
        })
        .catch((error) => {
          console.error("Error fetching video URLs:", error);
        });
    }
  }, [article]);

  if (!article) {
    return <p>No article data provided.</p>;
  }

  return (
    <div>
      <Header />
      <div className="article-page-container">
        <div className="article-header-container">
          <h1 className="article-title-text">{article.title}</h1>
          <p className="article-description-text">{article.description}</p>
        </div>
        {videoUrls.length > 0 ? (
          videoUrls.map((url, index) => (
            <div key={index} className="custom-video-wrapper">
              <video controls>
                <source src={url} type="video/mp4" />
                Your browser does not support the video tag.
              </video>
            </div>
          ))
        ) : (
          <p>No videos available.</p>
        )}
        <div className="text-content-container">{extractedText}</div>

        {/* {article.fileUrl ? (
          <PDFViewer
            pdfUrl={article.fileUrl}
            onTextExtracted={setExtractedText}
          />
        ) : (
          <p>No PDF available.</p>
        )} */}
      </div>
      <Footer />
    </div>
  );
}

export default ArticlePage;
