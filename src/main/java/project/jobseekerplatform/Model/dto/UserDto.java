package project.jobseekerplatform.Model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.User} entity
 */
@Data
public class UserDto implements Serializable {
    private int id;
    private String name;
    private String profilePicture;
}