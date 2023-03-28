package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.dto.CommentDto;
import project.jobseekerplatform.Model.dto.PostDto;
import project.jobseekerplatform.Model.entities.Post;

import java.util.List;

public interface PostService {
    void createPost(PostDto postDto, Integer id);

    Post findPost(int postId);

    void deletePost(int postId);

    void updatePost(PostDto postDto);

    List<PostDto> getNewsFeed(int userId);

    List<PostDto> getPostByUserId(int userId);

    void createReact(Integer postId, Integer userId);

    List<CommentDto> getComment(int postId);
}
