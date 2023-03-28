//package project.jobseekerplatform;
//
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import project.jobseekerplatform.Model.entities.Company;
//import project.jobseekerplatform.Model.entities.User;
//import project.jobseekerplatform.Persistences.CompanyRepo;
//import project.jobseekerplatform.Persistences.UserRepo;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class Init {
//
//    private final UserRepo userRepo;
//    private final CompanyRepo companyRepo;
//
//    @Autowired
//    public Init(UserRepo userRepo, CompanyRepo companyRepo) {
//        this.userRepo = userRepo;
//        this.companyRepo = companyRepo;
//    }
//
//    @PostConstruct
//    void init(){
//        List<User> users = Arrays.asList(
//           addUser("Tien Anh", "tienanh@gmail.com", "tien", "1411", "src/main/resources/static/Pics/Avatar_Aang.png", "I am a student"),
//           addUser("Dieu", "dieu@gmail.com", "dieu", "1411", "src/main/resources/static/Pics/Avatar_Aang.png", "I am a student"),
//           addUser("Cuong", "cuong@gmail.com", "cuong", "1411", "src/main/resources/static/Pics/Avatar_Aang.png", "I am a student")
//        );
//    }
//
//    private User addUser(String name, String email, String username, String password, String profilePicture, String bio){
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setProfilePicture(profilePicture);
//        user.setBio(bio);
//        userRepo.save(user);
//        return user;
//    }
//
//    private Company addCompany(String name, String email, String username, String password, String profilePicture, String bio){
//        Company company = new Company();
//        company.setName(name);
//        company.setProfilePicture(profilePicture);
//        company.setBio(bio);
//        companyRepo.save(company);
//        return company;
//    }
//}
