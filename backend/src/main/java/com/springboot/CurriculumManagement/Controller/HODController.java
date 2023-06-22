package com.springboot.CurriculumManagement.Controller;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Entities.ProgramCoordinator;
import com.springboot.CurriculumManagement.Entities.Subjects;
import com.springboot.CurriculumManagement.Exceptions.ResourceNotFoundException;
import com.springboot.CurriculumManagement.Repository.FacultyRepository;
import com.springboot.CurriculumManagement.Repository.PCRepository;
import com.springboot.CurriculumManagement.Services.HODService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/HOD/")
public class HODController {

    @Autowired
    private HODService hodService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FacultyRepository facultyDao;

    @Autowired
    private PCRepository pcDao;

    @GetMapping("/isHOD")
    public ResponseEntity<String> checkHOD() {
        return new ResponseEntity<String>("HOD", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HOD')")
    @PostMapping("/getallsubjects")
    public List<Subjects> getAllSubjects(@RequestBody Department dept) {

        return this.hodService.getAllSubjects(dept);
    }

    @PreAuthorize("hasRole('ROLE_HOD')")
    @PostMapping("/addnewfaculty")
    public ResponseEntity<HttpStatus> addNewFaculty(@RequestBody Faculty newFaculty) {
        try {
            System.out.println(newFaculty.getPassword());
            newFaculty.setPassword(this.passwordEncoder.encode(newFaculty.getPassword()));
            this.hodService.addNewFaculty(newFaculty);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ROLE_HOD')")
    @PostMapping("/getallfaculty")
    public List<Faculty> getAllFaculty(@RequestBody Department dept) {

        return this.hodService.getAllFaculty(dept);
    }

    @PreAuthorize("hasRole('ROLE_HOD')")
    @DeleteMapping("/deletefaculty/{facultyId}")
    public ResponseEntity<HttpStatus> deleteFaculty(@PathVariable String facultyId) {
        try {
            System.out.println("delete");
            this.hodService.deleteFaculty(facultyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_HOD')")
    @GetMapping("/getfacultybyid/{facultyid}")
    public Faculty getFacultyById(@PathVariable(value = "facultyid") String id) {
        // Optional<Faculty> faculty = hodService.getFacultyById(id);
        // return faculty;
        Faculty faculty = this.facultyDao.findByFacultyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty", "id", id));
        return faculty;
    }

    @PreAuthorize("hasRole('ROLE_HOD')")
    @GetMapping("/appointpc/{newPcId}")
    public ResponseEntity<HttpStatus> appointProgramCoordinator(@PathVariable String newPcId) {

        try {
            Faculty newPc = getFacultyById(newPcId);
            this.hodService.appointProgramCoordinator(newPc);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("in catch controller");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/checkpc")
    public ProgramCoordinator checkPc(@RequestBody Department dept) {
        ProgramCoordinator programCoordinator = this.pcDao.findPcByDeptId(dept)
                .orElseThrow(() -> new ResourceNotFoundException("Pc of ", "dept-id", dept.getDeptId()));
        return programCoordinator;
    }
}
