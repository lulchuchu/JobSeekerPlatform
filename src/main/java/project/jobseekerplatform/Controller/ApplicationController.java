package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Services.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addApplication(Authentication auth, @RequestBody Application application) {
        applicationService.addApplication(application, auth);
        return ResponseEntity.ok("Application added");
    }

    @GetMapping("/applyied/all")
    public ResponseEntity<?> listAllApplied(@RequestParam("applicationId") int applicationId) {
        List<UserDtoBasic> users = applicationService.listUserApplied(applicationId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/all")
    public ResponseEntity<?> listApplicationByCompany(@RequestParam("companyId") int companyId) {
        List<Application> applications = applicationService.listApplicationByCompany(companyId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestParam("userId") int userId, @RequestParam("applicationId") int applicationId) {
        Application application = applicationService.apply(userId, applicationId);
        return ResponseEntity.ok(application);
    }
}
