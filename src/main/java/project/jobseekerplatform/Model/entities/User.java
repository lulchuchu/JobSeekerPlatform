package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import project.jobseekerplatform.Model.Role;

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
    private String shortDescription;
    private String address;
    //    @JsonIgnore
    private String password;
    private String profilePicture;

    @ManyToMany(mappedBy = "followers")
    private List<Company> followingCompany;

    @Column(length = 100000000)
    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_skill",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "skill_id"}))
    private List<Skill> skills;

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

    @ManyToMany(mappedBy = "usersLiked")
    @JsonIgnore
    private List<Post> postsLiked;

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
