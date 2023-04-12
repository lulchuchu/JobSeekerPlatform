package project.jobseekerplatform.Model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.Comment} entity
 */
@Data
public class CommentDto implements Serializable {
    private int id;
    private String contents;
    private LocalDate commentDate;
    private int postId;
    private int userId;
    private String userName;
    private String userProfilePicture;
}