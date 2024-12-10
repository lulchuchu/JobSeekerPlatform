package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.entities.Experience;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.ExperienceService;

@RestController
@RequestMapping("/api/experience")
@CrossOrigin

public class ExperienceController {

    private final ExperienceService experienceService;

    @Autowired
    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping("/all/user")
    public ResponseEntity<?> getAllJobsByUserId(@RequestParam int userId) {
        return ResponseEntity.ok(experienceService.getAllJobsByUserId(userId));
    }

    @GetMapping("/all/company")
    public ResponseEntity<?> getAllJobsByCompanyId(@RequestParam int companyId) {
        return ResponseEntity.ok(experienceService.getAllJobsByCompanyId(companyId));
    }

    //Chi co nguoi dung moi co the them job
    @GetMapping("/add")
    public ResponseEntity<?> addJob(Authentication authentication, @RequestBody Experience experience) {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        experienceService.addJob(userDetail.getUser().getId(), experience);
        return ResponseEntity.ok("Job " + experience + "added");
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> addJobToUser(@RequestParam int userId, @RequestParam int jobId) {
        experienceService.addJobToUser(userId, jobId);
        return ResponseEntity.ok("Job added to user");
    }
}
