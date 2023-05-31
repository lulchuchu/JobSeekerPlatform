package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterDto {
    private String date;
    private String experience;
    private String jobType;
    private String onSite;
    private String companyId;
    private Integer currPage;
    private Integer numberPerPage;
}
