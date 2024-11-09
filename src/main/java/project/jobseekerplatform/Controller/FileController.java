package project.jobseekerplatform.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping(
            value = "/getImage/{path}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getImage(@PathVariable String path) throws IOException {
        log.info("Inside getImage with path = {}", path);
        Resource imgFile = fileStorageService.load(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
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
