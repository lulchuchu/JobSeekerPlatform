package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.jobseekerplatform.Exception.ResourceException;
import project.jobseekerplatform.Model.dto.ApplicationDto;
import project.jobseekerplatform.Model.dto.FilterDto;
import project.jobseekerplatform.Model.dto.InterviewDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.Application;
import project.jobseekerplatform.Model.entities.CV;
import project.jobseekerplatform.Model.entities.Interview;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.*;
import project.jobseekerplatform.Services.ApplicationService;
import project.jobseekerplatform.Services.CVParsingService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final CVRepo cvRepo;
    private final CVParsingService cvParsingService;

    @Autowired
    public ApplicationServiceImpl(ModelMapper modelMapper, ApplicationRepo applicationRepo, UserRepo userRepo,
                                  CompanyRepo companyRepo, InterviewRepo interviewRepo, InterviewRepo interviewRepo1, CVRepo cvRepo, WebClient.Builder webClientBuilder, CVParsingService cvParsingService) {
        this.modelMapper = modelMapper;
        this.applicationRepo = applicationRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
        this.interviewRepo = interviewRepo1;
        this.cvRepo = cvRepo;
        this.cvParsingService = cvParsingService;
    }

    @Override
    public void apply(int cvId, int applicationId, User user) {
        Optional<CV> cv = cvRepo.findById(cvId);
        if (cv.isEmpty()) {
            throw new ResourceException("CV not found");
        }
        Optional<Application> application = applicationRepo.findById(applicationId);
        if (application.isEmpty()) {
            throw new ResourceException("Application not found");
        }
        application.get().getCvs().add(cv.get());
        Path root = Paths.get("Files");

        File cvFile = new File(String.valueOf(root.resolve(cv.get().getFilename())));

        // Call the parseCV method
        cvParsingService.parseCV(cvFile, user.getId(), applicationId)
                .doOnNext(parsedData -> {
                    // Process the returned data if needed
                    System.out.println("Parsed CV Data: " + parsedData);
                })
                .doOnError(error -> {
                    throw new ResourceException("Failed to parse CV: " + error.getMessage());
                })
                .subscribe(); // Trigger the WebClient call

        cv.get().getApplication().add(application.get());
        cvRepo.save(cv.get());
        applicationRepo.save(application.get());
    }

    @Override
    public List<UserDtoBasic> listUserApplied(int applicationId) {
        return applicationRepo.findById(applicationId).get().getCvs().stream().map(
                cv -> {
                    UserDtoBasic u = modelMapper.map(cv.getUser(), UserDtoBasic.class);
                    u.setCv(cv);
                    return u;
                }).toList();
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
            // return applications;
        } else {
            long totalPage = applicationRepo.countJob(dateResult, experience, jobType, onSite,
                    Integer.parseInt(companyId));
            List<Application> applications = applicationRepo.findJobCompany(dateResult, experience, jobType, onSite,
                    Integer.parseInt(companyId), pageable);
            return new PageImpl<>(applications, pageable, totalPage);
            // return applications;
        }
    }

    @Override
    public boolean checkApply(User user, int applicationId) {
        List<Integer> cvs = user.getCV().stream().map(CV::getId).toList();
        Optional<Application> application = applicationRepo.findById(applicationId);
        List<CV> cvOfApplication = application.get().getCvs();
        for (CV cv : cvOfApplication) {
            if (cvs.contains(cv.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Application getApplication(int id) {
        return applicationRepo.findById(id).get();
    }

    @Override
    public void setInterview(InterviewDto interviewDto) {
        // Lay ra cong viec
        Application application = applicationRepo.findById(interviewDto.getApplicationId()).get();
        // Lay ra user
        User user = userRepo.findById(interviewDto.getUserId()).get();
        // Xoa interview cu neu co
        if (interviewRepo.findByApplicationIdAndUserId(interviewDto.getApplicationId(),
                interviewDto.getUserId()) != null) {
            interviewRepo.deleteByApplicationIdAndUserId(interviewDto.getApplicationId(), interviewDto.getUserId());
        }
        // Tao interview moi
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
                    applicationDto.setNumberOfApplicants(application.getCvs().size());
                    return applicationDto;
                }).toList();
    }

    @Override
    public void unApply(User user, int applicationId) {
        List<CV> cv = cvRepo.findByUserId(user.getId());
        if (cv.isEmpty()) {
            throw new ResourceException("The user didn't apply for this job");
        }
        Optional<Application> application = applicationRepo.findById(applicationId);
        if (application.isEmpty()) {
            throw new ResourceException("Application not found");
        }
        for (CV cv1 : cv) {
            if (application.get().getCvs().contains(cv1)) {
                application.get().getCvs().remove(cv1);
                applicationRepo.save(application.get());
                return;
            }
        }
    }
}
