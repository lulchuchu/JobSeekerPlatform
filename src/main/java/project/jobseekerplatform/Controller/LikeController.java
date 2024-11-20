package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.PostService;

@RestController
@RequestMapping("/api/like")
@CrossOrigin
public class LikeController {
    private final PostService postService;

    @Autowired
    public LikeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getLikes(@RequestParam Integer postId) {
        return ResponseEntity.ok(postService.listLiked(postId));
    }

    @GetMapping("/count")
    public ResponseEntity<?> countLike(@RequestParam Integer postId) {
        return ResponseEntity.ok(postService.countLike(postId));
    }

    @GetMapping("/checkLiked")
    public ResponseEntity<?> checkLiked(Authentication authentication, @RequestParam Integer postId) {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return ResponseEntity.ok(postService.checkReact(postId, userDetail.getUser().getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLike(Authentication authentication, @RequestParam Integer postId) {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        postService.createReact(postId, userDetail.getUser().getId());
        return ResponseEntity.ok("React created successful");
    }
}
