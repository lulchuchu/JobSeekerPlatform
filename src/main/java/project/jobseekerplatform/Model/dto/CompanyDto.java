package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto implements Serializable {
    private int id;
    private String name;
    private String profilePicture;
}
