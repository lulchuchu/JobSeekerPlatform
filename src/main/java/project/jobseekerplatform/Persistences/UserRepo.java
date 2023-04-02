package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);
}
