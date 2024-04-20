package sesc.assement.student.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.exceptions.StudentNotValidException;
import sesc.assement.student.models.Student;
import sesc.assement.student.models.User;
import sesc.assement.student.repositories.StudentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewStudent() {
        Student student = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.createNewStudent(student);

        assertEquals(student, result);
    }

    @Test
    void getStudentById() {
        Long studentId = 1L;
        Student student = new Student();
        when(studentRepository.findStudentByStudentId(studentId)).thenReturn(student);

        Student result = studentService.getStudentById(studentId);

        assertEquals(student, result);
    }

    @Test
    void getStudentById_StudentNotValidException() {
        Long studentId = 1L;
        when(studentRepository.findStudentByStudentId(studentId)).thenReturn(null);

        assertThrows(StudentNotValidException.class, () -> studentService.getStudentById(studentId));
    }

    @Test
    void updateStudent() {
        Student student = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.updateStudent(student);

        assertEquals(student, result);
    }

    @Test
    void updateStudent_StudentNotValidException() {
        Student student = new Student();
        Long studentId = 0L;
        when(studentRepository.findStudentByStudentId(studentId)).thenReturn(null);

        assertThrows(StudentNotValidException.class, () -> studentService.updateStudent(student));
    }

    @Test
    void getProfile() {
        User user = new User();
        Student student = new Student();
        //student.setStudentId(1L);
        when(studentRepository.findStudentByStudentId(student.getStudentId())).thenReturn(student);

        ModelAndView result = studentService.getProfile(user, student, "profile");

        assertEquals("profile", result.getViewName());
        assertTrue(result.getModel().containsKey("user"));
        assertTrue(result.getModel().containsKey("student"));
    }

    @Test
    void getProfile_UserIsNull() {
        Student student = new Student();
        //student.setStudentId(1L);

        ModelAndView result = studentService.getProfile(null, student, "profile");

        assertEquals("redirect:/login", result.getViewName());
    }

    @Test
    void editProfile() {
        Long id = 1L;
        Student student = new Student();
        //student.setStudentId(id);
        when(studentRepository.findStudentByStudentId(id)).thenReturn(student);
        when(studentRepository.saveAndFlush(any(Student.class))).thenReturn(student);

        ModelAndView result = studentService.editProfile(id, student);

        assertEquals("profile", result.getViewName());
        assertTrue(result.getModel().containsKey("student"));
        assertTrue(result.getModel().containsKey("updated"));
        assertTrue(result.getModel().containsKey("message"));
    }
}