package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToOne
    private Company company;

}
