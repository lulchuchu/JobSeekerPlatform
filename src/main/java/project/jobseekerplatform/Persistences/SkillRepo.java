package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Integer> {
}
