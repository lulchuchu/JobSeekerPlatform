package project.jobseekerplatform.Model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
//    @Enumerated(EnumType.STRING)
//    private List<Skill> skills;
    private String description;
    private LocalDate endDate;
    @ManyToOne
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "user_application",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"application_id", "user_id"})
    )
    private List<User> users;

}
