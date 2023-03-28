package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.entities.Company;

import java.util.List;

public interface CompanyService {
    void addCompany(Company company);

    List<Company> findCompanyByName(String name);
}
