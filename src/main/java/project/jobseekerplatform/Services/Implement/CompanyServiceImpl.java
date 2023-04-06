package project.jobseekerplatform.Services.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    public CompanyServiceImpl(CompanyRepo companyRepo, UserRepo userRepo) {
        this.companyRepo = companyRepo;
        this.userRepo = userRepo;
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
