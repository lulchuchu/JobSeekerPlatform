package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getAllSkills();

    List<Application> getApplicationsBySkillId(int skillId);

    List<Job> getJobsBySkillId(int skillId);
}
