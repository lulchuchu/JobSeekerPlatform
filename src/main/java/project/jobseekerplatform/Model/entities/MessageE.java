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
public class MessageE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contents;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    private LocalDateTime time;
}
