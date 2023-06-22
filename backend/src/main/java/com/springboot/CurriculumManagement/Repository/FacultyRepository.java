package com.springboot.CurriculumManagement.Repository;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Entities.Faculty;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FacultyRepository extends JpaRepository<Faculty,String> {
    Optional<Faculty> findByFacultyId(String id);

    @Query(value = "select f from Faculty f where f.dept=?1")
    List<Faculty> findAllByDeptId(Department deptId);



}
