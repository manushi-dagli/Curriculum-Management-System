import React from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import "./RolesPageStyles.css";

function RolesPage() {

    const [role, setRole] = useState({});

    const selectRole = (role) => {
        localStorage.setItem('role', role);
    };
    
    return (
        <>
            <div className="rolescontainer">
                <form onSubmit={selectRole}>
                    <Link to="/login"><input type="submit" className="roleButton" value="HOD" onClick={() => selectRole("hod")} /></Link>
                    <Link to="/login"><input type="submit" className="roleButton" value="Faculty" onClick={() => selectRole("faculty")}/></Link>
                    <Link to="/login" ><input type="submit" className="roleButton" value="Program Coordinator" onClick={() => selectRole("pc")}/></Link>
                </form>
            </div>
        </>
    );
}

export default RolesPage;