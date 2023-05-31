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
public class UserDto implements Serializable {
    private int id;
    private String name;
    private String profilePicture;
}