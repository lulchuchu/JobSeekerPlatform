package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class InterviewDto {
    private int userId;
    private int applicationId;
    private LocalDateTime time;
}
