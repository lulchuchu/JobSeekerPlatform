package project.jobseekerplatform.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.CommentDto;
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
    public ResponseEntity<?> createPost(@RequestBody CommentDto comment) {
        commentService.createComment(comment);
//        kafkaTemplate.send("comment-topic", comment.toString());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/show/{postId}")
    public ResponseEntity<?> showComment(@PathVariable int postId) {

        return ResponseEntity.ok(commentService.showComment(postId));
    }
}
