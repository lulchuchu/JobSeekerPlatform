package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.FilterDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.ApplicationRepo;
import project.jobseekerplatform.Persistences.CompanyRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.ApplicationService;

import java.time.LocalDate;
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
    public Page<Application> listApplicationByCompany(int companyId, Pageable pageable) {
        List<Application> applications = applicationRepo.findByComId(companyId, pageable);
        return new PageImpl<>(applications, pageable, applications.size());
    }

    @Override
    public List<Application> listAllApplication(FilterDto filterDto) {
        String date = filterDto.getDate();
        String experience = filterDto.getExperience() == null ? "%%" : filterDto.getExperience();
        String jobType = filterDto.getJobType() == null ? "%%" : filterDto.getJobType();
        String onSite = filterDto.getOnSite() == null ? "%%" : filterDto.getOnSite();
        String companyId = filterDto.getCompanyId();

        LocalDate dateResult = LocalDate.now();
        if (date == null) {
            dateResult = LocalDate.of(2000, 1, 1);
        } else {
            if (date.equals("1 day")) {
                dateResult = dateResult.minusDays(1);
            } else if (date.equals("Last week")) {
                dateResult = dateResult.minusWeeks(1);
            } else if (date.equals("Last month")) {
                dateResult = dateResult.minusMonths(1);
            } else if (date.equals("Any time")) {
                dateResult = LocalDate.of(2000, 1, 1);
            }
        }


//        switch (date){
//            case "1 day":
//                dateResult = dateResult.minusDays(1);
//            case "Last week":
//                dateResult = dateResult.minusWeeks(1);
//            case "Last month":
//                dateResult = dateResult.minusMonths(1);
//            case "Any time":
//                dateResult = LocalDate.of(2000, 1, 1);
//        }
        if (companyId == null) {
            return applicationRepo.findJob(dateResult, experience, jobType, onSite);
        } else {

            return applicationRepo.findJobCompany(dateResult, experience, jobType, onSite, Integer.parseInt(companyId));
        }
    }

    @Override
    public boolean checkApply(int id, int applicationId) {
        User user = userRepo.findById(id).get();
        return user.getApplications().stream()
                .anyMatch(application -> application.getId() == applicationId);
    }
}
