package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.jobseekerplatform.Exception.ResourceException;
import project.jobseekerplatform.Model.dto.ApplicationDto;
import project.jobseekerplatform.Model.dto.FilterDto;
import project.jobseekerplatform.Model.dto.InterviewDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.CV;
import project.jobseekerplatform.Model.entities.Interview;
import project.jobseekerplatform.Model.entities.Job;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.*;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.CVParsingService;
import project.jobseekerplatform.Services.CompanyService;
import project.jobseekerplatform.Services.JobService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final ModelMapper modelMapper;
    private final JobRepo jobRepo;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;
    private final InterviewRepo interviewRepo;
    private final CVRepo cvRepo;
    private final CVParsingService cvParsingService;
    private final CompanyService companyService;

    @Autowired
    public JobServiceImpl(ModelMapper modelMapper, JobRepo jobRepo, UserRepo userRepo, CompanyRepo companyRepo, InterviewRepo interviewRepo, InterviewRepo interviewRepo1, CVRepo cvRepo, WebClient.Builder webClientBuilder, CVParsingService cvParsingService, CompanyService companyService) {
        this.modelMapper = modelMapper;
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
        this.interviewRepo = interviewRepo1;
        this.cvRepo = cvRepo;
        this.cvParsingService = cvParsingService;
        this.companyService = companyService;
    }

    static Specification<Job> opening() {
        return (job, cq, cb) -> cb.greaterThanOrEqualTo(job.get("endDate"), LocalDateTime.now());
    }

    static Specification<Job> matchExperience(String experience) {
        if (experience == null) {
            return null;
        }
        return (job, cq, cb) -> cb.like(job.get("experience"), experience);
    }

    static Specification<Job> matchJobType(String jobType) {
        if (jobType == null) {
            return null;
        }
        return (job, cq, cb) -> cb.like(job.get("type"), jobType);
    }

    static Specification<Job> matchOnSite(String onSite) {
        if (onSite == null) {
            return null;
        }
        return (job, cq, cb) -> cb.like(job.get("onSite"), onSite);
    }

    static Specification<Job> afterDate(LocalDateTime date) {
        return (job, cq, cb) -> cb.greaterThanOrEqualTo(job.get("startDate"), date);
    }

    static Specification<Job> titleSearch(String title) {
        return (job, cq, cb) -> cb.like(cb.lower(job.get("title")), "%" + title.toLowerCase() + "%");
    }

    static Specification<Job> addressSearch(String address) {
        return (job, cq, cb) -> cb.like(cb.lower(job.get("address")), "%" + address.toLowerCase() + "%");
    }

    static Specification<Job> companySearch(String companyName) {
        return (job, cq, cb) -> cb.like(cb.lower(job.get("company").get("name")), "%" + companyName.toLowerCase() + "%");
    }

    static Specification<Job> matchCompany(String companyId) {
        if (companyId == null) {
            return null;
        }
        return (job, cq, cb) -> cb.equal(job.get("company").get("id"), companyId);
    }

    @Override
    public void apply(int cvId, int applicationId, User user) {
        Optional<CV> cv = cvRepo.findById(cvId);
        if (cv.isEmpty()) {
            throw new ResourceException("CV not found");
        }
        Optional<Job> application = jobRepo.findById(applicationId);
        if (application.isEmpty()) {
            throw new ResourceException("Application not found");
        }
        application.get().getCvs().add(cv.get());
        Path root = Paths.get("Files");

        File cvFile = new File(String.valueOf(root.resolve(cv.get().getFilename())));

        // Call the parseCV method
        cvParsingService.parseCV(cvFile, user.getId(), applicationId).doOnNext(parsedData -> {
            // Process the returned data if needed
            System.out.println("Parsed CV Data: " + parsedData);
        }).doOnError(error -> {
            throw new ResourceException("Failed to parse CV: " + error.getMessage());
        }).subscribe(); // Trigger the WebClient call

        cv.get().getJob().add(application.get());
        cvRepo.save(cv.get());
        jobRepo.save(application.get());
    }

    @Override
    public List<UserDtoBasic> listUserApplied(int applicationId) {
        return jobRepo.findById(applicationId).get().getCvs().stream().map(cv -> {
            UserDtoBasic u = modelMapper.map(cv.getUser(), UserDtoBasic.class);
            u.setCv(cv);
            return u;
        }).toList();
    }

    @Override
    public void addJob(ApplicationDto application, Authentication auth) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        User user = userDetail.getUser();
        if (user.getManageCompany() == null) {
            throw new ResourceException("You are not allowed to add job");
        }

        System.out.println("addApplication: " + application);
        Job newJob = new Job();
        newJob.setTitle(application.getTitle());
        newJob.setCompany(companyRepo.findById(user.getManageCompany().getId()).get());
        newJob.setAddress(application.getAddress());
        newJob.setExperience(application.getExperience());
        newJob.setType(application.getType());
        newJob.setOnSite(application.getOnSite());
        newJob.setDescription(application.getDescription());
        newJob.setStartDate(application.getStartDate());
        newJob.setEndDate(application.getEndDate());
        jobRepo.save(newJob);
    }

    @Override
    public Page<Job> listJobByCompany(int companyId, Pageable pageable) {
        long totalJobPage = jobRepo.countByCompanyId(companyId);
        List<Job> jobs = jobRepo.findByComId(companyId, pageable);
        return new PageImpl<>(jobs, pageable, totalJobPage);
    }

    public List<Job> listAll() {
        return jobRepo.findAllByEndDateAfter(LocalDateTime.now());
    }

    @Override
    public Page<Job> queryJobs(FilterDto filterDto, Pageable pageable) {
        String date = filterDto.getDate();
        LocalDateTime dateResult = LocalDateTime.now();
        if (date == null) {
            dateResult = LocalDateTime.of(2000, 1, 1, 0, 0);
        } else {
            if (date.equals("1 day")) {
                dateResult = dateResult.minusDays(1);
            } else if (date.equals("Last week")) {
                dateResult = dateResult.minusWeeks(1);
            } else if (date.equals("Last month")) {
                dateResult = dateResult.minusMonths(1);
            } else if (date.equals("Any time")) {
                dateResult = LocalDateTime.of(2000, 1, 1, 0, 0);
            }
        }

        // Group the OR conditions first
        Specification<Job> searchSpec = filterDto.getQuery() != null ? Specification.where(titleSearch(filterDto.getQuery()))
                .or(addressSearch(filterDto.getQuery())).or(companySearch(filterDto.getQuery())) : null;

        // Then apply all AND conditions
        long totalPage = jobRepo.count(Specification.where(opening())
                .and(matchExperience(filterDto.getExperience()))
                .and(matchJobType(filterDto.getJobType()))
                .and(matchOnSite(filterDto.getOnSite()))
                .and(afterDate(dateResult))
                .and(searchSpec)
                .and(matchCompany(filterDto.getCompanyId())));

        return jobRepo.findAll(Specification.where(opening())
                .and(matchExperience(filterDto.getExperience()))
                .and(matchJobType(filterDto.getJobType()))
                .and(matchOnSite(filterDto.getOnSite()))
                .and(afterDate(dateResult))
                .and(searchSpec)
                .and(matchCompany(filterDto.getCompanyId())), pageable);
    }

    @Override
    public Page<Job> listAllJob(FilterDto filterDto, Pageable pageable) {
        String date = filterDto.getDate();
        String experience = filterDto.getExperience() == null ? "%%" : filterDto.getExperience();
        String jobType = filterDto.getJobType() == null ? "%%" : filterDto.getJobType();
        String onSite = filterDto.getOnSite() == null ? "%%" : filterDto.getOnSite();
        String companyId = filterDto.getCompanyId();

        LocalDateTime dateResult = LocalDateTime.now();
        if (date == null) {
            dateResult = LocalDateTime.of(2000, 1, 1, 0, 0);
        } else {
            if (date.equals("1 day")) {
                dateResult = dateResult.minusDays(1);
            } else if (date.equals("Last week")) {
                dateResult = dateResult.minusWeeks(1);
            } else if (date.equals("Last month")) {
                dateResult = dateResult.minusMonths(1);
            } else if (date.equals("Any time")) {
                dateResult = LocalDateTime.of(2000, 1, 1, 0, 0);
            }
        }
        if (companyId == null) {
            long totalPage = jobRepo.countJob(dateResult, experience, jobType, onSite);

            List<Job> jobs = jobRepo.findJob(dateResult, experience, jobType, onSite, pageable);
            return new PageImpl<>(jobs, pageable, totalPage);
            // return applications;
        } else {
            long totalPage = jobRepo.countJob(dateResult, experience, jobType, onSite, Integer.parseInt(companyId));
            List<Job> jobs = jobRepo.findJobCompany(dateResult, experience, jobType, onSite, Integer.parseInt(companyId), pageable);
            return new PageImpl<>(jobs, pageable, totalPage);
            // return applications;
        }
    }

    @Override
    public boolean checkApply(User user, int applicationId) {
        List<Integer> cvs = user.getCV().stream().map(CV::getId).toList();
        Optional<Job> application = jobRepo.findById(applicationId);
        List<CV> cvOfApplication = application.get().getCvs();
        for (CV cv : cvOfApplication) {
            if (cvs.contains(cv.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Job getJob(int id) {
        return jobRepo.findById(id).get();
    }

    @Override
    public void setInterview(InterviewDto interviewDto) {
        // Lay ra cong viec
        Job job = jobRepo.findById(interviewDto.getApplicationId()).get();
        // Lay ra user
        User user = userRepo.findById(interviewDto.getUserId()).get();
        // Xoa interview cu neu co
        if (interviewRepo.findByJobIdAndUserId(interviewDto.getApplicationId(), interviewDto.getUserId()) != null) {
            interviewRepo.deleteByJobIdAndUserId(interviewDto.getApplicationId(), interviewDto.getUserId());
        }
        // Tao interview moi
        Interview interview = new Interview();
        interview.setJob(job);
        interview.setUser(user);
        interview.setTime(interviewDto.getTime());
        interviewRepo.save(interview);
        jobRepo.save(job);
    }

    @Override
    public InterviewDto getInterview(int applicationId, int userId) {
        try {
            Interview interview = interviewRepo.findByJobIdAndUserId(applicationId, userId);
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
    public List<ApplicationDto> getAllJob(int companyId) {
        return jobRepo.findAllByCompanyIdAndEndDateAfter(companyId, LocalDateTime.now()).stream().map((application) -> {
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
        Optional<Job> application = jobRepo.findById(applicationId);
        if (application.isEmpty()) {
            throw new ResourceException("Application not found");
        }
        for (CV cv1 : cv) {
            if (application.get().getCvs().contains(cv1)) {
                application.get().getCvs().remove(cv1);
                jobRepo.save(application.get());
                return;
            }
        }
    }

    @Override
    public int closeJob(int applicationId, int id) {
        Optional<Job> job = jobRepo.findById(applicationId);
        if (job.isEmpty()) {
            throw new ResourceException("Application not found");
        }
        if (!companyService.checkAdmin(job.get().getCompany().getId(), id)) {
            throw new ResourceException("Do not have the permission to do this action");
        }

        job.get().setEndDate(LocalDateTime.now());
        jobRepo.save(job.get());
        return (job.get().getId());
    }
}
