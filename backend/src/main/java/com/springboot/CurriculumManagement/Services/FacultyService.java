package com.springboot.CurriculumManagement.Services;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Entities.Subjects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacultyService {
    List<Subjects> getAllSubjects(Department dept);

    List<Integer> getRemainingSubSequence(String semesterSelected);

    List<Department> getAllDept();

    ResponseEntity<HttpStatus> saveSubjectDetails(Subjects subjectDetails);

    List<Subjects> getAllMySubjects(Faculty faculty);
}
