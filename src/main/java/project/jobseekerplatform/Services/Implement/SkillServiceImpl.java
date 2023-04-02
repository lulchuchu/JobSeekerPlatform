package project.jobseekerplatform.Services.Implement;

import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.Skill;
import project.jobseekerplatform.Persistences.SkillRepo;
import project.jobseekerplatform.Services.SkillService;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepo skillRepo;

    public SkillServiceImpl(SkillRepo skillRepo) {
        this.skillRepo = skillRepo;
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepo.findAll();
    }

    @Override
    public List<Application> getApplicationsBySkillId(int skillId) {
        Optional<Skill> skill = skillRepo.findById(skillId);
        return skill.map(Skill::getApplications).orElse(null);
    }

    @Override
    public List<Job> getJobsBySkillId(int skillId) {
        Optional<Skill> skill = skillRepo.findById(skillId);
        return skill.map(Skill::getJobs).orElse(null);
    }
}
