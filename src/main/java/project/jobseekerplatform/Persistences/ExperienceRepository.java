package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import project.jobseekerplatform.Model.entities.Experience;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    List<Experience> findAllByUserId(int userId);

    List<Experience> findAllByCompanyId(int userId);
}
