package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @ManyToMany(mappedBy = "cvs")
    @JsonIgnore
    private List<Job> job;

    private String filename;
    private String path;
}
