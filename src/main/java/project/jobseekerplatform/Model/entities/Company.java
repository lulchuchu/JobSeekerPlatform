package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String profilePicture;
    private String bio;
    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Job> jobs;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Application> applications;
}
