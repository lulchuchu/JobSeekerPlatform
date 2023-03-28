package project.jobseekerplatform.Model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.LikeReact} entity
 */
@Data
public class LikeReactDto implements Serializable {
    private int id;
    private int postId;
    private Integer userId;

}