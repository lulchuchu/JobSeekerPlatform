package project.jobseekerplatform.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import project.jobseekerplatform.Model.dto.ApplicationDto;
import project.jobseekerplatform.Model.dto.FilterDto;
import project.jobseekerplatform.Model.dto.InterviewDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;

import java.util.List;

public interface JobService {
    // Application findById(int id);
    void apply(int cvId, int applicationId, User user);

    List<UserDtoBasic> listUserApplied(int applicationId);

    boolean checkApply(User user, int applicationId);

    void addJob(ApplicationDto application, Authentication auth);

    Page<Job> listJobByCompany(int companyId, Pageable pageable);

    Page<Job> listAllJob(FilterDto filterDto, Pageable pageable);

    Job getJob(int id);

    void setInterview(InterviewDto interviewDto);

    InterviewDto getInterview(int applicationId, int userId);

    List<ApplicationDto> getAllJob(int companyId);

    void unApply(User user, int applicationId);

    int closeJob(int applicationId, int id);

    List<Job> listAll();

    Page<Job> queryJobs(FilterDto filterDto, Pageable pageable);
}
