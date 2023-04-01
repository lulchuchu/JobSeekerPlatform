package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.entities.Company;
import project.jobseekerplatform.Services.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@CrossOrigin

public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/find")
    public List<Company> getAllCompaniesByNameContain(@RequestParam("containing") String string) {
        return companyService.findCompanyByName(string);
    }

    @PostMapping("/add")
    public void addCompany(@RequestBody Company company) {
        companyService.addCompany(company);
    }

}
