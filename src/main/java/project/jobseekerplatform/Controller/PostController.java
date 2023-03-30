package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.PostDto;
import project.jobseekerplatform.Model.entities.Post;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Security.jwt.JwtTokenProvider;
import project.jobseekerplatform.Services.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin

public class PostController {
    private final PostService postService;
    private final UserRepo userRepo;

//    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public PostController(PostService postService, JwtTokenProvider jwtTokenProvider, UserRepo userRepo) {
        this.postService = postService;
//        this.kafkaTemplate = kafkaTemplate;
        this.userRepo = userRepo;
    }

    @GetMapping("/newsfeed")
    public ResponseEntity<?> getNewsFeed(Authentication auth) {
        UserDetail userDetail = (UserDetail)auth.getPrincipal();
        List<PostDto> newsfeed = postService.getNewsFeed(userDetail.getUser().getId());
//        kafkaTemplate.send("newsfeed-topic", newsfeed.toString());
        return ResponseEntity.ok(newsfeed);
    }

    @GetMapping("/show")
    public ResponseEntity<?> getPost(Authentication auth) {
        UserDetail userDetail = (UserDetail)auth.getPrincipal();
        List<PostDto> post = postService.getPostByUserId(userDetail.getUser().getId());
        return ResponseEntity.ok(post);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto,Authentication auth) {
        UserDetail userDetail = (UserDetail)auth.getPrincipal();
        postService.createPost(postDto, userDetail.getUser().getId());
//        kafkaTemplate.send("post-topic", postDto.toString());
        return ResponseEntity.ok("Post created successful");
    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId, Authentication auth){
        UserDetail userDetail = (UserDetail)auth.getPrincipal();
        User user = userDetail.getUser();
        List<Integer> postIds = user.getPosts().stream().map(Post::getId).toList();
        if (postIds.contains(postId)) {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successful");
        } else {
            return ResponseEntity.ok("You can't delete this post");
        }
    }

    @GetMapping("/show/comment")
    public ResponseEntity<?> getComment(@RequestParam int postId) {
        return ResponseEntity.ok(postService.getComment(postId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto) {
        postService.updatePost(postDto);
        return ResponseEntity.ok("Post updated successful");
    }
}
