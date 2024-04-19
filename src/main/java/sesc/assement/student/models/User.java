package sesc.assement.student.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import sesc.assement.student.enums.Role;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "portal_user_student",
            joinColumns =
                    { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "student_id", referencedColumnName = "studentId") })
    @ToString.Exclude
    private Student student;

    public User() {
    }

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password; // This is the password
        this.role = role;
    }

}
