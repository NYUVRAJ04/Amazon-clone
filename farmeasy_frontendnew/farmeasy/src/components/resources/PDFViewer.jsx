import React, { useEffect } from "react";
import { Worker, Viewer } from "@react-pdf-viewer/core";
import "@react-pdf-viewer/core/lib/styles/index.css";
import "@react-pdf-viewer/default-layout/lib/styles/index.css";
import * as pdfjsLib from "pdfjs-dist";
import "../../css/resources/pdfviewer.css";

// Set the workerSrc to the CDN path
pdfjsLib.GlobalWorkerOptions.workerSrc = `https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js`;

function PDFViewer({ pdfUrl, onTextExtracted }) {
  useEffect(() => {
    const fetchPdfText = async () => {
      try {
        const loadingTask = pdfjsLib.getDocument(pdfUrl);
        const pdf = await loadingTask.promise;
        const numPages = pdf.numPages;
        let textContent = "";

        for (let pageNum = 1; pageNum <= numPages; pageNum++) {
          const page = await pdf.getPage(pageNum);
          const textContentPage = await page.getTextContent();
          let lastY,
            text = "";
          textContentPage.items.forEach((item) => {
            if (lastY === item.transform[5] || !lastY) {
              text += item.str;
            } else {
              text += "\n" + item.str;
            }
            lastY = item.transform[5];
          });
          textContent += text + "\n";
        }

        onTextExtracted(textContent);
      } catch (error) {
        console.error("Error fetching PDF text:", error);
      }
    };

    fetchPdfText();
  }, [pdfUrl, onTextExtracted]);

  const handleDownload = () => {
    window.open(pdfUrl, "_blank");
  };

  return (
    <div className="pdf-viewer-container">
      <Worker
        workerUrl={`https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js`}
      >
        <div className="pdf-viewer">
          <Viewer fileUrl={pdfUrl} />
        </div>
      </Worker>
      <button className="download-button" onClick={handleDownload}>
        Download PDF
      </button>
    </div>
  );
}

export default PDFViewer;
