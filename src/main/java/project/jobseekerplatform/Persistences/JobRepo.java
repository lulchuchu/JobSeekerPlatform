package project.jobseekerplatform.Persistences;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.jobseekerplatform.Model.entities.Job;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepo extends JpaRepository<Job, Integer>, JpaSpecificationExecutor<Job> {
    @Query("SELECT count(*) FROM Job a WHERE a.startDate >= ?1 AND a.experience like ?2 AND a.type like ?3 AND a.onSite like ?4 AND a.endDate >= CURRENT_TIME order by a.startDate desc")
    long countJob(LocalDateTime dateResult, String experience, String jobType, String onSite);

    @Query("SELECT a FROM Job a WHERE a.startDate >= ?1 AND a.experience like ?2 AND a.type like ?3 AND a.onSite like ?4 AND a.endDate >= CURRENT_TIME order by a.startDate desc")
    List<Job> findJob(LocalDateTime dateResult, String experience, String jobType, String onSite, Pageable pageable);

    @Query("SELECT count(*) FROM Job a WHERE a.company.id = ?5 AND a.startDate >= ?1 AND a.experience like ?2 AND a.type like ?3 AND a.onSite like ?4 AND a.endDate >= CURRENT_TIME order by a.startDate desc")
    long countJob(LocalDateTime dateResult, String experience, String jobType, String onSite, int id);

    @Query("SELECT a FROM Job a WHERE a.company.id = ?5 AND a.startDate >= ?1 AND a.experience like ?2 AND a.type like ?3 AND a.onSite like ?4 AND a.endDate >= CURRENT_TIME order by a.startDate desc")
    List<Job> findJobCompany(LocalDateTime dateResult, String experience, String jobType, String onSite, int id, Pageable pageable);

    @Query("SELECT a FROM Job a WHERE a.company.id = ?1")
    List<Job> findByComId(int id, Pageable pageable);

    long countByCompanyId(int companyId);

    List<Job> findAllByCompanyIdAndEndDateAfter(int companyId, LocalDateTime endDate);

    List<Job> findAllByEndDateAfter(LocalDateTime endDate);

    @Query("SELECT DISTINCT j FROM Job j LEFT JOIN j.company c " +
            "WHERE ( LOWER(j.title) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (LOWER(j.address) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Job> searchJobs(
            @Param("query") String query
    );
}
