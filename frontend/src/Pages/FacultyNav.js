import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import "./NavStyles.css";

function FacultyNav() {

    const [clicked, setClicked] = useState(false);

    const handleClick = () => {
        setClicked(!clicked);
    }

    return (
        <>
            <div className={clicked ?  "sidenav cont sidenavout" : "sidenav cont sidenavin"}>
                <div className="menu-icon" onClick={handleClick}>
                    <i className={clicked ? "fas fa-times" : "fas fa-bars"}></i>
                </div>
                <div className={clicked ? "navmargin Nav-menu active" : "navmargin Nav-menu hide"}>
                    <Link to="/Faculty/Subjects" onClick={handleClick}>My Subjects</Link>
                    <Link to="/Faculty/UpdateMySubject" onClick={handleClick}>Update My Subject</Link>
                </div>
            </div>
        </>
    );
}

export default FacultyNav;