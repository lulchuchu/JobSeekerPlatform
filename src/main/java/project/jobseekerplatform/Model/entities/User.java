package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.jobseekerplatform.Model.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String shortDescription;
    private String address;
    private String password;
    private String profilePicture;
    private String CV;

    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Company> followingCompany;

    @Column(length = 100000000)
    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "followers_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}))
    private List<User> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> followers;

    @ManyToMany(mappedBy = "usersLiked", fetch = FetchType.LAZY)
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

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Application> applications;

    @ManyToMany(mappedBy = "receivers", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notification> notifications;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<MessageE> sendedMessageES;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<MessageE> receivedMessageES;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Notification> triggerNotifications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Company manageCompany;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Interview> interview;

}
