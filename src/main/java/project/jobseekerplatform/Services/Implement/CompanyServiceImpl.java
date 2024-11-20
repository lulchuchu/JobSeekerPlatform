package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Exception.ResourceException;
import project.jobseekerplatform.Model.Role;
import project.jobseekerplatform.Model.dto.CompanyDto;
import project.jobseekerplatform.Model.entities.Company;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.CompanyRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.CompanyService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepo companyRepo, UserRepo userRepo, ModelMapper modelMapper) {
        this.companyRepo = companyRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addCompany(Company company) {
        companyRepo.save(company);
    }

    @Override
    public List<Company> findCompanyByName(String name) {
        return companyRepo.findAllByNameContaining(name);
    }

    @Override
    public Company getDetailCompany(int companyId) {
        Optional<Company> company = companyRepo.findById(companyId);

        if (company.isEmpty()) {
            throw new RuntimeException("Company not found");
        }

        return company.get();
    }

    @Override
    public boolean checkFollow(int userId, int companyId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Company> company = companyRepo.findById(companyId);

        if (user.isEmpty() || company.isEmpty()) {
            throw new RuntimeException("User or company not found");
        }

        List<Company> followingCompanies = user.get().getFollowingCompany();

        return followingCompanies.contains(company.get());
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepo.findAll().stream().map(company -> modelMapper.map(company, CompanyDto.class)).toList();
    }

    @Override
    public void grantUserToCompany(int companyId, int userId) {
        Optional<User> u = userRepo.findById(userId);
        Optional<Company> c = companyRepo.findById(companyId);
        if (u.isEmpty() || c.isEmpty()) {
            throw new ResourceException("User or company not found");
        }
        User user = u.get();
        user.setRole(Role.ADMIN);
        user.setManageCompany(c.get());
        userRepo.save(user);
    }

    @Override
    public boolean checkAdmin(int companyId, int userId) {
        Optional<User> u = userRepo.findById(userId);
        if (u.isEmpty()) {
            throw new ResourceException("User not found");
        }
        if (u.get().getManageCompany() == null) {
            return false;
        }

        return u.get().getManageCompany().getId() == companyId;
    }

    @Override
    public void addFollow(int userId, int companyId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Company> company = companyRepo.findById(companyId);

        if (user.isEmpty() || company.isEmpty()) {
            throw new RuntimeException("User or company not found");
        }

        List<Company> followingCompanies = user.get().getFollowingCompany();
        List<User> followers = company.get().getFollowers();

        if (followingCompanies.contains(company.get())) {
            followingCompanies.remove(company.get());
            followers.remove(user.get());
        } else {
            followingCompanies.add(company.get());
            followers.add(user.get());
        }

        userRepo.save(user.get());
        companyRepo.save(company.get());
    }
}
