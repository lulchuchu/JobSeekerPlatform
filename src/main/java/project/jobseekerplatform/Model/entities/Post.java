package project.jobseekerplatform.Model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data


public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String content;
    private LocalDate postedDate;
    private String images;

    @ManyToOne
    private User user;

    @ManyToOne
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "post_react",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "user_id"}))
    private List<User> usersLiked;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;
}
