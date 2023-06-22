package com.springboot.CurriculumManagement.Controller;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Repository.DepartmentRepository;
import com.springboot.CurriculumManagement.Services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/Pdf/")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private DepartmentRepository departmentDao;

//    @GetMapping("/createpdf")
//    public ResponseEntity<InputStreamResource> createPdf() {
//        //get entered branch name left for printing in pdf and pass in the createpdf method
//        ByteArrayInputStream pdf = pdfService.createPdf(2020,"IT");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;file=filename.pdf");
//        System.out.println("Just before return");
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
//
//
//    }

    @PostMapping("/getmergedpdf")
    public ResponseEntity<InputStreamResource> mergePdfs(@RequestParam("admissionYear")int admissionYear,@RequestParam("deptName")String deptName) throws IOException {

        System.out.println(admissionYear + "---------" + deptName);
        ByteArrayInputStream pdf = pdfService.mergePdfs(admissionYear,deptName);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;file=filename.pdf");
        System.out.println("Just before return in merge pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
    }

    @GetMapping("/getadmissionyearbydeptname/{deptName}")
    public List<Integer> getAdmissionYearByDeptName(@PathVariable String deptName){
        return this.pdfService.getListOfAdmissionYearByDeptname(deptName);
//         this.departmentDao.findStartYearByDepartmentName(deptName);
    }

    @GetMapping("/getdepartmentsbyadmissionyear/{admissionYear}")
    public List<Department> getDepartmentsListByAdmissionYear(@PathVariable int admissionYear){
        return this.departmentDao.findDepartmentsByAdmissionYear(admissionYear);
    }

    @GetMapping("/getalladmissionyears")
    public List<Integer> getAllAdmissionYears(){
        return this.pdfService.getListOfAllAdmissionYears();
//         this.departmentDao.findStartYearByDepartmentName(deptName);
    }

    @GetMapping("/getalldept")
    public List<Department> getAllDepartments(){
        return this.departmentDao.findAll();
    }
}
