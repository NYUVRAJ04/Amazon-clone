import React from "react";
import { FaUser } from "react-icons/fa";

function AdminHeader() {
  return (
    <div className="admin-header1">
      <header>
        <h4>Admin Dashboard</h4>
        <div>
          <FaUser />
        </div>
      </header>
    </div>
  );
}

export default AdminHeader;
