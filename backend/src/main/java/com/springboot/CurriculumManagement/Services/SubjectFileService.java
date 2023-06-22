package com.springboot.CurriculumManagement.Services;


import com.springboot.CurriculumManagement.Entities.SubjectFile;
import com.springboot.CurriculumManagement.Entities.Subjects;
import org.springframework.web.multipart.MultipartFile;

public interface SubjectFileService {
    public SubjectFile getFile(String fileId);
    public SubjectFile storeFile(MultipartFile file, String dduCode);

}
