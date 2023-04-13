package project.jobseekerplatform.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.Application} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto implements Serializable {
    private int id;
    private String title;
    private String address;
}