package com.springboot.CurriculumManagement.Services;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Entities.Subjects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PCService {
    void addNewSubject(Subjects newSubject);

    List<Faculty> getAllFaculty(Department deptId);

    List<Integer> getRemainingSubSequence(String semesterSelected);

    List<Subjects> getAllSubjects(Department dept);

    List<Department> getAllDept();

    void deleteSubject(String dduCode);

    ResponseEntity<HttpStatus> saveSubjectDetails(Subjects subjectDetails);
}
