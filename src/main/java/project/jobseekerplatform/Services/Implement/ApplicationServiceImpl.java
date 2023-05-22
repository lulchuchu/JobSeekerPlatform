package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.ApplicationDto;
import project.jobseekerplatform.Model.dto.FilterDto;
import project.jobseekerplatform.Model.dto.InterviewDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.Interview;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.ApplicationRepo;
import project.jobseekerplatform.Persistences.CompanyRepo;
import project.jobseekerplatform.Persistences.InterviewRepo;
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
    private final InterviewRepo interviewRepo;

    @Autowired
    public ApplicationServiceImpl(ModelMapper modelMapper, ApplicationRepo applicationRepo, UserRepo userRepo, CompanyRepo companyRepo, InterviewRepo interviewRepo, InterviewRepo interviewRepo1) {
        this.modelMapper = modelMapper;
        this.applicationRepo = applicationRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
        this.interviewRepo = interviewRepo1;
    }


    @Override
    public Application apply(int userId, int applicationId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Application> application = applicationRepo.findById(applicationId);
        if (user.isEmpty() || application.isEmpty()) {
            return null;
        }
        //Xem nhung cong viec ma user da apply
        List<Application> applications = user.get().getApplications();
        //Xem nhung user da apply vao cong viec nay
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
    public void addApplication(ApplicationDto application, Authentication auth) {
        System.out.println("addApplication: " + application);
        Application newApplication = new Application();
        newApplication.setTitle(application.getTitle());
        newApplication.setCompany(companyRepo.findById(application.getCompanyId()).get());
        newApplication.setAddress(application.getAddress());
        newApplication.setExperience(application.getExperience());
        newApplication.setType(application.getType());
        newApplication.setOnSite(application.getOnSite());
        newApplication.setDescription(application.getDescription());
        newApplication.setStartDate(application.getStartDate());
        newApplication.setEndDate(application.getEndDate());
        applicationRepo.save(newApplication);
    }

    @Override
    public Page<Application> listApplicationByCompany(int companyId, Pageable pageable) {
        long totalJobPage = applicationRepo.countByCompanyId(companyId);
        List<Application> applications = applicationRepo.findByComId(companyId, pageable);
        return new PageImpl<>(applications, pageable, totalJobPage);
    }

    @Override
    public Page<Application> listAllApplication(FilterDto filterDto, Pageable pageable) {
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
        if (companyId == null) {
            long totalPage = applicationRepo.countJob(dateResult, experience, jobType, onSite);

            List<Application> applications = applicationRepo.findJob(dateResult, experience, jobType, onSite, pageable);
            return new PageImpl<>(applications, pageable, totalPage);
//            return applications;
        } else {
            long totalPage = applicationRepo.countJob(dateResult, experience, jobType, onSite, Integer.parseInt(companyId));
            List<Application> applications = applicationRepo.findJobCompany(dateResult, experience, jobType, onSite, Integer.parseInt(companyId), pageable);
            return new PageImpl<>(applications, pageable, totalPage);
//            return applications;
        }
    }

    @Override
    public boolean checkApply(int id, int applicationId) {
        User user = userRepo.findById(id).get();
        return user.getApplications().stream()
                .anyMatch(application -> application.getId() == applicationId);
    }

    @Override
    public Application getApplication(int id) {
        return applicationRepo.findById(id).get();
    }

    @Override
    public void setInterview(InterviewDto interviewDto) {
        //Lay ra cong viec
        Application application = applicationRepo.findById(interviewDto.getApplicationId()).get();
        //Lay ra user
        User user = userRepo.findById(interviewDto.getUserId()).get();
        //Xoa interview cu neu co
        if (interviewRepo.findByApplicationIdAndUserId(interviewDto.getApplicationId(), interviewDto.getUserId()) != null
        ) {
            interviewRepo.deleteByApplicationIdAndUserId(interviewDto.getApplicationId(), interviewDto.getUserId());
        }
        //Tao interview moi
        Interview interview = new Interview();
        interview.setApplication(application);
        interview.setUser(user);
        interview.setTime(interviewDto.getTime());
        interviewRepo.save(interview);
        applicationRepo.save(application);
    }

    @Override
    public InterviewDto getInterview(int applicationId, int userId) {
        try {
            Interview interview = interviewRepo.findByApplicationIdAndUserId(applicationId, userId);
            InterviewDto interviewDto = new InterviewDto();
            interviewDto.setApplicationId(applicationId);
            interviewDto.setUserId(userId);
            interviewDto.setTime(interview.getTime());
            return interviewDto;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<ApplicationDto> getAllApplication(int companyId) {
        return applicationRepo.findAllByCompanyId(companyId).stream().map(
                (application) -> {
                    ApplicationDto applicationDto = modelMapper.map(application, ApplicationDto.class);
                    applicationDto.setNumberOfApplicants(application.getUsers().size());
                    return applicationDto;
                }).toList();
    }
}
