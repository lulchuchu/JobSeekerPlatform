package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.Company;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.ApplicationRepo;
import project.jobseekerplatform.Persistences.CompanyRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.ApplicationService;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ModelMapper modelMapper;
    private final ApplicationRepo applicationRepo;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;

    @Autowired
    public ApplicationServiceImpl(ModelMapper modelMapper, ApplicationRepo applicationRepo, UserRepo userRepo, CompanyRepo companyRepo) {
        this.modelMapper = modelMapper;
        this.applicationRepo = applicationRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
    }


    @Override
    public Application apply(int userId, int applicationId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Application> application = applicationRepo.findById(applicationId);
        if (user.isEmpty() || application.isEmpty()) {
            return null;
        }
        List<Application> applications = user.get().getApplications();
        List<User> users = application.get().getUsers();
        if (applications.contains(application.get())) {
            applications.remove(application.get());
            users.remove(user.get());
        } else {
            applications.add(application.get());
            users.add(user.get());
        }

        userRepo.save(user.get());
        applicationRepo.save(application.get());

        return application.get();
    }

    @Override
    public List<UserDtoBasic> listUserApplied(int applicationId) {
        return applicationRepo.findById(applicationId).get().getUsers().stream().map(
                user -> modelMapper.map(user, UserDtoBasic.class)).toList();
    }

    @Override
    public void addApplication(Application application, Authentication auth) {
        applicationRepo.save(application);
    }

    @Override
    public List<Application> listApplicationByCompany(int companyId) {
        Optional<Company> company = companyRepo.findById(companyId);
        if (company.isEmpty()) {
            return null;
        }
        List<Application> applications = company.get().getApplications();
        return applications;
    }

    @Override
    public List<Application> listAllApplication() {
        return applicationRepo.findAll();
    }

    @Override
    public boolean checkApply(int id, int applicationId) {
        User user = userRepo.findById(id).get();
        return user.getApplications().stream()
                .anyMatch(application -> application.getId() == applicationId);
    }
}
