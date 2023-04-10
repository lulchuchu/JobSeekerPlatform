package project.jobseekerplatform.Services.Implement;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.CommentDto;
import project.jobseekerplatform.Model.entities.Comment;
import project.jobseekerplatform.Model.entities.Post;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.CommentRepo;
import project.jobseekerplatform.Services.CommentService;
import project.jobseekerplatform.Services.PostService;
import project.jobseekerplatform.Services.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final UserService userService;
    private final PostService postService;
    private final ModelMapper modelMapper;


    public CommentServiceImpl(ModelMapper modelMapper, CommentRepo commentRepo, UserService userService, PostService postService) {
        this.modelMapper = modelMapper;
        this.commentRepo = commentRepo;
        this.userService = userService;
        this.postService = postService;
    }


    @Override
    public void createComment(Integer userId, CommentDto commentDto) {
        User user = userService.findById(userId);
        Post post = postService.findPost(commentDto.getPostId());
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setCommentDate(LocalDate.now());
        comment.setPost(post);
        comment.setContents(commentDto.getContents());
        commentRepo.save(comment);
    }

    @Override
    public List<CommentDto> showComment(int postId) {
        Post post = postService.findPost(postId);
        return post.getComment().stream().map((comment) -> {
                    CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
                    commentDto.setUserName(comment.getUser().getUsername());
                    commentDto.setUserProfilePicture(comment.getUser().getProfilePicture());
                    return commentDto;
                }
        ).toList();
    }

}
