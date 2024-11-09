package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.CommentDto;
import project.jobseekerplatform.Model.dto.LikeDto;
import project.jobseekerplatform.Model.dto.PostDto;
import project.jobseekerplatform.Model.entities.Post;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.PostRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.FileStorageService;
import project.jobseekerplatform.Services.PostService;
import project.jobseekerplatform.Services.UserService;

import java.time.LocalDate;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    private final ModelMapper modelMapper;
    private final PostRepo postRepo;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final UserRepo userRepo;


    @Autowired
    public PostServiceImpl(ModelMapper modelMapper, PostRepo postRepo, UserService userService, FileStorageService fileStorageService, UserRepo userRepo) {
        this.modelMapper = modelMapper;
        this.postRepo = postRepo;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.userRepo = userRepo;
    }

    @Override
    public List<PostDto> getNewsFeed(User user) {
        //Tim nhung nguoi user nay follow va lay ra nhung post cua nhung nguoi do
//        List<User> followingPeople = user.getFollowing();
        List<User> followingPeople = userRepo.findAllByFollowersIs(user);
        List<PostDto> newsfeed = new ArrayList<>();
        for (User following : followingPeople) {
            newsfeed.addAll(postRepo.findAllByUserId(following.getId()).stream().map(p -> {
                PostDto postDto = modelMapper.map(p, PostDto.class);
                postDto.setLikeCount(p.getUsersLiked().size());
                postDto.setCommentCount(p.getComment().size());
                return postDto;
            }).toList());
        }
        Collections.sort(newsfeed, Comparator.comparing(PostDto::getPostedDate, Collections.reverseOrder()));
        return newsfeed;
    }

    @Override
    public Page<PostDto> getPostByUserId(int userId, Pageable pageable) {
        long totalPage = postRepo.countByUserId(userId);
        List<PostDto> posts = postRepo.findAllByUserId(userId, pageable).stream().map(p -> {
            PostDto postDto = modelMapper.map(p, PostDto.class);
            postDto.setLikeCount(p.getUsersLiked().size());
            postDto.setCommentCount(p.getComment().size());
            return postDto;
        }).toList();
        return new PageImpl<>(posts, pageable, totalPage);
    }

    @Override
    public Page<PostDto> getPostByCompanyId(int companyId, Pageable pageable) {
        long totalPage = postRepo.countByCompanyId(companyId);
        List<PostDto> posts = postRepo.findAllByCompanyId(companyId, pageable).stream().map(p -> {
            PostDto postDto = modelMapper.map(p, PostDto.class);
            postDto.setLikeCount(p.getUsersLiked().size());
            postDto.setCommentCount(p.getComment().size());
            return postDto;
        }).toList();
        return new PageImpl<>(posts, pageable, totalPage);

    }

    @Override
    public boolean checkReact(int postId, int userId) {
//        User user = userService.findById(userId);
//        Post post = postRepo.findById(postId).get();
//        return post.getUsersLiked().contains(user);
        return postRepo.checkLike(postId, userId) != 0;
    }

    @Override
    public List<LikeDto> listLiked(Integer postId) {
        Post post = postRepo.findById(postId).get();
        return post.getUsersLiked().stream().map(u -> {
            LikeDto likeDto = new LikeDto();
            likeDto.setPostId(postId);
            likeDto.setUserId(u.getId());
            likeDto.setUsername(u.getName());
            return likeDto;
        }).toList();
    }

    @Override
    public int countLike(Integer postId) {
        Post post = postRepo.findById(postId).get();
        return post.getUsersLiked().size();
    }


    @Override
    public void createReact(Integer postId, Integer userId) {
        User user = userService.findById(userId);
        Post post = postRepo.findById(postId).get();
        List<Post> posts = user.getPostsLiked();
        List<User> users = post.getUsersLiked();
        if (posts.contains(post)) {
            posts.remove(post);
            users.remove(user);
        } else {
            posts.add(post);
            users.add(user);
        }
        postRepo.save(post);
        userService.saveUser(user);
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
    public Integer createPost(PostDto postDto, Integer id) {
        User user = userService.findById(id);
        Post post = new Post();
        post.setUser(user);
        post.setContent(postDto.getContent());
        post.setPostedDate(LocalDate.now());
        post.setImages(postDto.getImages());
        postRepo.save(post);
        return post.getId();
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
