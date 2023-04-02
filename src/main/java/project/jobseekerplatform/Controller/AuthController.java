package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.jobseekerplatform.Model.Role;
import project.jobseekerplatform.Model.dto.UserDtoSignup;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Security.jwt.JwtResponse;
import project.jobseekerplatform.Security.jwt.JwtTokenProvider;
import project.jobseekerplatform.Services.UserService;

@RestController
@CrossOrigin
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDtoSignup user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);
            User userr = userService.findByUsername(user.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt, userr.getId(), userr.getUsername(), userr.getName(), userr.getRole(), userr.getProfilePicture()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai thông tin đăng nhập");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.checkDuplicateEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email đã tồn tại");
        } else if (userService.checkDuplicateUserName(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username đã tồn tại");
        } else {
            Role role = user.getRole();
            String password = user.getPassword();
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("Created:\n" + user);
        }
    }
}
