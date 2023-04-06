package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    //full-time, part-time, internship, volunteer
    private String type;

    @Column(length = 100000000)
    private String description;
    private LocalDate startDate;
    private LocalDate leaveDate;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "job_skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "skill_id"}))
    private List<Skill> skills;

    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToOne
    private Company company;

}
