import React from 'react';
import { motion } from 'framer-motion';
import '../../css/subcomponents/Contactus.css'; // Assuming you have a CSS file for styling

const ContactUs = () => {
  return (
    <div className="Mamcontactus-container">
      <motion.div 
        className="Mamcontactus-header"
        initial={{ opacity: 0, y: -50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        <h2>Contact Us</h2>
        <p>We are here to help farmers and support modern agriculture. Reach out to us!</p>
      </motion.div>

      <motion.div 
        className="Mamcontactus-form"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.8 }}
      >
        <form>
          <div className="Mamcontactusform-group">
            <label>Name</label>
            <input type="text" placeholder="Your Name" required />
          </div>

          <div className="Mamcontactusform-group">
            <label>Email</label>
            <input type="email" placeholder="Your Email" required />
          </div>

          <div className="Mamcontactusform-group">
            <label>Phone Number</label>
            <input type="tel" placeholder="Your Phone Number" required />
          </div>

          <div className="Mamcontactusform-group">
            <label>Inquiry Type</label>
            <select required>
              <option value="general">General Inquiry</option>
              <option value="support">Technical Support</option>
              <option value="partnership">Partnership</option>
              <option value="feedback">Feedback</option>
            </select>
          </div>

          <div className="Mamcontactusform-group">
            <label>Message</label>
            <textarea placeholder="Write your message here" required></textarea>
          </div>

          <button type="submit">Send Message</button>
        </form>
      </motion.div>
    </div>
  );
};

export default ContactUs;
