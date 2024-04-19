package sesc.assement.student.repositories;

import sesc.assement.student.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByStudentId (long studentId);
    Student findByEmail(String email);
}