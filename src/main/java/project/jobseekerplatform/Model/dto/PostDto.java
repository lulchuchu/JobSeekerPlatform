package project.jobseekerplatform.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.Post} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto implements Serializable {
    private int id;
    private String content;
    private LocalDate postedDate;
    private String images;
    private UserDto user;
    private int likeCount;
    private int commentCount;
    private CompanyDto company;
}