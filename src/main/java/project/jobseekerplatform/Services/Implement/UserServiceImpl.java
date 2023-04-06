package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.dto.UserDtoSignup;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;


    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepo userRepo) {
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
    }

    @Override
    public void signUp(UserDtoSignup userDtoSignup) {
        User user = new User();
        user.setName(userDtoSignup.getName());
        user.setEmail(userDtoSignup.getEmail());
        user.setUsername(userDtoSignup.getUsername());
        user.setPassword(userDtoSignup.getPassword());
        userRepo.save(user);
    }

    @Override
    public User findById(int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get();
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }


    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public Boolean checkDuplicateUserName(String username) {
        User user = userRepo.findByUsername(username);
        return user != null;
    }

    @Override
    public Boolean checkDuplicateEmail(String email) {
        User user = userRepo.findByEmail(email);
        return user != null;
    }


    @Override
    public void addFollow(int userId, int followId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<User> follow = userRepo.findById(followId);
        if (user.isEmpty() || follow.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        List<User> following = user.get().getFollowing();
        User follower = follow.get();
        if (following.contains(follower)) {
            following.remove(follow.get());
            follow.get().getFollowers().remove(user.get());

        } else {
            following.add(follow.get());
            follow.get().getFollowers().add(user.get());
        }
        userRepo.save(user.get());
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public List<UserDtoBasic> listFollowers(int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get().getFollowers().stream().map(follower -> modelMapper.map(follower, UserDtoBasic.class)).toList();
    }

    @Override
    public List<UserDtoBasic> listFollowing(int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get().getFollowing().stream().map(following -> modelMapper.map(following, UserDtoBasic.class)).toList();
    }

    @Override
    public boolean checkFollow(Integer userId, int followId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<User> follow = userRepo.findById(followId);
        if (user.isEmpty() || follow.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        List<User> following = user.get().getFollowing();
        User follower = follow.get();
        return following.contains(follower);
    }

    @Override
    public List<Application> listApplying(int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get().getApplications();
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetail(user);
    }

    @Override
    public List<Job> listExperience(int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get().getJobs();
    }


}
