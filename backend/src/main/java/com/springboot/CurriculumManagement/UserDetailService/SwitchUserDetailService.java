//package com.springboot.CurriculumManagement.UserDetailService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class SwitchUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private HODUserDetailService hodUserDetailService;
//
//    @Autowired
//    private FacultyUserDetailService facultyUserDetailService;
//
//    @Autowired
//    private PCUserDetailService pcUserDetailService;
//
//    private Map<String, UserDetailsService> userDetailsServiceMap;
//
//    public SwitchUserDetailService() {
//        this.userDetailsServiceMap = new HashMap<>();
//    }
//
//    public void setUserDetailsServiceMap(Map<String, UserDetailsService> userDetailsServiceMap) {
//        this.userDetailsServiceMap = userDetailsServiceMap;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
//        String[] split = id.split(":");
//
//        String userType = split[0];
//        String userTypeId = split[1];
//
//        switch (userType) {
//            case "hod":
//                return hodUserDetailService.loadUserByUsername(userTypeId);
//            case "faculty":
//                return facultyUserDetailService.loadUserByUsername(userTypeId);
//            case "pc":
//                return pcUserDetailService.loadUserByUsername(userTypeId);
//            default:
//                throw new UsernameNotFoundException("Invalid user type");
//        }
//    }
//
//
//}
