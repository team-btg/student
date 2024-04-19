package sesc.assement.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sesc.assement.student.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId (long courseId);

}

