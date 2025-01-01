package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.ApplicationDto;
import project.jobseekerplatform.Model.dto.FilterDto;
import project.jobseekerplatform.Model.dto.InterviewDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.JobService;

import java.util.List;

@RestController
@CrossOrigin

@RequestMapping("/api/job")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getApplication(@PathVariable("id") int id) {
        Job job = jobService.getJob(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/all/{companyId}")
    public ResponseEntity<?> getApplicationByCompany(@PathVariable("companyId") int companyId) {
        List<ApplicationDto> application = jobService.getAllJob(companyId);
        return ResponseEntity.ok(application);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addJob(Authentication auth, @RequestBody ApplicationDto application) {
        jobService.addJob(application, auth);
        return ResponseEntity.ok("Application added");
    }

    @PostMapping("/close/{jobId}")
    public ResponseEntity<?> closeJob(Authentication auth, @PathVariable int jobId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        User user = userDetail.getUser();
        jobService.closeJob(jobId, user.getId());
        return ResponseEntity.ok("Application closed");
    }

    @GetMapping("/applied/all")
    public ResponseEntity<?> listAllApplied(Authentication auth, @RequestParam("applicationId") int applicationId) {
        List<UserDtoBasic> users = jobService.listUserApplied(applicationId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/company")
    public ResponseEntity<?> listApplicationByCompany(@RequestParam("companyId") int companyId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Job> applications = jobService.listJobByCompany(companyId, pageable);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/all")
    public ResponseEntity<?> listAllApplication(FilterDto filterDto) {
        Pageable pageable = PageRequest.of(filterDto.getCurrPage(), filterDto.getNumberPerPage());
        Page<Job> applications = jobService.listAllJob(filterDto, pageable);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/queryJobs")
    public ResponseEntity<?> listAllJobsss(@RequestBody FilterDto filterDto) {
        Pageable pageable = PageRequest.of(filterDto.getCurrPage(), filterDto.getNumberPerPage());
        Page<Job> jobs = jobService.queryJobs(filterDto, pageable);
        return ResponseEntity.ok(jobs);
    }
    @GetMapping("/checkApply")
    public ResponseEntity<?> checkApply(Authentication auth, @RequestParam("applicationId") int applicationId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        User user = userDetail.getUser();
        boolean isApplied = jobService.checkApply(user, applicationId);
        return ResponseEntity.ok(isApplied);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(Authentication auth, @RequestParam("cvId") int cvId, @RequestParam("applicationId") int applicationId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        User user = userDetail.getUser();
        jobService.apply(cvId, applicationId, user);
        return ResponseEntity.ok("Apply successfully");
    }

    @PostMapping("/unApply")
    public ResponseEntity<?> unApply(Authentication auth, @RequestParam("applicationId") int applicationId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        User user = userDetail.getUser();
        jobService.unApply(user, applicationId);
        return ResponseEntity.ok("Unapply successfully");
    }

    @CrossOrigin
    @PostMapping("/setInterview")
    public ResponseEntity<?> setInterview(@RequestBody InterviewDto interviewDto) {
        jobService.setInterview(interviewDto);
        return ResponseEntity.ok("Interview set");
    }

    @GetMapping("/getInterview")
    public ResponseEntity<?> getInterview(@RequestParam("applicationId") int applicationId, @RequestParam("userId") int userId) {
        InterviewDto interviewDto = jobService.getInterview(applicationId, userId);
        return ResponseEntity.ok(interviewDto);
    }
}
