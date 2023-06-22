import React, { useEffect, useState } from 'react';
import Select from 'react-select';
import { ToastContainer, toast } from 'react-toastify';
import { Viewer } from '@react-pdf-viewer/core'; // install this library
import { defaultLayoutPlugin } from '@react-pdf-viewer/default-layout'; // install this library
import { Worker } from '@react-pdf-viewer/core';
import axios from 'axios';
import baseurl from "../Components/baseurl";
import { getUserData } from "../Auth";
import "./subjectdetailsStyles.css";
import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/default-layout/lib/styles/index.css';
import OnHoverScrollContainer from "./../Components/CustomeScroll";
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


const PCSubjectdetails = () => {
  const navigate = useNavigate();

  let dept;
  let token;

  const [pcSubject, setPCSubject] = useState(JSON.parse(localStorage.getItem('pcsubject')) || []);

  const defaultLayoutPluginInstance = defaultLayoutPlugin();
  const [seq, setSeq] = useState([]);
  const [alldept, setAllDept] = useState([]);
  const [faculties, setFaculties] = useState([]);
  const [viewPdf, setViewPdf] = useState(null);
  const [selectedPDFFile, setSelectedPDFFile] = useState(null);
  const [pdfFileError, setPdfFileError] = useState('');


  useEffect(() => {
    try {
      fetchPCAuth(navigate)
      dept = getUserData().pcDto.dept;
      token = "Bearer " + getUserData().token;
      axios.post(`${baseurl}/PC/getallfaculty`, dept, { headers: { "Authorization": token } })
        .then((res) => {
          setFaculties(res.data);
        })
        .catch((err) => {
          console.log(err);
        });

      axios.get(`${baseurl}/PC/getremainingsubsequence/${pcSubject.semester}`, { headers: { "Authorization": token } }, pcSubject.semester)
        .then((res) => {
          const arr = res.data;
          arr.push(pcSubject.subSequence);
          arr.sort((a, b) => a - b);
          setSeq(arr);
        })
        .catch((err) => {
          console.log(err);
        });

      axios.get(`${baseurl}/PC/getalldept`, { headers: { "Authorization": token } })
        .then((res) => {
          setAllDept(res.data);
        })
        .catch((err) => {
          console.log(err);
        })
    } catch (err) { }
  }, []);

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
    value: JSON.stringify(s),
    label: JSON.stringify(s)
  }));
  const deptOptions = alldept.map((d) => ({
    value: d,
    label: `${d.deptName}`
  }));
  const facultyOptions = faculties.map((f) => ({
    label: `${f.facultyId} - ${f.facultyName}`,
    value: f
  }));

  const defaultsem = () => {
    return semOptions[semOptions.findIndex(option => option.value === JSON.stringify(pcSubject.semester))]
  }

  const defaultseq = () => {
    return subSequenceOptions[subSequenceOptions.findIndex(option => option.value === JSON.stringify(pcSubject.subSequence))]
  }

  const defaultfaculty = () => {
    return facultyOptions[facultyOptions.findIndex(option => option.value.facultyId === pcSubject.facultyId)]
  }

  const defaultdept = () => {
    return deptOptions[deptOptions.findIndex(option => option.value === pcSubject.dept)];
  }

  const handleFacultyChange = (selectedOption) => {
    setPCSubject({ ...pcSubject, facultyId: selectedOption.value.facultyId });
  };


  const updatesubjectform = (e) => {
    e.preventDefault();
    console.log(pcSubject)
    dept = getUserData().pcDto.dept;
    token = "Bearer " + getUserData().token;
    const updateres = axios.post(`${baseurl}/PC/savesubjectdetails`, pcSubject, { headers: { "Authorization": token } });
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
          <form onSubmit={updatesubjectform} >
            <div className='inline'>
              <div className='block1'>
                <h3 className="label margint gap3">Subject Name:</h3>
                <input type="text" onChange={(e) => { setPCSubject({ ...pcSubject, subjectName: e.target.value }) }} value={pcSubject.subjectName || ''} />
              </div>
              <div className='block1'>
                <h3 className="label margint gap3">Semester:</h3>
                <Select options={semOptions} placeholder='Select semester' styles={customStyles}
                  value={defaultsem()}
                  onChange={(e) => { setPCSubject({ ...pcSubject, semester: e.value }) }}
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
                  onChange={(e) => { setPCSubject({ ...pcSubject, subSequence: e.value }) }}
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
              <div className='block1'>
                <h3 className="label margint">AICTE Code:</h3>
                <input type="text" onChange={(e) => { setPCSubject({ ...pcSubject, aicteCode: e.target.value }) }} value={pcSubject.aicteCode || ''} />
              </div>
              <div className='block1'>
                <h3 className="label margint">Effective Date:</h3>
                <input type="date" onChange={(e) => { setPCSubject({ ...pcSubject, effectiveDate: e.target.value }) }} value={pcSubject.effectiveDate || ''} />
              </div>
              <div className='block1'>
                <h3 className="label margint">Removed Date:</h3>
                <input type="date" onChange={(e) => { setPCSubject({ ...pcSubject, removedDate: e.target.value }) }} value={pcSubject.removedDate || ''} />
              </div>
            </div>
            <div className='inline'>
              <div className='block2'>
                <h3 className="label margint">Subject Type:</h3>
                <input type="text" onChange={(e) => { setPCSubject({ ...pcSubject, subjectType: e.target.value }) }} value={pcSubject.subjectType || ''} />
              </div>
              <div className='block2'>
                <h3 className="label margint">Subject Type Explanation:</h3>
                <input type="text" onChange={(e) => { setPCSubject({ ...pcSubject, subjectTypeExplanation: e.target.value }) }} value={pcSubject.subjectTypeExplanation || ''} />
              </div>
            </div>
            <div className='inline'>
              <div className='block3'>
                <h3 className="label margint">Theory Marks:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, theoryMarks: e.target.value }) }} value={pcSubject.theoryMarks || ''} />
              </div>
              <div className='block3'>
                <h3 className="label margint">Sessional Marks:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, sessionalMarks: e.target.value }) }} value={pcSubject.sessionalMarks || ''} />
              </div>
              <div className='block3'>
                <h3 className="label margint">Termwork Marks:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, termworkMarks: e.target.value }) }} value={pcSubject.termworkMarks || ''} />
              </div>
              <div className='block3'>
                <h3 className="label margint">Practical Marks:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, practicalMarks: e.target.value }) }} value={pcSubject.practicalMarks || ''} />
              </div>
              <div className='block3'>
                <h3 className="label margint">Total Marks:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, totalMarks: e.target.value }) }} value={pcSubject.totalMarks || ''} />
              </div>
            </div>
            <div className='inline'>
              <div className='block4'>
                <h3 className="label margint">Lecture Hours:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, lectureHours: e.target.value }) }} value={pcSubject.lectureHours || ''} />
              </div>
              <div className='block4'>
                <h3 className="label margint">Tutorial:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, tutorial: e.target.value }) }} value={pcSubject.tutorial || ''} />
              </div>
              <div className='block4'>
                <h3 className="label margint">Practical Hours:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, practicalHours: e.target.value }) }} value={pcSubject.practicalHours || ''} />
              </div>
              <div className='block4'>
                <h3 className="label margint">Total Hours:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, totalHours: e.target.value }) }} value={pcSubject.totalHours || ''} />
              </div>
            </div>
            <div className='inline'>
              <div className='block1'>
                <h3 className="label margint">Lecture And Theory Credit:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, lectureAndTheoryCredit: e.target.value }) }} value={pcSubject.lectureAndTheoryCredit || ''} />
              </div>
              <div className='block1'>
                <h3 className="label margint">Practical Credit:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, practicalCredit: e.target.value }) }} value={pcSubject.practicalCredit || ''} />
              </div>
              <div className='block1'>
                <h3 className="label margint">Total Credit:</h3>
                <input type="number" onChange={(e) => { setPCSubject({ ...pcSubject, totalCredit: e.target.value }) }} value={pcSubject.totalCredit || ''} />
              </div>
            </div>
            <div>
              <h3 className="label margint">Extra Info:</h3>
              <input type="text" onChange={(e) => { setPCSubject({ ...pcSubject, extraInfo: e.target.value }) }} value={pcSubject.extraInfo || ''} />
            </div>
            <div>
              <h3 className="label margint">Faculty:</h3>
              <Select options={facultyOptions} placeholder='Select faculty' styles={customStyles}
                value={defaultfaculty()}
                onChange={handleFacultyChange}
                theme={(theme) => ({
                  ...theme,
                  colors: {
                    ...theme.colors,
                    primary: 'grey',
                  },
                })}
              />
            </div>
            <button type="submit" className="SubmitButton coolBeans margint">Update</button>
          </form>
        </OnHoverScrollContainer>
      </div>
    </div >
  )
}

export default PCSubjectdetails
