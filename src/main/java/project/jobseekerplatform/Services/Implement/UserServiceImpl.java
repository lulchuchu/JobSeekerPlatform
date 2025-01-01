package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.dto.UserDtoSignup;
import project.jobseekerplatform.Model.entities.CV;
import project.jobseekerplatform.Model.entities.Experience;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.CVRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final CVRepo cvRepo;


    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepo userRepo, CVRepo cvRepo) {
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.cvRepo = cvRepo;
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
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get();
    }


    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.orElse(null);
    }

    @Override
    public String checkExistedUser(String username, String email) {
        Optional<User> user = userRepo.findByEmailOrUsername(email, username);
        if (user.isEmpty()) {
            return "OK";
        }
        if (user.get().getEmail().equals(email)) {
            return "EMAIL";
        }
        return "USERNAME";
    }

    @Override
    public Boolean checkDuplicateUserName(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public Boolean checkDuplicateEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.isPresent();
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
    public List<UserDtoBasic> listWorkAt(int companyId) {
        List<User> users = userRepo.findAllByCompany(companyId);
        return users.stream().map(user -> modelMapper.map(user, UserDtoBasic.class)).toList();
    }

    @Override
    public void update(User user) {
        userRepo.updateUser(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getShortDescription(), user.getBio());
    }

    @Override
    public void changeCV(Integer userId, String path) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        CV cv = new CV();
        cv.setUser(user.get());
        cv.setFilename(path);
        cv.setPath(path);
        user.get().getCV().add(cv);
        cvRepo.save(cv);
        userRepo.save(user.get());
    }

    @Override
    public void changeProfilePicture(Integer userId, String path) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        userRepo.updateProfilePicture(userId, path);
    }

    @Override
    public List<UserDtoBasic> suggestFollowing(Integer userId) {
        //return max 5 random user and map to UserDtoBasic except user himself
        List<User> users = userRepo.findRandomUser(userId);
        return users.stream().map(user -> modelMapper.map(user, UserDtoBasic.class)).toList();
    }


    @Override
    public List<Job> listApplying(int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        List<Job> jobs = new ArrayList<>();
        for (CV cv : user.get().getCV()) {
            jobs.addAll(cv.getJob());
        }
        return jobs;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetail(user.get());
    }

    @Override
    public List<Experience> listExperience(int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get().getExperiences();
    }


}
