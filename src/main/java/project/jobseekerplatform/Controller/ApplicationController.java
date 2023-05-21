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
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.ApplicationService;

import java.util.List;

@RestController
@CrossOrigin

@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getApplication(@PathVariable("id") int id) {
        Application application = applicationService.getApplication(id);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/all/{companyId}")
    public ResponseEntity<?> getApplicationByCompany(@PathVariable("companyId") int companyId) {
        List<ApplicationDto> application = applicationService.getAllApplication(companyId);
        return ResponseEntity.ok(application);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addApplication(Authentication auth, @RequestBody Application application) {
        applicationService.addApplication(application, auth);
        return ResponseEntity.ok("Application added");
    }

    @GetMapping("/applied/all")
    public ResponseEntity<?> listAllApplied(Authentication auth, @RequestParam("applicationId") int applicationId) {
        List<UserDtoBasic> users = applicationService.listUserApplied(applicationId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/company")
    public ResponseEntity<?> listApplicationByCompany(@RequestParam("companyId") int companyId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Application> applications = applicationService.listApplicationByCompany(companyId, pageable);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/all")
    public ResponseEntity<?> listAllApplication(FilterDto filterDto) {
        System.out.println(filterDto);
        Pageable pageable = PageRequest.of(filterDto.getCurrPage(), filterDto.getNumberPerPage());
        Page<Application> applications = applicationService.listAllApplication(filterDto, pageable);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/checkApply")
    public ResponseEntity<?> checkApply(Authentication auth, @RequestParam("applicationId") int applicationId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        User user = userDetail.getUser();
        boolean isApplied = applicationService.checkApply(user.getId(), applicationId);
        return ResponseEntity.ok(isApplied);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(Authentication auth, @RequestParam("applicationId") int applicationId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        User user = userDetail.getUser();
        Application application = applicationService.apply(user.getId(), applicationId);
        return ResponseEntity.ok(application);
    }

    @CrossOrigin
    @PostMapping("/setInterview")
    public ResponseEntity<?> setInterview(@RequestBody InterviewDto interviewDto) {
        applicationService.setInterview(interviewDto);
        return ResponseEntity.ok("Interview set");
    }

    @GetMapping("/getInterview")
    public ResponseEntity<?> getInterview(@RequestParam("applicationId") int applicationId, @RequestParam("userId") int userId) {
        InterviewDto interviewDto = applicationService.getInterview(applicationId, userId);
        return ResponseEntity.ok(interviewDto);
    }
}
