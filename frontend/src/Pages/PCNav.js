import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import "./NavStyles.css";

function PCNav() {

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
                    <Link to="/PC/Subjects" onClick={handleClick}>All Subject</Link>
                    <Link to="/PC/AddSubject" onClick={handleClick}>Add Subjects</Link>
                    <Link to="/PC/UpdateSubject" onClick={handleClick}>Update Subject</Link>
                    <Link to="/PC/DeleteSubject" onClick={handleClick}>Delete Subject</Link>
                </div>
            </div>
        </>
    );
}

export default PCNav;