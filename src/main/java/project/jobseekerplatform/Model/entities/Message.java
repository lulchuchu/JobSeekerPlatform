package project.jobseekerplatform.Model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int contents;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    private LocalTime time;
}
