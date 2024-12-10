package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailOrUsername(String email, String username);

    @Query("SELECT u FROM User u INNER join Experience j ON u.id = j.user.id WHERE j.company.id = ?1")
    List<User> findAllByCompany(int companyId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name, u.email = :email, u.address = :address, u.shortDescription = :shortDescription, u.bio = :bio WHERE u.id = :id")
    void updateUser(@Param("id") Integer id, @Param("name") String name, @Param("email") String email, @Param("address") String address, @Param("shortDescription") String shortDescription, @Param("bio") String bio);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.profilePicture = :path WHERE u.id = :id")
    void updateProfilePicture(Integer id, String path);

    List<User> findAllByFollowersIs(User follower);

    List<User> findAllByFollowingIs(User following);

    @Query(value = "SELECT * FROM job.user u WHERE u.id != ?1 AND u.role = 'USER' ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<User> findRandomUser(Integer userId);
}
