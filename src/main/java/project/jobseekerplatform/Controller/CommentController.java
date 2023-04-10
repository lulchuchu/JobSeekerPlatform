package project.jobseekerplatform.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.CommentDto;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.CommentService;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin

public class CommentController {

//    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
//        this.kafkaTemplate = kafkaTemplate;
        this.commentService = commentService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(Authentication auth, @RequestBody CommentDto comment) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        commentService.createComment(userDetail.getUser().getId(), comment);
//        kafkaTemplate.send("comment-topic", comment.toString());
        return ResponseEntity.ok(comment);
    }

    @CrossOrigin
    @GetMapping("/show")
    public ResponseEntity<?> showComment(@RequestParam int postId) {
        return ResponseEntity.ok(commentService.showComment(postId));
    }
}
