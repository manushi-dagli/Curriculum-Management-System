package com.springboot.CurriculumManagement.Payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.CurriculumManagement.Entities.Department;

public class PCDto {
    private String programCoordinatorId;
    private String ProgramCoordinatorName;
    private String password;
    private String emailId;
    private Department dept;

    public String getProgramCoordinatorId() {
        return programCoordinatorId;
    }

    public void setProgramCoordinatorId(String programCoordinatorId) {
        this.programCoordinatorId = programCoordinatorId;
    }

    public String getProgramCoordinatorName() {
        return ProgramCoordinatorName;
    }

    public void setProgramCoordinatorName(String programCoordinatorName) {
        ProgramCoordinatorName = programCoordinatorName;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
