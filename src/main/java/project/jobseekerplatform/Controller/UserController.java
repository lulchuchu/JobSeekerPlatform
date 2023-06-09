package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Security.UserDetail;
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

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication auth) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        return ResponseEntity.ok(userService.findById(userDetail.getUser().getId()));
    }

    @GetMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam int userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(Authentication auth, @RequestBody User user) {
        UserDetail userDetail1 = (UserDetail) auth.getPrincipal();
        System.out.println(userDetail1.getUser().getId());
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        if (!userDetail1.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body("You can only update your own profile");
        }
        userService.update(user);
        return ResponseEntity.ok("Update successful");
    }


    @PostMapping("/addfollow")
    @CrossOrigin
    public ResponseEntity<?> addFollow(Authentication auth, @RequestParam int followId) {
//        System.out.println("followId" + followId);
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        userService.addFollow(userDetail.getUser().getId(), followId);
        return ResponseEntity.ok("Follow added successful");
    }

    @CrossOrigin
    @GetMapping("/checkfollow")
    public ResponseEntity<?> checkFollow(Authentication auth, @RequestParam int followId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        boolean check = userService.checkFollow(userDetail.getUser().getId(), followId);
        return ResponseEntity.ok(check);
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

    @GetMapping("/workat")
    public ResponseEntity<?> listWorkAt(@RequestParam int companyId) {
        return ResponseEntity.ok(userService.listWorkAt(companyId));
    }

    @PostMapping("/changeCV")
    public ResponseEntity<?> changeCV(Authentication auth, @RequestParam String path) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        userService.changeCV(userDetail.getUser().getId(), path);
        return ResponseEntity.ok("Change CV successful");
    }

    @PostMapping("/changeProfilePicture")
    public ResponseEntity<?> changeProfilePicture(Authentication auth, @RequestParam String path) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        userService.changeProfilePicture(userDetail.getUser().getId(), path);
        return ResponseEntity.ok("Change profile picture successful");
    }
}
