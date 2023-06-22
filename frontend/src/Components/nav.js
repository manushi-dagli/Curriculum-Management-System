import React from "react";
import { Navigate, NavLink } from "react-router-dom";
import { useEffect, useState } from "react";
import './navstyle.css';
import { doLogout, getUserData, isLoggedIn } from "../Auth";
import { useAuth } from "../Auth/AuthContext";


function Navbar() {

    const [userlink, setUserlink] = useState("");
    const [user, setUser] = useState([]);
    const [clicked, setClicked] = useState(false);
    const { ISLOGGEDIN, USER, LOGOUT } = useAuth();

    const handledashboard = () => {
        handleClick();
        if (isLoggedIn()) {
            <Navigate to={`/${userlink}/Subjects`} />
        }
    }

    const handlerole = () => {
        console.log(USER);
        if (USER.hodDto === null && USER.facultyDto === null)
            setUserlink("PC");
        else if (USER.pcDto === null && USER.facultyDto === null)
            setUserlink("HOD");
        else
            setUserlink("Faculty");
    }

    const handleClick = () => {
        setClicked(!clicked);
    }

    const handlelogout = () => {
        doLogout();
        handleClick();
        LOGOUT();
    }

    const handlelogin = () => {
        handleClick();
    }

    let dashlink = ISLOGGEDIN ? `/${userlink}/Subjects` : "/roles";
    let loglink = ISLOGGEDIN ? "/" : "/roles";
    let logtitle = ISLOGGEDIN ? "Logout" : "Authorized Login";
    let loghandler = ISLOGGEDIN ? handlelogout : handlelogin;

    useEffect(() => {
        setUser(getUserData());
        if (ISLOGGEDIN) handlerole();
        dashlink = ISLOGGEDIN ? `/${userlink}/Subjects` : "/roles";
        loglink = ISLOGGEDIN ? "/" : "/roles";
        logtitle = ISLOGGEDIN ? "Logout" : "Authorized Login";
        loghandler = ISLOGGEDIN ? handlelogout : handlelogin;
    }, [ISLOGGEDIN]);



    return (
        <>
            <nav className="NavbarItem">
                <h2 className="logo">
                    Curriculum Management
                </h2>
                <div className="menu-icon" onClick={handleClick}>
                    <i className={clicked ? "fas fa-times" : "fas fa-bars"}></i>
                </div>
                <ul className={clicked ? "nav-menu active" : "nav-menu"}>
                    <li>
                        <NavLink to={dashlink} onClick={handledashboard} className="nav-links-mobile coolBeans">Dashboard</NavLink>
                    </li>
                    <li>
                        <NavLink to={loglink} onClick={loghandler} className="nav-links-mobile coolBeans">{logtitle}</NavLink>
                    </li>
                </ul>

            </nav>
        </>
    )
}

export default Navbar;