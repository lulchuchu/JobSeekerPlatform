package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.jobseekerplatform.Services.FileStorageService;

@RestController
@RequestMapping("/api/file")
@CrossOrigin

public class FileController {
    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "files", required = false) MultipartFile[] files) {
        String fileName = "";
        for (MultipartFile file : files) {
            fileStorageService.save(file);
            fileName += file.getOriginalFilename() + ",";
        }
        return ResponseEntity.ok(fileName.substring(0, fileName.length() - 1));
    }
}
