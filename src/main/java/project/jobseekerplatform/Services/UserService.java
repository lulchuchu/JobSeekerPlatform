package project.jobseekerplatform.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.dto.UserDtoSignup;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void signUp(UserDtoSignup userDtoSignup);

    User findById(int userId);

    User findByUsername(String username);

    User findByEmail(String email);

    Boolean checkDuplicateUserName(String username);

    Boolean checkDuplicateEmail(String email);

    void addFollow(int userId, int followId);

    void saveUser(User user);

    List<UserDtoBasic> listFollowers(int userId);

    List<UserDtoBasic> listFollowing(int userId);

    List<Application> listApplying(int userId);

    UserDetails loadUserByUsername(String username);

    List<Job> listExperience(int userId);

    boolean checkFollow(Integer id, int followId);

    List<UserDtoBasic> listWorkAt(int companyId);
}
