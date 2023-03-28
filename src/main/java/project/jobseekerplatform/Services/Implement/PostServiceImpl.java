package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.CommentDto;
import project.jobseekerplatform.Model.dto.PostDto;
import project.jobseekerplatform.Model.entities.Post;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.PostRepo;
import project.jobseekerplatform.Services.FileStorageService;
import project.jobseekerplatform.Services.PostService;
import project.jobseekerplatform.Services.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final ModelMapper modelMapper;
    private final PostRepo postRepo;
    private final UserService userService;
    private final FileStorageService fileStorageService;


    @Autowired
    public PostServiceImpl(ModelMapper modelMapper, PostRepo postRepo, UserService userService, FileStorageService fileStorageService) {
        this.modelMapper = modelMapper;
        this.postRepo = postRepo;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<PostDto> getNewsFeed(int userId) {
        User user = userService.findById(userId);
        List<User> followingPeople = user.getFollowing();
        List<PostDto> newsfeed = new ArrayList<>();
        for (User follower : followingPeople) {
            newsfeed.addAll(postRepo.findAllByUserId(follower.getId()).stream().map(p -> modelMapper.map(p, PostDto.class)).toList());
        }
        return newsfeed;
    }

    @Override
    public List<PostDto> getPostByUserId(int userId) {
        User user = userService.findById(userId);
        return user.getPosts().stream().map(p -> modelMapper.map(p, PostDto.class)).toList();
    }

    @Override
    public void createReact(Integer postId, Integer userId) {

    }

    @Override
    public List<CommentDto> getComment(int postId) {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isEmpty()) {
            throw new RuntimeException("Post not found");
        }
        return post.get().getComment().stream().map(c -> modelMapper.map(c, CommentDto.class)).toList();
    }

    @Override
    public void createPost(PostDto postDto, Integer id) {
        User user = userService.findById(id);
//        System.out.println(user);
        Post post = new Post();
        post.setUser(user);
        post.setContent(postDto.getContent());
        post.setPostedDate(LocalDate.now());
        post.setImages(postDto.getImages());
        postRepo.save(post);
    }

    @Override
    public Post findPost(int postId) {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isEmpty()) {
            throw new RuntimeException("Post not found");
        }
        return post.get();
    }

    @Override
    public void deletePost(int postId) {
        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) {
            throw new RuntimeException("User not found");
        }
        postRepo.deleteById(postId);
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = postRepo.findById(postDto.getId()).orElse(null);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        postRepo.updatePostById(postDto.getId(), postDto.getContent());
    }

}
