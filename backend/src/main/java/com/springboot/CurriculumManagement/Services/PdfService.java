package com.springboot.CurriculumManagement.Services;

import com.springboot.CurriculumManagement.Entities.Subjects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface PdfService {
    public ByteArrayInputStream createPdf(int admissionYear,String deptname,List<Subjects>subjectsList);

    ByteArrayInputStream getMergedPdfsFromDB(List<Subjects> subjectsList) throws IOException;

    public ByteArrayInputStream mergePdfs(int admissionYear, String deptname) throws IOException;

    List<Integer> getListOfAdmissionYearByDeptname(String deptName);

    List<Integer> getListOfAllAdmissionYears();
}
