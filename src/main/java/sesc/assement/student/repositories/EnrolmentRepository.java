package sesc.assement.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sesc.assement.student.models.*;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Long>{
    //Enrolment findByEnrolmentId(long enrolmentId);
    Enrolment findEnrolmentByCourseAndStudent(Course course, Student student);
}
