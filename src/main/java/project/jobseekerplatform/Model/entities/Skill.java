package project.jobseekerplatform.Model.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
public class Skill {
    @Id
    private int id;
    private String name;

    @ManyToMany(mappedBy = "skills")
    private List<Application> applications;

    @ManyToMany(mappedBy = "skills")
    private List<Job> jobs;

    @ManyToMany(mappedBy = "skills")
    private List<User> users;
}
