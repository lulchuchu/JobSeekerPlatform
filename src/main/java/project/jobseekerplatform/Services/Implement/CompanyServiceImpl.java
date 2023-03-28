package project.jobseekerplatform.Services.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.entities.Company;
import project.jobseekerplatform.Persistences.CompanyRepo;
import project.jobseekerplatform.Services.CompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;

    @Autowired
    public CompanyServiceImpl(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }


    @Override
    public void addCompany(Company company) {
        companyRepo.save(company);
    }

    @Override
    public List<Company> findCompanyByName(String name) {
        return companyRepo.findAllByNameContaining(name);
    }
}
