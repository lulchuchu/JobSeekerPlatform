package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.UserDtoSignup;
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
    @GetMapping("/details/{userId}")
    public ResponseEntity<?> getDetails(@PathVariable int userId){
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDtoSignup userDtoSignup) {
        userService.signUp(userDtoSignup);
        return ResponseEntity.ok("User created successful");
    }

    @PutMapping("/addfollow/{userId}/{followId}")
    public ResponseEntity<?> addFollow(@PathVariable int userId, @PathVariable int followId) {
        userService.addFollow(userId, followId);
        return ResponseEntity.ok("Follow added successful");
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<?> listFollowing(@PathVariable int userId) {
        return ResponseEntity.ok(userService.listFollowing(userId));
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<?> listFollowers(@PathVariable int userId) {
        return ResponseEntity.ok(userService.listFollowers(userId));
    }

    @GetMapping("/applying")
    public ResponseEntity<?> listApplying(@RequestParam int userId) {
        return ResponseEntity.ok(userService.listApplying(userId));
    }
}
