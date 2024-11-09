package project.jobseekerplatform.Services.Implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.jobseekerplatform.Services.FileStorageService;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    //    private final Path root = Paths.get("src/main/resources/static/Pics");
    private final Path root = Paths.get("Pics");

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
            log.info("loading image" + filename);
            Path file = root.resolve(filename);
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

}
