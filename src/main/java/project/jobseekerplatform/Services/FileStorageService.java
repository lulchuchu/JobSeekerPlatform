package project.jobseekerplatform.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import project.jobseekerplatform.Model.entities.CV;

import java.util.AbstractMap;
import java.util.List;

public interface FileStorageService {

    void save(MultipartFile file);

    Resource load(String filename);

    AbstractMap.SimpleEntry<String, Resource> loadCV(int userId);

    List<CV> getCVFileName(int userId);

    void deleteCV(int cvId);
}
