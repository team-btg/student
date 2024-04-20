package sesc.assement.student.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.models.Student;
import sesc.assement.student.models.User;
import sesc.assement.student.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginUserServiceTest {

    @InjectMocks
    private LoginUserService loginUserService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadLoginUserDetails() {
        User user = new User();
        Student student = new Student();
        String view = "home";

        ModelAndView result = loginUserService.loadLoginUserDetails(user, student, view);

        assertEquals(view, result.getViewName());
        assertTrue(result.getModel().containsKey("user"));
        assertTrue(result.getModel().containsKey("student"));
        assertTrue(result.getModel().containsKey("showFirstName"));
    }

    @Test
    void loadLoginUserDetails_UserIsNull() {
        Student student = new Student();
        String view = "home";

        ModelAndView result = loginUserService.loadLoginUserDetails(null, student, view);

        assertEquals("redirect:/login", result.getViewName());
    }
}