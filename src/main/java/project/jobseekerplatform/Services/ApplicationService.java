package project.jobseekerplatform.Services;

import org.springframework.security.core.Authentication;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Application;

import java.util.List;

public interface ApplicationService {
    //    Application findById(int id);
    Application apply(int userId, int applicationId);

    List<UserDtoBasic> listUserApplied(int applicationId);

    void addApplication(Application application, Authentication auth);

    List<Application> listApplicationByCompany(int companyId);

    List<Application> listAllApplication();

    boolean checkApply(int id, int applicationId);
}
