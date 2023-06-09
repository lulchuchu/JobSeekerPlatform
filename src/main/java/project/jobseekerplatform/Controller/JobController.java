package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.JobService;

@RestController
@RequestMapping("/api/job")
@CrossOrigin

public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/all/user")
    public ResponseEntity<?> getAllJobsByUserId(@RequestParam int userId) {
        return ResponseEntity.ok(jobService.getAllJobsByUserId(userId));
    }

    @GetMapping("/all/company")
    public ResponseEntity<?> getAllJobsByCompanyId(@RequestParam int companyId) {
        return ResponseEntity.ok(jobService.getAllJobsByCompanyId(companyId));
    }

    //Chi co nguoi dung moi co the them job
    @GetMapping("/add")
    public ResponseEntity<?> addJob(Authentication authentication, @RequestBody Job job) {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        jobService.addJob(userDetail.getUser().getId(), job);
        return ResponseEntity.ok("Job " + job + "added");
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> addJobToUser(@RequestParam int userId, @RequestParam int jobId) {
        jobService.addJobToUser(userId, jobId);
        return ResponseEntity.ok("Job added to user");
    }
}
