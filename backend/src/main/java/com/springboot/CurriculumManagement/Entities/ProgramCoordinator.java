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
@Table(name = "program_coordinator")
public class ProgramCoordinator implements UserDetails {
    @Id
    private String programCoordinatorId;

    @Column(nullable = false)
    private String programCoordinatorName;

    @JsonIgnore
    @Column(nullable = false)
    private String password;
//    @Column(nullable = false)
//    private String departmentId;
    @Column(unique = true, nullable = false)
    private String emailId;

    @OneToOne
    @JoinColumn(name = "deptId", nullable = false, unique = true)
    private Department dept;

    public ProgramCoordinator() {
        super();
    }

    public ProgramCoordinator(String programCoordinatorId, String programCoordinatorName, String password, String emailId, Department dept) {
        super();
        this.programCoordinatorId = programCoordinatorId;
        this.programCoordinatorName = programCoordinatorName;
        this.password = password;
        this.emailId = emailId;
        this.dept = dept;
    }

    public String getProgramCoordinatorId() {
        return programCoordinatorId;
    }

    public void setProgramCoordinatorId(String programCoordinatorId) {
        this.programCoordinatorId = programCoordinatorId;
    }

    public String getProgramCoordinatorName() {
        return programCoordinatorName;
    }

    public void setProgramCoordinatorName(String programCoordinatorName) {
        programCoordinatorName = programCoordinatorName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PC"));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return programCoordinatorId;
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
