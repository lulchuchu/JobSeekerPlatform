package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.jobseekerplatform.Model.entities.Job;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Job} entity
 */
@Getter
@Setter
@NoArgsConstructor
public class ApplicationDto implements Serializable {
    private int id;
    private String title;
    private String experience;
    private String type;
    private String onSite;
    private String address;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int companyId;
    private int numberOfApplicants;
}