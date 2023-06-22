package com.springboot.CurriculumManagement.Entities;

//import jakarta.persistence.*;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Department")
public class Department {
    @Id
    private String deptId;
    @Column(unique = true,nullable = false)
    private String deptName;

    @Column(nullable = false)
    private int startYear;

    @Column
    private String endYear;

    @OneToOne(mappedBy = "dept")
    private ProgramCoordinator programCoordinator;

    @OneToOne(mappedBy = "dept")
    private HOD hod;

    @OneToMany(mappedBy = "dept")
    private List<Faculty> faculty;

    @OneToMany(mappedBy = "dept")
    private List<Subjects> subjectsList;

    @OneToMany(mappedBy = "parentDept")
    private List<Subjects> subjectsListForParentDept;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Department() {
        super();
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }
}
