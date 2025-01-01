package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Job {
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private Company company;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "job_cv",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "cv_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "cv_id"})
    )
    private List<CV> cvs;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private List<Interview> interviews;
}
