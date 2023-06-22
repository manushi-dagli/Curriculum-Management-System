package com.springboot.CurriculumManagement.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "subject_files")
public class SubjectFile {
    @Lob
    @Column
    private byte[] subjectFileData;


    @Id
    private String subjectDduCode;

    public SubjectFile() {

    }

    public SubjectFile(byte[] subjectFileData, String subjectDduCode) {
        this.subjectFileData = subjectFileData;
        this.subjectDduCode = subjectDduCode;
    }





    public byte[] getSubjectFileData() {
        return subjectFileData;
    }

    public void setSubjectFileData(byte[] subjectFileData) {
        this.subjectFileData = subjectFileData;
    }

    public String getSubjectDduCode() {
        return subjectDduCode;
    }

    public void setSubjectDduCode(String subjectDduCode) {
        this.subjectDduCode = subjectDduCode;
    }
}