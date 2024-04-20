package sesc.assement.student.services;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;
//import sesc.assement.student.models.LoginUserDetails;
import sesc.assement.student.models.Student;
import sesc.assement.student.models.User;
import sesc.assement.student.repositories.UserRepository;

@Validated
@Component
public class LoginUserService {
//public class LoginUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public LoginUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ModelAndView loadLoginUserDetails(User user, Student student, @NotNull @NotEmpty String view) {
        if (user == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("user", user);
        if (student != null) {
            modelAndView.addObject("student", student);
        }
        modelAndView.addObject("showFirstName", student != null && student.getStudentFirstName() != null);
        return modelAndView;
    }
}
