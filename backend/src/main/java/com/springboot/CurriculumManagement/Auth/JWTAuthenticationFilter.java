package com.springboot.CurriculumManagement.Auth;

import com.springboot.CurriculumManagement.UserDetailService.FacultyUserDetailService;
import com.springboot.CurriculumManagement.UserDetailService.HODUserDetailService;
import com.springboot.CurriculumManagement.UserDetailService.PCUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private HODUserDetailService hodUserDetailsService;
    @Autowired
    private FacultyUserDetailService facultyUserDetailService;
    @Autowired
    private PCUserDetailService pcUserDetailService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken=request.getHeader("Authorization");
        System.out.println(requestToken);
        String token=null;
        String id=null;
        if(requestToken != null && requestToken.startsWith("Bearer")){
            token=requestToken.substring(7);
            try{
                id=this.jwtTokenHelper.getUsernameFromToken(token);
            }catch(IllegalArgumentException e){
                System.out.println("Unable to get JWT Token");
            }catch(ExpiredJwtException e){
                System.out.println("JWT Token is expired");
            }catch(MalformedJwtException e){
                System.out.println("Invalid JWT");
            }
        }
        else{
            System.out.println("JWT token does not begin with Bearer");
        }
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if(id!=null) {
            UserDetails userDetails = null;
            try{
                userDetails = hodUserDetailsService.loadUserByUsername(id);
            }catch (Exception e){
                try{
                    userDetails = facultyUserDetailService.loadUserByUsername(id);
                }catch(Exception e2){
                    try{
                        userDetails = pcUserDetailService.loadUserByUsername(id);
                    }catch(Exception e3){
                        logger.error("Invalid user");
                    }
                }
            }
            assert userDetails != null;
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Invalid jwt token");
            }

        }
        else{
            System.out.println("username is null or context is not null");
        }
        filterChain.doFilter(request,response);
    }

}
