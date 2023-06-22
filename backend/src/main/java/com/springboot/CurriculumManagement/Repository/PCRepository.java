package com.springboot.CurriculumManagement.Repository;

import com.springboot.CurriculumManagement.Entities.Department;
import com.springboot.CurriculumManagement.Entities.Faculty;
import com.springboot.CurriculumManagement.Entities.ProgramCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PCRepository extends JpaRepository<ProgramCoordinator,String> {
    Optional<ProgramCoordinator> findByProgramCoordinatorId(String id);

    @Query(value = "select p from ProgramCoordinator p where p.dept=?1")
    Optional<ProgramCoordinator> findPcByDeptId(Department deptId);
}
