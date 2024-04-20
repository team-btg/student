package sesc.assement.student.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.exceptions.StudentNotValidException;
import sesc.assement.student.exceptions.UserNotValidException;
import sesc.assement.student.models.*;
import sesc.assement.student.repositories.StudentRepository;
import sesc.assement.student.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPortalServiceTest {

    @InjectMocks
    private UserPortalService userPortalService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private IntegrationService integrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewUser() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findStudentByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ModelAndView result = userPortalService.createNewUser(user);

        assertEquals("login", result.getViewName());
        assertTrue(result.getModel().containsKey("user"));
        assertTrue(result.getModel().containsKey("message"));
    }

    @Test
    void createNewUser_UserNotValidException() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findStudentByEmail(user.getEmail())).thenReturn(user);

        assertThrows(UserNotValidException.class, () -> userPortalService.createNewUser(user));
    }

    @Test
    void getStudentByEmail() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findStudentByEmail(email)).thenReturn(user);

        User result = userPortalService.getStudentByEmail(email);

        assertEquals(user, result);
    }

    @Test
    void findStudentFromUser() {
        User user = new User();
        Student student = new Student();
        user.setStudent(student);
        when(studentRepository.findStudentByStudentId(student.getStudentId())).thenReturn(student);

        Student result = userPortalService.findStudentFromUser(user);

        assertEquals(student, result);
    }

    @Test
    void createStudentFromUser() {
        User user = new User();
        user.setUserId(1L);
        when(studentRepository.findStudentByStudentId(user.getUserId())).thenReturn(null);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        User result = userPortalService.createStudentFromUser(user);

        assertEquals(user, result);
    }

    @Test
    void createStudentFromUser_StudentNotValidException() {
        User user = new User();
        user.setUserId(-1L);
        Student student = new Student();
        when(studentRepository.findStudentByStudentId(user.getUserId())).thenReturn(student);

        assertThrows(StudentNotValidException.class, () -> userPortalService.createStudentFromUser(user));
    }
}