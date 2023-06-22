import React from "react";
import { useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import { doLogin } from "./../Auth/index.js";
import 'react-toastify/dist/ReactToastify.css';
import "./LoginPageStyles.css";
import baseurl from "./../Components/baseurl";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../Auth/AuthContext";



function LoginPage() {

    let role = localStorage.getItem('role');
    const navigate = useNavigate();
    const {LOGIN} = useAuth();

    const loginfun = (data) => {
        const returndata = axios.post(`${baseurl}/api/v1/auth/login`, data).then((response) => {
            doLogin(response.data, function () {
                console.log("login details saved in localstorage");
                LOGIN();
                if (role === "hod"){
                    navigate("/HOD/Subjects");}
                else if (role === "faculty")
                    navigate("/Faculty/Subjects");
                else if (role === "pc")
                    navigate("/PC/Subjects");
            });
        });
        toast.promise(
            returndata,
            {
                pending: {
                    render() {
                        return "Please Wait!!"
                    },
                    icon: "✋",
                },
                success: {
                    render({ data }) {
                        return `Login Successfully!!`
                    },
                    icon: "🚀",
                },
                error: {
                    render({ data }) {
                        console.log(data);
                        if (data.response.status === 400 || data.response.status === 404)
                            return data.response.data.status;
                        return data.response.data.message
                    },
                    icon: "💥",
                }
            },
            {
                className: 'dark-toast',
                position: toast.POSITION.BOTTOM_RIGHT,
            }
        )
    }
    const [login, setLogin] = useState({ role: role, id: "", password: "" });
    const loginForm = (e) => {
        console.log(login);
        e.preventDefault();

        if (login.id === '' || login.password === '') {
            toast.error("Please fill all the fields");
            return;
        }

        loginfun(login);

    }

    return (
        <>
            <ToastContainer />
            <div className="container">
                <form onSubmit={loginForm} >
                    <h3 className="label">ID:</h3>
                    <input type="text" onChange={(e) => { setLogin({ ...login, id: e.target.value }) }} value={login.id} />
                    <h3 className="label">Password:</h3>
                    <input type="password" onChange={(e) => { setLogin({ ...login, password: e.target.value }) }} value={login.password} /><br/>
                    <input type="submit" className="SubmitButton coolBeans login" value="Login"></input>
                </form>
            </div>
        </>
    );
}

export default LoginPage;