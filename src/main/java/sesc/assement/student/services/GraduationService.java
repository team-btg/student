package sesc.assement.student.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.exceptions.StudentNotValidException;
import sesc.assement.student.models.Account;
import sesc.assement.student.models.Student;

@Validated
@Component
public class GraduationService {
    private final IntegrationService integrationService;

    public GraduationService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    public ModelAndView getGraduationStatus(Student student) {
        if (student == null) {
            throw new StudentNotValidException();
        }
        Account account = integrationService.getStudentPaymentStatus(student);
        ModelAndView modelAndView = new ModelAndView("graduation");
        modelAndView.addObject("balanceOutstanding", account.isHasOutstandingBalance());
        modelAndView.addObject("message", account.isHasOutstandingBalance() ? "ineligible to graduate" : "eligible to graduate");
        return modelAndView;
    }
}
