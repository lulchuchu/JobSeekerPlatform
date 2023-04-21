package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u INNER join Job j ON u.id = j.user.id WHERE j.company.id = ?1")
    List<User> findAllByCompany(int companyId);
}
