package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.entities.Job;

import java.util.List;

public interface JobService {
    Job addJob(Job job);

    List<Job> getAllJobsByUserId(int userId);

    void addJobToUser(int userId, int jobId);

    List<Job> getAllJobsByCompanyId(int companyId);
}
