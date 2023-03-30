package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Services.PostService;

@RestController
@RequestMapping("/api/react")
@CrossOrigin

public class ReactController {
    private final PostService postService;

    @Autowired
    public ReactController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getReactList(@RequestParam Integer postId) {
        return ResponseEntity.ok(postService.findPost(postId).getLikeReacts());
    }

    @GetMapping("/count")
    public ResponseEntity<?> countReact(@RequestParam Integer postId) {
        return ResponseEntity.ok(postService.findPost(postId).getLikeReacts().size());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReact(@RequestParam Integer postId, @RequestParam Integer userId) {
        postService.createReact(postId, userId);
        return ResponseEntity.ok("React created successful");
    }
}
