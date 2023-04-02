package project.jobseekerplatform.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.jobseekerplatform.Services.SkillService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getApplicationsBySkillId(int skillId) {
        return ResponseEntity.ok(skillService.getApplicationsBySkillId(skillId));
    }

    @GetMapping("/jobs")
    public ResponseEntity<?> getJobsBySkillId(int skillId) {
        return ResponseEntity.ok(skillService.getJobsBySkillId(skillId));
    }
}
