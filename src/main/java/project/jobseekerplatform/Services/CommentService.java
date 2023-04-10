package project.jobseekerplatform.Services;


import project.jobseekerplatform.Model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    void createComment(Integer userId, CommentDto commentDto);

    List<CommentDto> showComment(int postId);
}
