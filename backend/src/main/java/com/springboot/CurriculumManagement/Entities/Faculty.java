package com.springboot.CurriculumManagement.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import jakarta.persistence.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty implements UserDetails {
    @Id
    private String facultyId;

    @Column(nullable = false)
    private String facultyName;

    @Column(nullable = false)
    private String password;
//    @Column(nullable = false)
//    private String departmentName;
    @Column(unique = true, nullable = false)
    private String emailId;

//    @OneToOne
//    @JoinColumn(name = "deptId", nullable = false)
//    private Department dept;

    @ManyToOne
    @JoinColumn(name = "deptId",nullable = false)
    private Department dept;
//@ManyToMany(cascade = {
//        CascadeType.ALL
//    })
//    @JoinTable(
//        name = "teaches",
//        joinColumns = {
//            @JoinColumn(name = "faculty_id")
//        },
//        inverseJoinColumns = {
//            @JoinColumn(name = "subject_id")
//        }
//    )
//    private List<Subjects> subjectsList;

//    @OneToMany(mappedBy = "faculty")
//    private List<Subjects> subjectsList;


    public Faculty() {
        super();
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_FACULTY"));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return facultyId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
