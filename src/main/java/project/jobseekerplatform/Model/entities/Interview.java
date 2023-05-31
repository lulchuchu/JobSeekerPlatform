package project.jobseekerplatform.Model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Boolean accepted;
    private LocalDateTime time;
    @ManyToOne
    private Application application;
    @ManyToOne
    private User user;
}
