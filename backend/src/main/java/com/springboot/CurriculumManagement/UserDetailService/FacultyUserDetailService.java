package com.springboot.CurriculumManagement.UserDetailService;

import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Exceptions.ResourceNotFoundException;
import com.springboot.CurriculumManagement.Repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FacultyUserDetailService implements UserDetailsService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public Faculty loadUserByUsername(String id) throws UsernameNotFoundException {

        Faculty faculty=this.facultyRepository.findByFacultyId(id).orElseThrow(()->new ResourceNotFoundException("Faculty","id",id));
        return faculty;
    }
}