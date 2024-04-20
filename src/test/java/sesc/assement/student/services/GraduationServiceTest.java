package sesc.assement.student.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.exceptions.StudentNotValidException;
import sesc.assement.student.models.Account;
import sesc.assement.student.models.Student;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GraduationServiceTest {

    @InjectMocks
    private GraduationService graduationService;

    @Mock
    private IntegrationService integrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGraduationStatus() {
        Student student = new Student();
        Account account = new Account();
        account.setHasOutstandingBalance(false);
        when(integrationService.getStudentPaymentStatus(student)).thenReturn(account);

        ModelAndView result = graduationService.getGraduationStatus(student);

        assertEquals("graduation", result.getViewName());
        assertTrue(result.getModel().containsKey("balanceOutstanding"));
        assertTrue(result.getModel().containsKey("message"));
        assertEquals(false, result.getModel().get("balanceOutstanding"));
        assertEquals("eligible to graduate", result.getModel().get("message"));
    }

    @Test
    void getGraduationStatus_StudentNotValidException() {
        assertThrows(StudentNotValidException.class, () -> graduationService.getGraduationStatus(null));
    }
}