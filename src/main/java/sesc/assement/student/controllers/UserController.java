package sesc.assement.student.controllers;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sesc.assement.student.models.Student;
import sesc.assement.student.models.User;
import sesc.assement.student.services.LoginUserService;
import sesc.assement.student.services.UserPortalService;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
public class UserController {
    private final UserPortalService userService;
    private final LoginUserService loginUserService;

    @Autowired
    public UserController(UserPortalService userService, LoginUserService loginUserService) {
        this.userService = userService;
        this.loginUserService = loginUserService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/enroll")
    public ModelAndView enroll() {
        return new ModelAndView("register");
    }

    @GetMapping("/main")
    public ModelAndView main(@SessionAttribute("user") User user, @Nullable @SessionAttribute("student") Student student) {
        return loginUserService.loadLoginUserDetails(user, student, "main");
        //return new ModelAndView("main");
    }

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        try {
            ModelAndView modelAndView = new ModelAndView("login");

            User existingStudent = userService.getStudentByEmail(email);
            if (existingStudent != null && existingStudent.getPassword().equals(password)) {
                //RequestContextHolder.currentRequestAttributes().setAttribute("user", existingStudent, RequestAttributes.SCOPE_REQUEST);
                request.getSession().setAttribute("user", existingStudent);

                Student student = userService.findStudentFromUser(existingStudent);
                if (student != null) {
                    request.getSession().setAttribute("student", student);
                    request.getSession().setAttribute("isStudent", student != null);
                }

                // Create an authentication token
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(existingStudent.getEmail(), null, new ArrayList<>());
//
//                // Set the authentication in the SecurityContext
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                return new RedirectView("/main");
            } else {
                modelAndView.addObject("isError", true);
                modelAndView.addObject("message", "Invalid email or password");
                return modelAndView;
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user")
    public ModelAndView createNewStudent(@ModelAttribute User newStudent) {
        return userService.createNewUser(newStudent);
        //return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    static class ResponseObject {
        private final Long studentId;

        public ResponseObject(Long id) {
            this.studentId = id;
        }

        public Long getId() {
            return studentId;
        }
    }
}
