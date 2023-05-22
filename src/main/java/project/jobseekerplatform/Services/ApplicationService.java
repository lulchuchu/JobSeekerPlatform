package project.jobseekerplatform.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import project.jobseekerplatform.Model.dto.ApplicationDto;
import project.jobseekerplatform.Model.dto.FilterDto;
import project.jobseekerplatform.Model.dto.InterviewDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Application;

import java.util.List;

public interface ApplicationService {
    //    Application findById(int id);
    Application apply(int userId, int applicationId);

    List<UserDtoBasic> listUserApplied(int applicationId);

    boolean checkApply(int id, int applicationId);

    void addApplication(ApplicationDto application, Authentication auth);

    Page<Application> listApplicationByCompany(int companyId, Pageable pageable);

    Page<Application> listAllApplication(FilterDto filterDto, Pageable pageable);


    Application getApplication(int id);

    void setInterview(InterviewDto interviewDto);

    InterviewDto getInterview(int applicationId, int userId);

    List<ApplicationDto> getAllApplication(int companyId);
}
