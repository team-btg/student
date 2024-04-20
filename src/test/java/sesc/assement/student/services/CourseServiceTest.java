package sesc.assement.student.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.models.*;
import sesc.assement.student.repositories.CourseRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrolmentService enrolmentService;

    @Mock
    private UserPortalService userService;

    @Mock
    private IntegrationService integrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses() {
        Course course = new Course();
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(course));

        List<Course> result = courseService.getAllCourses();

        assertEquals(1, result.size());
        assertEquals(course, result.get(0));
    }

    @Test
    void getCourses() {
        Course course = new Course();
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(course));

        ModelAndView result = courseService.getCourses();

        assertEquals("courses", result.getViewName());
        assertTrue(result.getModel().containsKey("courses"));
        assertEquals(Collections.singletonList(course), result.getModel().get("courses"));
    }

    @Test
    void getCourse() {
        Course course = new Course();
        User user = new User();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(enrolmentService.findEnrolment(any(), any())).thenReturn(new Enrolment());

        ModelAndView result = courseService.getCourse(1L, user);

        assertEquals("course", result.getViewName());
        assertTrue(result.getModel().containsKey("course"));
        assertTrue(result.getModel().containsKey("isEnrolled"));
    }

    @Test
    void getCourseById() {
        Course course = new Course();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(1L);

        assertEquals(course, result);
    }

    @Test
    void enrolInCourse() {
        Course course = new Course();
        User user = new User();
        Invoice invoice = new Invoice();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userService.findStudentFromUser(any())).thenReturn(new Student());
        when(integrationService.createCourseFeeInvoice(any())).thenReturn(invoice);

        ModelAndView result = courseService.enrolInCourse(1L, user);

        assertEquals("course", result.getViewName());
        assertTrue(result.getModel().containsKey("course"));
        assertTrue(result.getModel().containsKey("student"));
        assertTrue(result.getModel().containsKey("isEnrolled"));
    }
}