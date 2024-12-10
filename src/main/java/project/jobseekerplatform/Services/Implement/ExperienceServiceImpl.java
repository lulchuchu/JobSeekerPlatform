package project.jobseekerplatform.Services.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.entities.Experience;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.ExperienceRepository;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.ExperienceService;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepo userRepository;

    @Autowired
    public ExperienceServiceImpl(ExperienceRepository experienceRepository, UserRepo userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Experience addJob(Integer id, Experience experience) {
        experience.setUser(userRepository.findById(id).get());
        experienceRepository.save(experience);
        return experience;
    }

    @Override
    public List<Experience> getAllJobsByUserId(int userId) {
        return experienceRepository.findAllByUserId(userId);
    }

    @Override
    public void addJobToUser(int userId, int jobId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Experience> job = experienceRepository.findById(jobId);
        if (user.isEmpty() || job.isEmpty()) {
            return;
        }

        user.get().getExperiences().add(job.get());
        userRepository.save(user.get());
        job.get().setUser(user.get());
        experienceRepository.save(job.get());
    }

    @Override
    public List<Experience> getAllJobsByCompanyId(int companyId) {
        return experienceRepository.findAllByCompanyId(companyId);
    }
}
