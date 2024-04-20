package sesc.assement.student.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sesc.assement.student.exceptions.EnrolmentAlreadyExistsException;
import sesc.assement.student.models.Course;
import sesc.assement.student.models.Enrolment;
import sesc.assement.student.models.Student;
import sesc.assement.student.repositories.EnrolmentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrolmentServiceTest {

    @InjectMocks
    private EnrolmentService enrolmentService;

    @Mock
    private EnrolmentRepository enrolmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEnrolment() {
        Course course = new Course();
        Student student = new Student();
        Enrolment enrolment = new Enrolment(student, course);
        when(enrolmentRepository.findEnrolmentByCourseAndStudent(course, student)).thenReturn(null);
        when(enrolmentRepository.save(any(Enrolment.class))).thenReturn(enrolment);

        Enrolment result = enrolmentService.createEnrolment(course, student);

        assertEquals(enrolment, result);
    }

    @Test
    void createEnrolment_AlreadyExists() {
        Course course = new Course();
        Student student = new Student();
        Enrolment enrolment = new Enrolment(student, course);
        when(enrolmentRepository.findEnrolmentByCourseAndStudent(course, student)).thenReturn(enrolment);

        assertThrows(EnrolmentAlreadyExistsException.class, () -> enrolmentService.createEnrolment(course, student));
    }

    @Test
    void findEnrolment() {
        Course course = new Course();
        Student student = new Student();
        Enrolment enrolment = new Enrolment(student, course);
        when(enrolmentRepository.findEnrolmentByCourseAndStudent(course, student)).thenReturn(enrolment);

        Enrolment result = enrolmentService.findEnrolment(course, student);

        assertEquals(enrolment, result);
    }
}