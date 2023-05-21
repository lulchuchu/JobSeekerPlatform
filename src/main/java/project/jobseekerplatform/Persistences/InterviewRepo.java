package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import project.jobseekerplatform.Model.entities.Interview;

import javax.transaction.Transactional;

public interface InterviewRepo extends JpaRepository<Interview, Integer> {
    @Transactional
    @Modifying
    void deleteByApplicationIdAndUserId(int applicationId, int userId);

    Interview findByApplicationIdAndUserId(int applicationId, int userId);
}
