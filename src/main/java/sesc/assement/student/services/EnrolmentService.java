package sesc.assement.student.services;

import io.micrometer.common.lang.Nullable;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import sesc.assement.student.exceptions.EnrolmentAlreadyExistsException;
import sesc.assement.student.models.*;
import sesc.assement.student.repositories.EnrolmentRepository;

@Service
public class EnrolmentService {
    private final EnrolmentRepository enrolmentRepository;

    public EnrolmentService(EnrolmentRepository enrolmentRepository) {
        this.enrolmentRepository = enrolmentRepository;
    }

    public Enrolment createEnrolment(@NotNull Course course, @NotNull Student student) {
        if (enrolmentRepository.findEnrolmentByCourseAndStudent(course, student) != null) {
            throw new EnrolmentAlreadyExistsException("Student " + student.getStudentId() + " is already enrolled in course " + course.getCourseName());
        }
        Enrolment enrolment = new Enrolment(student, course);
        return enrolmentRepository.save(enrolment);
    }

    public Enrolment findEnrolment(@NotNull Course course, @Nullable Student student) {
        return enrolmentRepository.findEnrolmentByCourseAndStudent(course, student);
    }
}
