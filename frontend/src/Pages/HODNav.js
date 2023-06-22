import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import "./NavStyles.css";

function HODNav() {

    const [clicked, setClicked] = useState(false);

    const handleClick = () => {
        setClicked(!clicked);
    }

    return (
        <>
            <div className={clicked ? "sidenav cont sidenavout" : "sidenav cont sidenavin"}>
                <div className="menu-icon" onClick={handleClick}>
                    <i className={clicked ? "fas fa-times" : "fas fa-bars"}></i>
                </div>
                <div className={clicked ? "navmargin Nav-menu active" : "navmargin Nav-menu hide"}>
                    <Link to="/HOD/Subjects" onClick={handleClick}>All Subjects</Link>
                    <Link to="/HOD/AddFaculty" onClick={handleClick}>Add Faculty</Link>
                    <Link to="/HOD/DeleteFaculty" onClick={handleClick}>Delete Faculty</Link>
                    <Link to="/HOD/AppointPC" onClick={handleClick}>Appoint Program Coordinator</Link>
                </div>
            </div>
        </>
    );
}

export default HODNav;