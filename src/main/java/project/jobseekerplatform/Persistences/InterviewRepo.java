package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import project.jobseekerplatform.Model.entities.Interview;

import javax.transaction.Transactional;

public interface InterviewRepo extends JpaRepository<Interview, Integer> {
    @Transactional
    @Modifying
    void deleteByJobIdAndUserId(int jobId, int userId);

    Interview findByJobIdAndUserId(int jobId, int userId);
}
