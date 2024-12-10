package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.entities.Experience;

import java.util.List;

public interface ExperienceService {
    Experience addJob(Integer id, Experience experience);

    List<Experience> getAllJobsByUserId(int userId);

    void addJobToUser(int userId, int jobId);

    List<Experience> getAllJobsByCompanyId(int companyId);
}
