package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Services.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam int userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }


    @PutMapping("/addfollow")
    public ResponseEntity<?> addFollow(@RequestParam int userId, @RequestParam int followId) {
        userService.addFollow(userId, followId);
        return ResponseEntity.ok("Follow added successful");
    }

    @GetMapping("/following")
    public ResponseEntity<?> listFollowing(@RequestParam int userId) {
        return ResponseEntity.ok(userService.listFollowing(userId));
    }

    @GetMapping("/followers")
    public ResponseEntity<?> listFollowers(@RequestParam int userId) {
        return ResponseEntity.ok(userService.listFollowers(userId));
    }

    @GetMapping("/applying")
    public ResponseEntity<?> listApplying(@RequestParam int userId) {
        return ResponseEntity.ok(userService.listApplying(userId));
    }

    @GetMapping("/experience")
    public ResponseEntity<?> listExperience(@RequestParam int userId) {
        return ResponseEntity.ok(userService.listExperience(userId));
    }
}
