import React from 'react'
import Select from 'react-select';
import { useEffect, useState } from "react";
import axios from "axios";
// import "./deletefacultyStyles.css";
import { getUserData } from "../Auth";
import baseurl from "../Components/baseurl";
import { ToastContainer, toast } from 'react-toastify';
import { fetchPCAuth } from './../Components/Verify';
import { useNavigate } from 'react-router-dom';


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

const Deletesubject = () => {
  const navigate = useNavigate();

  let dept=getUserData().pcDto.dept;
  let token;
  const [subject, setSubject] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const subjectsOption = subjects.map((s) => ({
    label: `${s.dduCode} - ${s.subjectName} , sem - ${s.semester}`,
    value: `${s.dduCode} - ${s.subjectName} , sem - ${s.semester}`
  }));








  useEffect(() => {
    try {
      fetchPCAuth(navigate)
      dept = getUserData().pcDto.dept;
      token = "Bearer " + getUserData().token;
      axios.post(`${baseurl}/PC/getallsubjects`, dept, { headers: { Authorization: token } })
        .then((res) => {
          setSubjects(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    } catch (err) { }
  }, []);
  const deletesubjecthandle = (option) => {
    setSubject(option);
  }
  const deletefacultyform = (e) => {
    // e.preventDefault();
    dept = getUserData().pcDto.dept;
    token = "Bearer " + getUserData().token;
    const dduCode = subject.split("-")[0].trim();
    console.log("hello")
    console.log(dduCode)
    // console.log(token);
    const deletesubjectresponse = axios.delete(`${baseurl}/PC/deletesubject/${dduCode}`, { headers: { "Authorization": token } }, dduCode);
    toast.promise(
      deletesubjectresponse,
      {
        pending: {
          render() {
            return "Please Wait!!"
          },
          icon: "✋",
        },
        success: {
          render() {
            return `Subject deleted Successfully!!`
          },
          icon: "🚀",
        },
        error: {
          render({ data }) {
            console.log(data);
            if (data.response.status === 400 || data.response.status === 404 || data.response.status === 401)
              return data.response.data.status;
            return `Something went wrong!!`
          },
          icon: "💥",
        }
      },
      {
        className: 'dark-toast',
        position: toast.POSITION.BOTTOM_RIGHT,
      }
    );

  }
  return (
    <div>
      <ToastContainer />

      <div className="container">
        <form onSubmit={deletefacultyform} >

          <h3 className="label margint">Subjects:</h3>
          <Select options={subjectsOption} placeholder='Select subject to delete' styles={customStyles}
            onChange={(e) => { deletesubjecthandle(e.value); }}
            theme={(theme) => ({
              ...theme,
              colors: {
                ...theme.colors,
                primary: 'grey',
              },
            })}
          />
          <button type="submit" className="SubmitButton coolBeans margint">Delete</button>
        </form>
      </div>
    </div>
  )
}

export default Deletesubject;
