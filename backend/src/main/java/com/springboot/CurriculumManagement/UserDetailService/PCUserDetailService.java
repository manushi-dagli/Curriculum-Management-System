package com.springboot.CurriculumManagement.UserDetailService;

import com.springboot.CurriculumManagement.Entities.ProgramCoordinator;
import com.springboot.CurriculumManagement.Exceptions.ResourceNotFoundException;
import com.springboot.CurriculumManagement.Repository.PCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PCUserDetailService implements UserDetailsService {
    @Autowired
    private PCRepository pcRepository;
    @Override
    public ProgramCoordinator loadUserByUsername(String id) throws UsernameNotFoundException {
        ProgramCoordinator programCoordinator=this.pcRepository.findByProgramCoordinatorId(id).orElseThrow(()->new ResourceNotFoundException("Program Coordinator","id",id));
        return programCoordinator;
    }
}
