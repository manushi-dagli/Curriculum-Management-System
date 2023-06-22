package com.springboot.CurriculumManagement.Repository;


import com.springboot.CurriculumManagement.Entities.SubjectFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectFileRepository extends JpaRepository<SubjectFile,String> {
}
