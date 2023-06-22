package com.springboot.CurriculumManagement.Services;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Entities.Subjects;
import com.springboot.CurriculumManagement.Payloads.HODDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface HODService {

    List<Subjects> getAllSubjects(Department dept);
    public HODDto getHODById(String HODId);
	void addNewFaculty(Faculty faculty) throws DuplicateKeyException;

    List<Faculty> getAllFaculty(Department dept);

    void deleteFaculty(String facultyId);

//    void appointProgramCoordinator();
void appointProgramCoordinator(Faculty newPc);

    Optional<Faculty> getFacultyById(String id);

    void deleteProgramCoordinator(String pcId);
}
