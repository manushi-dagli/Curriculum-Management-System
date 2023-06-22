package com.springboot.CurriculumManagement.Controller;

import com.springboot.CurriculumManagement.Auth.JWTAuthRequest;
import com.springboot.CurriculumManagement.Auth.JWTAuthResponse;
import com.springboot.CurriculumManagement.Auth.JWTTokenHelper;
import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Entities.HOD;
import com.springboot.CurriculumManagement.Entities.ProgramCoordinator;
import com.springboot.CurriculumManagement.Payloads.FacultyDto;
import com.springboot.CurriculumManagement.Payloads.PCDto;
import com.springboot.CurriculumManagement.Services.FacultyServiceImpl;
import com.springboot.CurriculumManagement.Services.HODServiceImpl;
import com.springboot.CurriculumManagement.Services.PCServiceImpl;
import com.springboot.CurriculumManagement.UserDetailService.FacultyUserDetailService;
import com.springboot.CurriculumManagement.UserDetailService.HODUserDetailService;
import com.springboot.CurriculumManagement.UserDetailService.PCUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {

    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private HODUserDetailService hodUserDetailsService;
    @Autowired
    private FacultyUserDetailService facultyUserDetailService;
    @Autowired
    private PCUserDetailService pcUserDetailService;
    @Autowired
    private HODServiceImpl hodService;

    @Autowired
    private FacultyServiceImpl facultyService;

    @Autowired
    private PCServiceImpl pcService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> getToken(@RequestBody JWTAuthRequest request) throws BadCredentialsException{
        try {


            System.out.println(request.getId() + "     " + request.getPassword());
            String token;
            JWTAuthResponse response;
            if ("hod".equals(request.getRole())) {
                HOD hod = new HOD();
                this.authenticate(request.getId(), request.getPassword(), hod.getAuthorities());
                hod = this.hodUserDetailsService.loadUserByUsername(request.getId());
                System.out.println("Req pass: " + request.getPassword());
                System.out.println("Db pass:" + hod.getPassword());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                if (!encoder.matches(request.getPassword(), hod.getPassword())) {
                    throw new BadCredentialsException("Incorrect Password");
                }
//            System.out.println("This is hod dept of auth controller:"+hod.getDept());
                token = this.jwtTokenHelper.generateToken(hod);
                response = new JWTAuthResponse();
                response.setToken(token);
                response.setHodDto(hodService.HODToDto(hod));
            } else if ("faculty".equals(request.getRole())) {
                Faculty faculty = new Faculty();
                this.authenticate(request.getId(), request.getPassword(), faculty.getAuthorities());
                faculty = this.facultyUserDetailService.loadUserByUsername(request.getId());
                System.out.println("Req pass: " + request.getPassword());
                System.out.println("Db pass:" + faculty.getPassword());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                if (!encoder.matches(request.getPassword(), faculty.getPassword())) {
                    throw new BadCredentialsException("Incorrect Password");
                }
                token = this.jwtTokenHelper.generateToken(faculty);
                response = new JWTAuthResponse();
                response.setToken(token);
                response.setFacultyDto(facultyService.FacultyToDto(faculty));
            } else {
                ProgramCoordinator programCoordinator = new ProgramCoordinator();
                this.authenticate(request.getId(), request.getPassword(), programCoordinator.getAuthorities());
                programCoordinator = this.pcUserDetailService.loadUserByUsername(request.getId());
                System.out.println("Req pass: " + request.getPassword());
                System.out.println("Db pass:" + programCoordinator.getPassword());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                if (!encoder.matches(request.getPassword(), programCoordinator.getPassword())) {
                    throw new BadCredentialsException("Incorrect Password");
                }
                token = this.jwtTokenHelper.generateToken(programCoordinator);
                response = new JWTAuthResponse();
                response.setToken(token);
                response.setPcDto(pcService.PcToDto(programCoordinator));
            }
            System.out.println("Hello");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (BadCredentialsException e){
            System.out.println("Invalid password in catch");
            throw new RuntimeException("Incorrect Password");
        }
    }

    private void authenticate(String id, String pass, Collection<? extends GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(id, pass, authorities);
        try {
            System.out.println(usernamePasswordAuthenticationToken);
            Authentication authenticate = null;
            if (!usernamePasswordAuthenticationToken.isAuthenticated()) {
                authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticate);
            }
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details!!");
            throw new RuntimeException("Invalid Id or Password");
        }
    }
}
