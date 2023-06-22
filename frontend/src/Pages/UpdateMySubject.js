import React from 'react'
import Select from 'react-select';
import { useEffect, useState } from "react";
import axios from "axios";
// import "./deletefacultyStyles.css";
import { getUserData } from "../Auth";
import baseurl from "../Components/baseurl";
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from "react-router-dom";
import { fetchFacultyAuth } from './../Components/Verify';

const customStyles = {
    valueContainer: (base) => ({
        ...base,
        justifyContent: 'left',
    }),
    container: (base) => ({
        ...base,
        width: '80%',
        margin: 'auto'
    }),
    control: (base) => ({
        ...base,
        maxHeight: "50px",
        cursor: "pointer",
    }),
    option: (base, state) => ({
        ...base,
        color: state.isSelected ? "white" : "black",
        backgroundColor: state.isSelected ? "black" : "white",
        cursor: "pointer",
        borderRadius: 3,
        '&:hover': {
            backgroundColor: "grey",
            color: "white",
            borderRadius: 0,
        }
    })
};

const UpdateMySubject = () => {

    const navigate = useNavigate();

    let dept=getUserData().facultyDto.dept;
    let token;
    let faculty=getUserData().facultyDto;

    const [subjects, setSubjects] = useState([]);
    const [facultySubject, setFacultySubject] = useState({});
    const subjectsOption = subjects.map((s) => ({
        label: `${s.dduCode} - ${s.subjectName} , sem - ${s.semester}`,
        value: `${s.dduCode} - ${s.subjectName} , sem - ${s.semester}`
    }));

    useEffect(() => {
        try {
            fetchFacultyAuth(navigate)
            dept = getUserData().facultyDto.dept;
            token = "Bearer " + getUserData().token;
            console.log(faculty)
            
            axios.post(`${baseurl}/Faculty/getallmysubjects`, faculty, { headers: { "Authorization": token } })
                .then((res) => {
                    setSubjects(res.data);
                })
                .catch((err) => {
                    console.log(err);
                });
        } catch (err) { }
    }, []);

    const searchsubjectById = (subjectid) => {
        return subjects.find((subject) => subject.dduCode === subjectid);
    };

    const updatesubjecthandle = (option) => {
        const subjectid = option.split("-")[0].trim();
        setFacultySubject(searchsubjectById(subjectid));
        localStorage.setItem('facultysubject', JSON.stringify(searchsubjectById(subjectid)));
    }

    const updatesubjectform = (e) => {
        navigate("/Faculty/FacultySubjectDetails");
    }

    return (
        <div>
            <ToastContainer />
            <div className="container">
                <form onSubmit={updatesubjectform} >
                    <h3 className="label margint">Subjects:</h3>
                    <Select options={subjectsOption} placeholder='Select subject to update' styles={customStyles}
                        onChange={(e) => { updatesubjecthandle(e.value); }}
                        theme={(theme) => ({
                            ...theme,
                            colors: {
                                ...theme.colors,
                                primary: 'grey',
                            },
                        })}
                    />
                    <button type="submit" className="SubmitButton coolBeans margint">Update</button>
                </form>
            </div>
        </div>
    )
}

export default UpdateMySubject
