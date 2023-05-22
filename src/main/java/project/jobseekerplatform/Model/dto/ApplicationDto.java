package project.jobseekerplatform.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link project.jobseekerplatform.Model.entities.Application} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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