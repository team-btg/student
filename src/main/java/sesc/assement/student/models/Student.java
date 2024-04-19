package sesc.assement.student.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Student {
    @Id
    @Column(nullable = false, unique = true)
    private final Long studentId;
    private String studentFirstName;
    private String studentSurName;
    private String studentCourse;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String email; // This is the username

    @OneToOne(mappedBy = "student")
    private User user;

    public Student() {
        // Generate an 8-digit number starting with '7'
        long randomNumber = (long) (Math.random() * 90000000L) + 70000000L;
        this.studentId = randomNumber;
    }

    public Long getStudentId() {
        return studentId;
    }

}