import React, { useEffect, useState } from "react";

const FileDisplay = () => {
  const [fileUrl, setFileUrl] = useState("");

  useEffect(() => {
    setFileUrl(`http://localhost:9006/api/awareness/content/1`);
  }, []);

  return (
    <>
      {/* Example for displaying PDF */}
      <embed src={fileUrl} width="600" height="500" type="application/pdf" />

      {/* Example for displaying Video */}
      <video width="600" controls>
        <source src={fileUrl} type="video/mp4" />
        Your browser does not support the video tag.
      </video>

      {/* Example for displaying Image */}
      <img src={fileUrl} alt="Awareness Content" width="600" />
    </>
  );
};

export default FileDisplay;
