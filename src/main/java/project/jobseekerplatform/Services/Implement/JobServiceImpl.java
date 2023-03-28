package project.jobseekerplatform.Services.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.JobRepository;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.JobService;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepo userRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, UserRepo userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Job addJob(Job job) {
        jobRepository.save(job);
        return job;
    }

    @Override
    public List<Job> getAllJobsByUserId(int userId) {
        return jobRepository.findAllByUserId(userId);
    }

    @Override
    public void addJobToUser(int userId, int jobId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Job> job = jobRepository.findById(jobId);
        if (user.isEmpty() || job.isEmpty()) {
            return;
        }

        user.get().getJobs().add(job.get());
        userRepository.save(user.get());
        job.get().setUser(user.get());
        jobRepository.save(job.get());
    }

    @Override
    public List<Job> getAllJobsByCompanyId(int companyId) {
        return jobRepository.findAllByCompanyId(companyId);
    }
}
