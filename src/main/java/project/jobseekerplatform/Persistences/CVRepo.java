package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.CV;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVRepo extends JpaRepository<CV, Integer> {
    Optional<CV> findByUserIdAndJobId(int userId, int jobId);

    List<CV> findByUserId(Integer id);
}
