package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u INNER join Job j ON u.id = j.user.id WHERE j.company.id = ?1")
    List<User> findAllByCompany(int companyId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name, u.email = :email, u.address = :address, u.shortDescription = :shortDescription WHERE u.id = :id")
    void updateUser(@Param("id") Integer id, @Param("name") String name, @Param("email") String email, @Param("address") String address, @Param("shortDescription") String shortDescription);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.profilePicture = :path WHERE u.id = :id")
    void updateProfilePicture(Integer id, String path);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.CV = :path WHERE u.id = :id")
    void updateCV(Integer id, String path);
}
