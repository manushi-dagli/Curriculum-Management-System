package com.springboot.CurriculumManagement.UserDetailService;

import com.springboot.CurriculumManagement.Entities.HOD;
import com.springboot.CurriculumManagement.Exceptions.ResourceNotFoundException;
import com.springboot.CurriculumManagement.Repository.HODRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HODUserDetailService implements UserDetailsService {

    @Autowired
    private HODRepository hodRepository;

    @Override
    public HOD loadUserByUsername(String id) throws UsernameNotFoundException {
        HOD hod=this.hodRepository.findByHODId(id).orElseThrow(()->new ResourceNotFoundException("HOD","id",id));
        return hod;
    }
}
