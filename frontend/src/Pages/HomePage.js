import React from "react";
import Select from 'react-select';
import { useEffect, useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "./HomePageStyles.css";
import baseurl from "../Components/baseurl";

const customStyles = {
    valueContainer: (base) => ({
        ...base,
        justifyContent: 'left',
    }),
    container: (base) => ({
        ...base,
        width: '80%',
        margin: 'auto',
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


function HomePage() {
    const [years, setYears] = useState([]);
    const [alldept, setAllDept] = useState([]);
    const [selectedYear, setSelectedYear] = useState('');
    const [selectedDept, setSelectedDept] = useState('');
    const [isValid, setIsValid] = useState(false);

    const yearOptions = years.map((year) => ({
        label: year,
        value: year
    }));

    const deptOptions = alldept.map((d) => ({
        value: d,
        label: `${d.deptName}`
    }));


    useEffect(() => {
        axios.get(`${baseurl}/Pdf/getalldept`)
            .then((res) => {
                setAllDept(res.data);
            })
            .catch((err) => {
                console.log(err);
            });

        axios.get(`${baseurl}/Pdf/getalladmissionyears`)
            .then((res) => {
                setYears(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    useEffect(() => {
        setIsValid((selectedDept && selectedYear) ? true : false);
      }, [selectedDept,selectedYear]);

    const getBranchesByYear = async (year) => {
        try {
            axios.get(`${baseurl}/Pdf/getdepartmentsbyadmissionyear/${year}`)
            .then((res) => {
                setAllDept(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
        } catch (error) {
            console.log(error);
        }
    }
    const getYearsByBranch = async (branch) => {
        try {
            axios.get(`${baseurl}/Pdf/getadmissionyearbydeptname/${branch}`)
                .then((res) => {
                    setYears(res.data);
                })
                .catch((err) => {
                    console.log(err);
                });
        } catch (error) {
            console.log(error);
        }
    }

    const handleYearChange = (option) => {
        setSelectedYear(option);
        getBranchesByYear(option);
    };
    const handleBranchChange = (option) => {
        setSelectedDept(option.deptName);
        getYearsByBranch(option.deptName);
    };

    const handleForm = (e) => {
        console.log(selectedYear, selectedDept);
        const formData = new FormData();
        formData.append('admissionYear',selectedYear);
        formData.append('deptName',selectedDept);
        axios.post(`${baseurl}/Pdf/getmergedpdf`, formData, { responseType: 'arraybuffer' })
            .then((res) => {
                console.log(res.data);
                const url = window.URL.createObjectURL(new Blob([res.data]), { type: 'application/pdf' });
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', `${selectedDept}-${selectedYear}-Curriculum.pdf`); //or any other extension
                document.body.appendChild(link);
                link.click();
            })
            .catch((error) => {
                console.log(error);
            });
        e.preventDefault();
    }


    return (
        <>
            <ToastContainer />
            <div className="container">
                <form onSubmit={handleForm} >
                    <h3 className="label">Admission Year:</h3>
                    <Select options={yearOptions} placeholder='Select Year' styles={customStyles}
                        onChange={(e) => { handleYearChange(e.value); }}
                        theme={(theme) => ({
                            ...theme,
                            colors: {
                                ...theme.colors,
                                primary: 'grey',
                            },
                        })}
                    />
                    <h3 className="label">Branch:</h3>
                    <Select options={deptOptions} placeholder='Select Branch' styles={customStyles}
                        onChange={(e) => { handleBranchChange(e.value); }}
                        theme={(theme) => ({
                            ...theme,
                            colors: {
                                ...theme.colors,
                                primary: 'grey',
                            },
                        })} />
                    <input type="submit" disabled={!isValid} className="SubmitButton coolBeans" value="Download" />
                </form>
            </div>
        </>
    );
}

export default HomePage;