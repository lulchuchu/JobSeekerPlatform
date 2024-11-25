package project.jobseekerplatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.PostDto;
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
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<?> getNewsFeed(Authentication auth) {
        UserDetail userDetail = (UserDetail)auth.getPrincipal();
        List<PostDto> newsfeed = postService.getNewsFeed(userDetail.getUser());
//        kafkaTemplate.send("newsfeed-topic", newsfeed.toString());
        return ResponseEntity.ok(newsfeed);
    }

    @GetMapping("/show")
    public ResponseEntity<?> getPost(@RequestParam int userId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "2") int size) {
//        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        System.out.println("Size: " + size + " Page: " + page + " UserId: " + userId + " ");
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDto> post = postService.getPostByUserId(userId, pageable);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/showCompany")
    public ResponseEntity<?> getPostCompany(@RequestParam int companyId,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size) {
//        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDto> post = postService.getPostByCompanyId(companyId, pageable);
        return ResponseEntity.ok(post);
    }

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, Authentication auth) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        int postId = postService.createPost(postDto, userDetail.getUser());
//        kafkaTemplate.send("post-topic", postDto.toString());
//        return ResponseEntity.ok("Post created successful");
        return ResponseEntity.ok(postId);

    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId, Authentication auth){
        UserDetail userDetail = (UserDetail)auth.getPrincipal();
        User user = userDetail.getUser();
        postService.deletePost(postId, user);
        return ResponseEntity.ok("Post deleted successful");
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
