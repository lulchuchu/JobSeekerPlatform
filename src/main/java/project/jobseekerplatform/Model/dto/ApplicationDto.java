package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.Application} entity
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
    private LocalDate startDate;
    private LocalDate endDate;
    private int companyId;
    private int numberOfApplicants;
}