package project.jobseekerplatform.Services.Implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.jobseekerplatform.Exception.ResourceException;
import project.jobseekerplatform.Model.entities.CV;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.CVRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.FileStorageService;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    // private final Path root = Paths.get("src/main/resources/static/Pics");
    private final Path root = Paths.get("Files");
    private final UserRepo userRepo;
    private final CVRepo cvRepo;

    public FileStorageServiceImpl(UserRepo userRepo, CVRepo cvRepo) {
        this.userRepo = userRepo;
        this.cvRepo = cvRepo;
    }

    @Override
    public void save(MultipartFile file) {
        try {
            log.info("root path" + root);
            if (!this.root.resolve(file.getOriginalFilename()).toFile().exists()) {
                InputStream in = file.getInputStream();
                Files.copy(in, this.root.resolve(file.getOriginalFilename()));
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file;
            if (Objects.equals(filename, "null") || Objects.equals(filename, "undefined")) {
                file = root.resolve("blankuser.png");
            } else {
                file = root.resolve(filename);
            }
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<CV> getCVFileName(int userId) {
        Optional<User> u = userRepo.findById(userId);
        if (u.isEmpty()) {
            throw new ResourceException("User not found");
        }
        return u.get().getCV();
    }

    @Override
    public void deleteCV(int cvId) {
        CV cv = cvRepo.findById(cvId).orElseThrow(() -> new ResourceException("CV not found"));
        for (Job job : cv.getJob()) {
            job.getCvs().remove(cv);
        }
        cvRepo.delete(cv);
    }

    @Override
    public AbstractMap.SimpleEntry<String, Resource> loadCV(int cvId) {
        Optional<CV> cv = cvRepo.findById(cvId);

        if (cv.isEmpty()) {
            throw new ResourceException("CV not found");
        }

        return new AbstractMap.SimpleEntry<>(cv.get().getFilename(), load(cv.get().getFilename()));
    }
}
