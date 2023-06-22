package com.springboot.CurriculumManagement.Auth;

import com.springboot.CurriculumManagement.Payloads.FacultyDto;
import com.springboot.CurriculumManagement.Payloads.HODDto;
import com.springboot.CurriculumManagement.Payloads.PCDto;

public class JWTAuthResponse {
    private String token;

    private HODDto hodDto;

    private FacultyDto facultyDto;

    private PCDto pcDto;

    public HODDto getHodDto() {
        return hodDto;
    }

    public void setHodDto(HODDto hodDto) {
        this.hodDto = hodDto;
    }

    public FacultyDto getFacultyDto() {
        return facultyDto;
    }

    public void setFacultyDto(FacultyDto facultyDto) {
        this.facultyDto = facultyDto;
    }

    public PCDto getPcDto() {
        return pcDto;
    }

    public void setPcDto(PCDto pcDto) {
        this.pcDto = pcDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
