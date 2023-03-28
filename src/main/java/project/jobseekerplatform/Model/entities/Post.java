package project.jobseekerplatform.Model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String content;
    private LocalDate postedDate;
    private String images;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<LikeReact> likeReacts;
    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

}
