package project.jobseekerplatform.Persistences;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.jobseekerplatform.Model.entities.Application;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Integer> {
    @Query("SELECT a FROM Application a WHERE a.startDate >= ?1 AND a.experience like ?2 AND a.type like ?3 AND a.onSite like ?4")
    List<Application> findJob(LocalDate dateResult, String experience, String jobType, String onSite);

    @Query("SELECT a FROM Application a WHERE a.company.id = ?5 AND a.startDate >= ?1 AND a.experience like ?2 AND a.type like ?3 AND a.onSite like ?4")
    List<Application> findJobCompany(LocalDate dateResult, String experience, String jobType, String onSite, int id);

    @Query("SELECT a FROM Application a WHERE a.company.id = ?1")
    List<Application> findByComId(int id, Pageable pageable);
}
