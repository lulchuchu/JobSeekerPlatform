package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String experience;
    //full-time, part-time
    private String type;
    private String onSite;
    private String address;
    @Column(length = 100000000)

    private String description;
    private LocalDate startDate;
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
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    private List<Interview> interviews;
}
