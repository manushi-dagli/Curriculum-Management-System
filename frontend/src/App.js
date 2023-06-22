import React from "react";
import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';
import './App.css';
import { AuthProvider } from "./Auth/AuthContext";
import Background from "./Components/Background";
import Navbar from "./Components/nav";
import RolesPage from "./Pages/RolesPage";
import HomePage from "./Pages/HomePage";
import LoginPage from "./Pages/LoginPage";
import HODNav from "./Pages/HODNav";
import FacultyNav from "./Pages/FacultyNav";
import PCNav from "./Pages/PCNav";
import Subjects from "./Pages/Subjects";
import Addfaculty from "./Pages/addfaculty";
import Deletefaculty from "./Pages/deletefaculty";
import AppointPC from "./Pages/appointPC";
import Addsubject from "./Pages/addsubject";
import Deletesubject from "./Pages/deletesubject";
import UpdateMySubject from "./Pages/UpdateMySubject";
import Updatesubject from "./Pages/updatesubject";
import PCSubjectdetails from "./Pages/pcsubjectdetails";
import Logs from "./Pages/logs";
import FacultySubjectdetails from "./Pages/Facultysubjectdetails";

function App() {
  return (
    <div>
      <BrowserRouter>
        <AuthProvider>
          <Navbar />
          <Routes>
            <Route exact path="/" element={<HomePage />} />
            <Route exact path="/login" element={<LoginPage />} />
            <Route exact path="/roles" element={<RolesPage />} />
            <Route exact path="HOD" element={
              <>
                <HODNav />
                <Outlet />
              </>
            } >
              <Route exact path="Subjects" element={<Subjects />} />
              <Route exact path="AddFaculty" element={<Addfaculty />} />
              <Route exact path="DeleteFaculty" element={<Deletefaculty />} />
              <Route exact path="AppointPC" element={<AppointPC />} />
              <Route exact path="logs" element={<Logs />} />
            </Route>
            <Route exact path="Faculty" element={
              <>
                <FacultyNav />
                <Outlet />
              </>
            } >
              <Route exact path="Subjects" element={<Subjects />} />
              <Route exact path="UpdateMySubject" element={<UpdateMySubject />} />
              <Route exact path="FacultySubjectDetails" element={<FacultySubjectdetails />} />
            </Route>
            <Route exact path="PC" element={
              <>
                <PCNav />
                <Outlet />
              </>
            } >
              <Route exact path="Subjects" element={<Subjects />} />
              <Route exact path="AddSubject" element={<Addsubject />} />
              <Route exact path="DeleteSubject" element={<Deletesubject />} />
              <Route exact path="UpdateSubject" element={<Updatesubject />} />
              <Route exact path="PCSubjectDetails" element={<PCSubjectdetails />} />
            </Route>
          </Routes>
        </AuthProvider>
        <Background />
      </BrowserRouter>

    </div>
  );
}

export default App;
