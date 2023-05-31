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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contents;
    private LocalDate commentDate;

    @ManyToOne
    @JsonIgnore
    private Post post;

    @ManyToOne
    private User user;

//    @Override
//    public String toString() {
//        return "Comment{" +
//                "id=" + id +
//                ", contents='" + contents + '\'' +
//                ", commentDate=" + commentDate +
//                ", post=" + post +
//                ", user=" + user +
//                '}';
//    }
}
