package project.jobseekerplatform.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.jobseekerplatform.Model.dto.CommentDto;
import project.jobseekerplatform.Model.dto.LikeDto;
import project.jobseekerplatform.Model.dto.PostDto;
import project.jobseekerplatform.Model.entities.Post;
import project.jobseekerplatform.Model.entities.User;

import java.util.List;

public interface PostService {
    Integer createPost(PostDto postDto, Integer id);

    Post findPost(int postId);

    void deletePost(int postId);

    void updatePost(PostDto postDto);

    List<PostDto> getNewsFeed(User user);

    Page<PostDto> getPostByUserId(int userId, Pageable pageable);

    void createReact(Integer postId, Integer userId);

    List<CommentDto> getComment(int postId);

    boolean checkReact(int postId, int id);

    List<LikeDto> listLiked(Integer postId);

    int countLike(Integer postId);

    Page<PostDto> getPostByCompanyId(int companyId, Pageable pageable);
}
