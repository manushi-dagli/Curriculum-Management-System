package com.springboot.CurriculumManagement.Repository;

import com.springboot.CurriculumManagement.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,String> {

    @Query(value = "select d from Department d where d.deptName=?1")
    public Department findByName(String deptname);

    @Query(value = "select d from Department d where d.startYear<=?1")
    public List<Department> findDepartmentsByAdmissionYear(int admissionYear);

    @Query(value = "select d.startYear from Department d where d.deptName=?1")
    public int findStartYearByDepartmentName(String deptName);

    @Query(value = "select min (d.startYear) from Department d")
    public int findStartYearOfFirstDepartment();
}
