package project.jobseekerplatform.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {
    private String date;
    private String experience;
    private String jobType;
    private String onSite;
    private String companyId;
    private Integer currPage;
    private Integer numberPerPage;
}
