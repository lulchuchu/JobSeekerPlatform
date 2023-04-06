package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.entities.Company;
import project.jobseekerplatform.Security.UserDetail;
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

    @GetMapping("/details")
    public ResponseEntity<?> getCompanyById(@RequestParam int companyId) {
        Company company = companyService.getDetailCompany(companyId);
        return ResponseEntity.ok(company);
    }

    @GetMapping("/find")
    public ResponseEntity<?> getAllCompaniesByNameContain(@RequestParam("containing") String string) {
        List<Company> companies = companyService.findCompanyByName(string);
        return ResponseEntity.ok(companies);
    }

    @PostMapping("/addFollow")
    @CrossOrigin

    public ResponseEntity<?> addFollow(Authentication auth, @RequestParam int companyId) {
        try {
            UserDetail userDetail = (UserDetail) auth.getPrincipal();
            companyService.addFollow(userDetail.getUser().getId(), companyId);
            return ResponseEntity.ok("Follow added successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/checkFollow")

    public ResponseEntity<?> checkFollow(Authentication auth, @RequestParam int companyId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        boolean check = companyService.checkFollow(userDetail.getUser().getId(), companyId);
        return ResponseEntity.ok(check);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        try {
            companyService.addCompany(company);
            return ResponseEntity.ok(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
