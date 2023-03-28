package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.User} entity
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDtoBasic implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String profilePicture;
    private String bio;
}