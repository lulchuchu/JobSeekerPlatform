package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import project.jobseekerplatform.Model.entities.Application;

public interface ApplicationRepo extends JpaRepository<Application, Integer> {
}
