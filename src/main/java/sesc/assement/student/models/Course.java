package sesc.assement.student.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Course {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseId;
    private String courseCode;
    private String courseName;
    private int courseCredits;
    private Date startDate;
    private Date endDate;
    private String department;
    private double fee;

    public Course() {
    }

    public Course(String courseCode, String courseName,
                  int courseCredits, Date startDate,
                  Date endDate, String department,
                  double fee) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseCredits = courseCredits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.fee = fee;
    }
}
