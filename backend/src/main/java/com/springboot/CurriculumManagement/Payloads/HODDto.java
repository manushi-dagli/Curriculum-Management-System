package com.springboot.CurriculumManagement.Payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.CurriculumManagement.Entities.Department;

public class HODDto {
    private String HODId;
    private String HODName;
    private String password;
    private String emailId;
    private Department dept;

    public String getHODId() {
        return HODId;
    }

    public void setHODId(String HODId) {
        this.HODId = HODId;
    }

    public String getHODName() {
        return HODName;
    }

    public void setHODName(String HODName) {
        this.HODName = HODName;
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
