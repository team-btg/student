package sesc.assement.student.controllers;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.models.Student;
import sesc.assement.student.services.GraduationService;

@Controller
public class GraduationController {
    private final GraduationService graduationService;

    public GraduationController(GraduationService graduationService) {
        this.graduationService = graduationService;
    }

    @RequestMapping("/graduation")
    public ModelAndView home(@Nullable @SessionAttribute("student") Student student) {
        return graduationService.getGraduationStatus(student);
    }
}
