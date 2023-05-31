package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LikeDto implements Serializable {
    private int id;
    private int userId;
    private int postId;
    private String username;
}
