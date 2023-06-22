import axios from "axios";
import { getUserData } from "../Auth";

export const fetchHODAuth = async (navigate) => {
  try {
    let token = "Bearer " + getUserData().token;
    const response = await axios.get("/HOD/isHOD", { headers: { "Authorization": token } });
    if (response.data === "HOD") {}
    else if (response.status === 401) {
        navigate("/roles");
    }
  } catch (error) {
    console.log(error);
      navigate("/roles");
  }
}

export const fetchFacultyAuth = async (navigate) => {
  try {
    let token = "Bearer " + getUserData().token;
    const response = await axios.get("/Faculty/isFaculty", { headers: { "Authorization": token } });
    if (response.data === "Faculty") {}
    else if (response.status === 401) {
        navigate("/roles");
    }
  } catch (error) {
    console.log(error);
      navigate("/roles");
  }
}

export const fetchPCAuth = async (navigate) => {
  try {
    let token = "Bearer " + getUserData().token;
    const response = await axios.get("/PC/isPC", { headers: { "Authorization": token } });
    if (response.data === "PC") {}
    else if (response.status === 401) {
        navigate("/roles");
    }
  } catch (error) {
    console.log(error);
      navigate("/roles");
  }
}