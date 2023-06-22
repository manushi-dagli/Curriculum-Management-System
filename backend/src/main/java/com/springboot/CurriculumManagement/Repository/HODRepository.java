package com.springboot.CurriculumManagement.Repository;
import com.springboot.CurriculumManagement.Entities.HOD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HODRepository extends JpaRepository<HOD, String> {
    Optional<HOD> findByHODId(String id);
}

