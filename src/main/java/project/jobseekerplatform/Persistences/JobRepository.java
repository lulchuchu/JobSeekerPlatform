package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import project.jobseekerplatform.Model.entities.Job;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findAllByUserId(int userId);

    List<Job> findAllByCompanyId(int userId);
}
