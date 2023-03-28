package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import project.jobseekerplatform.Model.entities.Company;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    List<Company> findAllByNameContaining(String name);
}
