import React from 'react';
import '../../css/insurance/eligibilitycongrats.css';

function EligibilityCongrats({ message, onClose }) {
  return (
    <div className='congrats-card'>
      <div className='congrats-card-content'>
        <h2>Congratulations!</h2>
        <p>{message}</p>
        <button className='btn btn-primary' onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
}

export default EligibilityCongrats;
