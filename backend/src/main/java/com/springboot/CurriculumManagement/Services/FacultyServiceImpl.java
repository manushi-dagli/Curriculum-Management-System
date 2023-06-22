package com.springboot.CurriculumManagement.Services;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Entities.Subjects;
import com.springboot.CurriculumManagement.Payloads.FacultyDto;
import com.springboot.CurriculumManagement.Payloads.HODDto;
import com.springboot.CurriculumManagement.Repository.DepartmentRepository;
import com.springboot.CurriculumManagement.Repository.FacultyRepository;
import com.springboot.CurriculumManagement.Repository.SubjectsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class FacultyServiceImpl implements FacultyService{

    @Autowired
    private FacultyRepository facultyDao;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentRepository departmentDao;

    @Autowired
    private SubjectsRepository subjectsDao;

    final private List<Integer> subSequenceLimit= Arrays.asList(new Integer[] { 1, 2, 3, 4, 5,6 });

    @Override
    public List<Subjects> getAllSubjects(Department dept) {

        return subjectsDao.findAllByDeptId(dept);
    }

    @Override
    public List<Integer> getRemainingSubSequence(String semesterSelected) {

        List<Integer> existingSubSequence= subjectsDao.findExistingSubSequence(parseInt(semesterSelected));
        List<Integer> remainingSubSequence= new ArrayList<>(subSequenceLimit);
        remainingSubSequence.removeAll(existingSubSequence);
        System.out.println("seq "+remainingSubSequence);
        return remainingSubSequence;
    }

    @Override
    public List<Department> getAllDept() {
        return departmentDao.findAll();
    }

    @Override
    public ResponseEntity<HttpStatus> saveSubjectDetails(Subjects subjectDetails) {
        try {
            subjectsDao.save(subjectDetails);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){

            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<Subjects> getAllMySubjects(Faculty faculty) {
        String facultyId=faculty.getFacultyId();
        return subjectsDao.findAllByFacultyId(facultyId);
    }

    public FacultyDto FacultyToDto(Faculty faculty) {
        FacultyDto dto=this.modelMapper.map(faculty,FacultyDto.class);
        return dto;
    }
}
