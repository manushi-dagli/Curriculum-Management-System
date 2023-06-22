package com.springboot.CurriculumManagement.Entities;


//import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subjects {
    @Id
    private String dduCode;
    @Column
    private Date effectiveDate;
//    @Column(nullable = false, columnDefinition = "DATE DEFAULT '9999-12-31'")
//    private Date removedDate;

    @Column(nullable = false, columnDefinition = "DATE DEFAULT '9999-12-31'")
    private LocalDate removedDate = LocalDate.parse("9999-12-31");
    @Column(nullable = false)
    private int semester;

    //What is sub seq?
    @Column(nullable = false)
    private int subSequence;
    @Column(unique = true)
    private String aicteCode;


    @Column(nullable = false)
    private String subjectName;

    //optional
    @ManyToOne
    @JoinColumn(name = "parentdept")
    private Department parentDept;

    @Column
    private String extraInfo;
    @Column
    private String subjectType;


    @Column
    private String subjectTypeExplanation;
    @Column(columnDefinition = "int default 0")
    private int theoryMarks;
    @Column(columnDefinition = "int default 0")
    private int sessionalMarks;
    @Column(columnDefinition = "int default 0")
    private int termworkMarks;
    @Column(columnDefinition = "int default 0")
    private int practicalMarks;
    @Column(columnDefinition = "int default 0")
    private int totalMarks;
    @Column(columnDefinition = "int default 0")
    private int lectureHours;
    @Column(columnDefinition = "int default 0")
    private int tutorial;

    @Column(columnDefinition = "int default 0")
    private int practicalHours;
    @Column(columnDefinition = "int default 0")
    private int totalHours;
    @Column(columnDefinition = "int default 0")
    private int lectureAndTheoryCredit;
    @Column(columnDefinition = "int default 0")
    private int practicalCredit;
    @Column(columnDefinition = "int default 0")
    private int totalCredit;

    @ManyToOne
    @JoinColumn(name = "dept")
    private Department dept;

//   @ManyToMany(mappedBy = "subjectsList", cascade = { CascadeType.ALL })
//    private List<Faculty> facultyList;

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

//    @ManyToOne
//    @JoinColumn(name = "faculty",nullable = false)
//    private Faculty faculty;

//    public Faculty getFaculty() {
//        return faculty;
//    }
//
//    public void setFaculty(Faculty faculty) {
//        this.faculty = faculty;
//    }

    @Column(nullable = false)
    private String facultyId;

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }
    //    public String getDept() {
//        return dept;
//    }
//
//    public void setDept(String dept) {
//        this.dept = dept;
//    }

    public LocalDate getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(LocalDate removedDate) {
        this.removedDate = removedDate;
    }

    public Subjects() {
        super();
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }



    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSubSequence() {
        return subSequence;
    }

    public void setSubSequence(int subSequence) {
        this.subSequence = subSequence;
    }

    public String getAicteCode() {
        return aicteCode;
    }

    public void setAicteCode(String aicteCode) {
        this.aicteCode = aicteCode;
    }

    public String getdduCode() {
        return dduCode;
    }

    public void setdduCode(String dduCode) {
        this.dduCode = dduCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Department getParentDept() {
        return parentDept;
    }

    public void setParentDept(Department parentDept) {
        this.parentDept = parentDept;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSubjectTypeExplanation() {
        return subjectTypeExplanation;
    }

    public void setSubjectTypeExplanation(String subjectTypeExplanation) {
        this.subjectTypeExplanation = subjectTypeExplanation;
    }

    public int getTheoryMarks() {
        return theoryMarks;
    }

    public void setTheoryMarks(int theoryMarks) {
        this.theoryMarks = theoryMarks;
    }

    public int getSessionalMarks() {
        return sessionalMarks;
    }

    public void setSessionalMarks(int sessionalMarks) {
        this.sessionalMarks = sessionalMarks;
    }

    public int getTermworkMarks() {
        return termworkMarks;
    }

    public void setTermworkMarks(int termworkMarks) {
        this.termworkMarks = termworkMarks;
    }

    public int getPracticalMarks() {
        return practicalMarks;
    }

    public void setPracticalMarks(int practicalMarks) {
        this.practicalMarks = practicalMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }


    public int getTutorial() {
        return tutorial;
    }

    public void setTutorial(int tutorial) {
        this.tutorial = tutorial;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public void setLectureHours(int lectureHours) {
        this.lectureHours = lectureHours;
    }

    public int getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(int practicalHours) {
        this.practicalHours = practicalHours;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getLectureAndTheoryCredit() {
        return lectureAndTheoryCredit;
    }

    public void setLectureAndTheoryCredit(int lectureAndTheoryCredit) {
        this.lectureAndTheoryCredit = lectureAndTheoryCredit;
    }

    public int getPracticalCredit() {
        return practicalCredit;
    }

    public void setPracticalCredit(int practicalCredit) {
        this.practicalCredit = practicalCredit;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }
}
