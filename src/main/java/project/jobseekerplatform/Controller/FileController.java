package project.jobseekerplatform.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.jobseekerplatform.Services.FileStorageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@CrossOrigin
@Slf4j
public class FileController {
    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping(value = "/getImage/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String path) throws IOException {
        log.info("Inside getImage with path = {}", path);
        Resource imgFile = fileStorageService.load(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    @GetMapping(value = "/getCV/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getCV(@PathVariable int userId) throws IOException {
        Resource cvFile = fileStorageService.loadCV(userId);
        String cvFileName = fileStorageService.getCVFileName(userId);

        return ResponseEntity
                .ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + cvFileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(cvFile);
    }

    @GetMapping(value = "/getCVFileName/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<String> getFileCVName(@PathVariable int userId) throws IOException {
        String cvFile = fileStorageService.getCVFileName(userId);

        return ResponseEntity
                .ok()
                .body(cvFile);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "files", required = false) MultipartFile[] files) {
        log.info("Inside update controller");
        StringBuilder fileName = new StringBuilder();
        for (MultipartFile file : files) {
            fileStorageService.save(file);
            fileName.append(file.getOriginalFilename()).append(",");
        }
        System.out.println(fileName.substring(0, fileName.length() - 1));
        return ResponseEntity.ok(fileName.substring(0, fileName.length() - 1));
    }
}
