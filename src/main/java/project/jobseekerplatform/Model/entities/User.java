package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import project.jobseekerplatform.Model.Role;
import project.jobseekerplatform.Model.Skill;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String username;
//    @JsonIgnore
    private String password;
    private String profilePicture;
    private String bio;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Skill skills;
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "followers_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}))
    private List<User> following;
    @ManyToMany(mappedBy = "following")
    @JsonIgnore
    private List<User> followers;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<LikeReact> likeReacts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Job> jobs;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<Application> applications;
}
