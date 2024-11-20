package project.jobseekerplatform.Model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String profilePicture;
    private String website;
    private String shortDescription;
    //Technology, Information ...
    private String industry;
    private String companySize;
    private String location;

    @OneToMany
    private List<User> admin;

    @ManyToMany
    @JoinTable(
            name = "company_followers",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"company_id", "user_id"}))
    @JsonIgnore
    private List<User> followers;

    @Column(length = 100000000)
    private String bio;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Job> jobs;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Application> applications;
}
