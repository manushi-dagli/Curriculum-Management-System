package com.springboot.CurriculumManagement.Services;

import com.springboot.CurriculumManagement.Entities.*;
import com.springboot.CurriculumManagement.Exceptions.ResourceNotFoundException;
import com.springboot.CurriculumManagement.Payloads.HODDto;
import com.springboot.CurriculumManagement.Repository.FacultyRepository;
import com.springboot.CurriculumManagement.Repository.HODRepository;
import com.springboot.CurriculumManagement.Repository.PCRepository;
import com.springboot.CurriculumManagement.Repository.SubjectsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HODServiceImpl implements HODService{

	@Autowired
    private FacultyRepository facultyDao;

    @Autowired
    private PCRepository pcDao;
    @Autowired
    private HODRepository hodDao;


    @Autowired
    private SubjectsRepository subjectsDao;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Subjects> getAllSubjects(Department dept) {

        return subjectsDao.findAllByDeptId(dept);
    }

    @Override
    public HODDto getHODById(String HODId) {
        HOD hod=this.hodDao.findById(HODId).orElseThrow(()->new ResourceNotFoundException("HOD","Id",HODId));
        return this.HODToDto(hod);
    }

    @Override
    public void addNewFaculty(Faculty faculty) throws DuplicateKeyException {
        Faculty isPresent = null;
        try {
            String faculty_id = faculty.getFacultyId();
             isPresent = this.facultyDao.findByFacultyId(faculty_id).orElseThrow(() -> new ResourceNotFoundException("Faculty", "id", faculty_id));
             throw new DuplicateKeyException("Same id already exists");
        }
        catch (ResourceNotFoundException e){
                facultyDao.save(faculty);
        }
       

    }

    @Override
    public List<Faculty> getAllFaculty(Department dept) {

        return facultyDao.findAllByDeptId(dept);

    }

    @Override
    public void deleteFaculty(String facultyId) {
        Faculty facultyToDelete=facultyDao.getById(facultyId);
        facultyDao.delete(facultyToDelete);
    }




    @Override
    public void appointProgramCoordinator(Faculty newPc){
        ProgramCoordinator ifExists = null;
        try {
            Department dept = newPc.getDept();
            System.out.println("Dept to be added :"+dept.getDeptId());
            ifExists = this.pcDao.findPcByDeptId(dept).orElseThrow(() -> new ResourceNotFoundException("Program Coordinator", "dept", "dept_id"));
            deleteProgramCoordinator(ifExists.getProgramCoordinatorId());
            ProgramCoordinator pcToAdd=new ProgramCoordinator();
            pcToAdd.setDept(newPc.getDept());
            pcToAdd.setProgramCoordinatorId(newPc.getFacultyId());
            pcToAdd.setEmailId(newPc.getEmailId());
            pcToAdd.setProgramCoordinatorName(newPc.getFacultyName());
            pcToAdd.setPassword(newPc.getPassword());
            pcDao.save(pcToAdd);
        }
        catch (ResourceNotFoundException e){
            System.out.println("in catch ");
            ProgramCoordinator pcToAdd=new ProgramCoordinator(newPc.getFacultyId(), newPc.getFacultyName(), newPc.getPassword(), newPc.getEmailId(), newPc.getDept());
            pcDao.save(pcToAdd);
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public Optional<Faculty> getFacultyById(String id) {
        return facultyDao.findByFacultyId(id);
    }

    @Override
    public void deleteProgramCoordinator(String pcId) {
        ProgramCoordinator pcToDelete=pcDao.getById(pcId);
        pcDao.delete(pcToDelete);
    }


    public HOD DtoToHOD(HODDto dto){
        HOD hod=this.modelMapper.map(dto,HOD.class);
        return hod;
    }
    public HODDto HODToDto(HOD hod){
        HODDto dto=this.modelMapper.map(hod,HODDto.class);
        return dto;
    }
	
	

    public HODServiceImpl() {
    }


}
