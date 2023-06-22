import React, { useEffect, useState } from 'react';
import Select from 'react-select';
import { Viewer } from '@react-pdf-viewer/core'; // install this library
import { defaultLayoutPlugin } from '@react-pdf-viewer/default-layout'; // install this library
import { Worker } from '@react-pdf-viewer/core';
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';
import baseurl from "../Components/baseurl";
import { getUserData } from "../Auth";
import "./subjectdetailsStyles.css";
import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/default-layout/lib/styles/index.css';
import OnHoverScrollContainer from "./../Components/CustomeScroll";
import { fetchFacultyAuth } from './../Components/Verify';
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


const FacultySubjectdetails = () => {
    const navigate = useNavigate();

    let dept;
    let token;
    const [facultySubject, setFacultySubject] = useState(JSON.parse(localStorage.getItem('facultysubject')) || []);

    const defaultLayoutPluginInstance = defaultLayoutPlugin();
    const [seq, setSeq] = useState([]);
    const [alldept, setAllDept] = useState([]);
    const [pdfFile, setPdfFile] = useState(null);
    const [pdfFileError, setPdfFileError] = useState('');
    const [viewPdf, setViewPdf] = useState(null);
    const [selectedPDFFile, setSelectedPDFFile] = useState(null);

    const handlePdfFileView = () => {
        if (selectedPDFFile) {
            const blob = new Blob([selectedPDFFile], { type: 'application/pdf' });
            let reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onloadend = (e) => {
                console.log(e.target.result)
                setViewPdf(e.target.result);
                setPdfFileError('');
            }
        }
        else {
            setViewPdf(null);
        }
    }


    useEffect(() => {
        try {
            fetchFacultyAuth(navigate)
            dept = getUserData().facultyDto.dept;
            token = "Bearer " + getUserData().token;
            axios.get(`${baseurl}/Faculty/getremainingsubsequence/${facultySubject.semester}`, { headers: { "Authorization": token } }, facultySubject.semester)
                .then((res) => {
                    const arr = res.data;
                    arr.push(facultySubject.subSequence);
                    arr.sort((a, b) => a - b);
                    setSeq(arr);
                })
                .catch((err) => {
                    console.log(err);
                });

            axios.get(`${baseurl}/Faculty/getalldept`, { headers: { "Authorization": token } })
                .then((res) => {
                    setAllDept(res.data);
                })
                .catch((err) => {
                    console.log(err);
                })

            axios.get(`${baseurl}/Faculty/getPdf/${facultySubject.dduCode}`, { headers: { "Authorization": token }, responseType: "arraybuffer" }, facultySubject.dduCode)
                .then((res) => {
                    setSelectedPDFFile(res.data);
                })
                .catch((err) => {
                    console.log(err);
                })
        } catch (err) { }
    }, []);
    useEffect(() => {
        handlePdfFileView();
    }, [selectedPDFFile]);


    const semOptions = [
        { value: "1", label: "1" },
        { value: "2", label: "2" },
        { value: "3", label: "3" },
        { value: "4", label: "4" },
        { value: "5", label: "5" },
        { value: "6", label: "6" },
        { value: "7", label: "7" },
        { value: "8", label: "8" },
    ];
    const subSequenceOptions = seq.map((s) => ({
        value: s,
        label: JSON.stringify(s)
    }));
    const deptOptions = alldept.map((d) => ({
        value: d,
        label: `${d.deptName}`
    }));

    const fileType = ['application/pdf'];

    const handlePdfFileChange = (e) => {
        setPdfFile(e.target.files[0]);
        if (e.target.files[0]) {
            if (e.target.files[0] && fileType.includes(e.target.files[0].type)) {
                let reader = new FileReader();
                reader.readAsDataURL(e.target.files[0]);
                reader.onloadend = (e) => {
                    setSelectedPDFFile(e.target.result);
                    setPdfFileError('');
                }
            }
            else {
                setSelectedPDFFile(null);
                setPdfFileError('Please select valid pdf file');
            }
        }
        else {
            console.log('select your file');
        }
    }

    const defaultsem = () => {
        return semOptions[semOptions.findIndex(option => option.label === JSON.stringify(facultySubject.semester))]
    }

    const defaultseq = () => {
        return subSequenceOptions[subSequenceOptions.findIndex(option => option.label === JSON.stringify(facultySubject.subSequence))]
    }

    const defaultdept = () => {
        return deptOptions[deptOptions.findIndex(option => option.value === facultySubject.dept)];
    }

    const updatesubjectform = async (e) => {
        e.preventDefault();
        console.log(pdfFile);

        dept = getUserData().facultyDto.dept;
        token = "Bearer " + getUserData().token;

        const formData = new FormData();
        if (pdfFile !== null) {
            formData.append("file", pdfFile);
            formData.append("dduCode", facultySubject.dduCode);
            const uploadres = axios.post(`${baseurl}/Faculty/uploadsubjectfile`, formData, { headers: { "Content-Type": "multipart/form-data", "Authorization": token } });
            toast.promise(
                uploadres,
                {
                    pending: {
                        render() {
                            return "Please Wait!!"
                        },
                        icon: "✋",
                    },
                    success: {
                        render() {
                            return `Subject PDF stored Successfully!!`
                        },
                        icon: "🚀",
                    },
                    error: {
                        render({ data }) {
                            console.log(data);
                            if (data.response.status === 400 || data.response.status === 404 || data.response.status === 401)
                                return data.response.data.status;
                            return 'Internal server error!!';
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



        const updateres = axios.post(`${baseurl}/Faculty/savesubjectdetails`, facultySubject, { headers: { "Authorization": token } });
        toast.promise(
            updateres,
            {
                pending: {
                    render() {
                        return "Please Wait!!"
                    },
                    icon: "✋",
                },
                success: {
                    render() {
                        return `Subject Details stored Successfully!!`
                    },
                    icon: "🚀",
                },
                error: {
                    render({ data }) {
                        console.log(data);
                        if (data.response.status === 400 || data.response.status === 404 || data.response.status === 401)
                            return data.response.data.status;
                        return 'Internal server error!!';
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
            <div className="subjectdetailcontainer">
                <OnHoverScrollContainer>
                    <form onSubmit={updatesubjectform} encType='multipart/form-data'>
                        <div className='inline'>
                            <div className='block1'>
                                <h3 className="label margint gap3">Subject Name:</h3>
                                <input type="text" onChange={(e) => { setFacultySubject({ ...facultySubject, subjectName: e.target.value }) }} value={facultySubject.subjectName || ''} />
                            </div>
                            <div className='block1'>
                                <h3 className="label margint gap3">Semester:</h3>
                                <Select options={semOptions} placeholder='Select semester' styles={customStyles}
                                    value={defaultsem()}
                                    onChange={(e) => { setFacultySubject({ ...facultySubject, semester: e.value }) }}
                                    theme={(theme) => ({
                                        ...theme,
                                        colors: {
                                            ...theme.colors,
                                            primary: 'grey',
                                        },
                                    })}
                                />
                            </div>
                            <div className='block1'>
                                <h3 className="label margint gap3">Sub Sequence:</h3>
                                <Select options={subSequenceOptions} placeholder='Select sequence' styles={customStyles}
                                    value={defaultseq()}
                                    onChange={(e) => { setFacultySubject({ ...facultySubject, subSequence: e.value }) }}
                                    theme={(theme) => ({
                                        ...theme,
                                        colors: {
                                            ...theme.colors,
                                            primary: 'grey',
                                        },
                                    })}
                                />
                            </div>
                        </div>
                        <div className='inline'>
                            <div className='block2'>
                                <h3 className="label margint">Subject Type:</h3>
                                <input type="text" onChange={(e) => { setFacultySubject({ ...facultySubject, subjectType: e.target.value }) }} value={facultySubject.subjectType || ''} />
                            </div>
                            <div className='block2'>
                                <h3 className="label margint">Subject Type Explanation:</h3>
                                <input type="text" onChange={(e) => { setFacultySubject({ ...facultySubject, subjectTypeExplanation: e.target.value }) }} value={facultySubject.subjectTypeExplanation || ''} />
                            </div>
                        </div>
                        <div className='inline'>
                            <div className='block3'>
                                <h3 className="label margint">Theory Marks:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, theoryMarks: e.target.value }) }} value={facultySubject.theoryMarks || ''} />
                            </div>
                            <div className='block3'>
                                <h3 className="label margint">Sessional Marks:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, sessionalMarks: e.target.value }) }} value={facultySubject.sessionalMarks || ''} />
                            </div>
                            <div className='block3'>
                                <h3 className="label margint">Termwork Marks:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, termworkMarks: e.target.value }) }} value={facultySubject.termworkMarks || ''} />
                            </div>
                            <div className='block3'>
                                <h3 className="label margint">Practical Marks:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, practicalMarks: e.target.value }) }} value={facultySubject.practicalMarks || ''} />
                            </div>
                            <div className='block3'>
                                <h3 className="label margint">Total Marks:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, totalMarks: e.target.value }) }} value={facultySubject.totalMarks || ''} />
                            </div>
                        </div>
                        <div className='inline'>
                            <div className='block4'>
                                <h3 className="label margint">Lecture Hours:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, lectureHours: e.target.value }) }} value={facultySubject.lectureHours || ''} />
                            </div>
                            <div className='block4'>
                                <h3 className="label margint">Tutorial:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, tutorial: e.target.value }) }} value={facultySubject.tutorial || ''} />
                            </div>
                            <div className='block4'>
                                <h3 className="label margint">Practical Hours:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, practicalHours: e.target.value }) }} value={facultySubject.practicalHours || ''} />
                            </div>
                            <div className='block4'>
                                <h3 className="label margint">Total Hours:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, totalHours: e.target.value }) }} value={facultySubject.totalHours || ''} />
                            </div>
                        </div>
                        <div className='inline'>
                            <div className='block1'>
                                <h3 className="label margint">Lecture And Theory Credit:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, lectureAndTheoryCredit: e.target.value }) }} value={facultySubject.lectureAndTheoryCredit || ''} />
                            </div>
                            <div className='block1'>
                                <h3 className="label margint">Practical Credit:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, practicalCredit: e.target.value }) }} value={facultySubject.practicalCredit || ''} />
                            </div>
                            <div className='block1'>
                                <h3 className="label margint">Total Credit:</h3>
                                <input type="number" onChange={(e) => { setFacultySubject({ ...facultySubject, totalCredit: e.target.value }) }} value={facultySubject.totalCredit || ''} />
                            </div>
                        </div>


                        <h3 className="label margint">Extra Info:</h3>
                        <input type="text" onChange={(e) => { setFacultySubject({ ...facultySubject, extraInfo: e.target.value }) }} value={facultySubject.extraInfo || ''} />


                        <input type="file" accept='.pdf' className='form-control margint' onChange={handlePdfFileChange} />

                        {pdfFileError && <div className='error-msg'>{pdfFileError}</div>}
                        <br />
                        <button type="submit" className="SubmitButton coolBeans margint">Update</button>
                    </form>


                    {/* <h4>View PDF</h4>
                    <div className='pdf-container'>
                        {viewPdf && <Worker workerUrl="https://unpkg.com/pdfjs-dist@3.4.120/build/pdf.worker.min.js">
                            <Viewer fileUrl={viewPdf} plugins={[defaultLayoutPluginInstance]} />
                        </Worker>}
                        {!viewPdf && <>No pdf file selected</>}
                    </div> */}
                </OnHoverScrollContainer>
            </div>
        </div >
    )
}

export default FacultySubjectdetails
