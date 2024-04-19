package sesc.assement.student.services;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sesc.assement.student.enums.Role;
import sesc.assement.student.exceptions.*;
//import sesc.assement.student.models.LoginUserDetails;
import sesc.assement.student.models.Account;
import sesc.assement.student.models.Student;
import sesc.assement.student.models.User;
import sesc.assement.student.repositories.StudentRepository;
import sesc.assement.student.repositories.UserRepository;

import java.util.List;

@Service
public class UserPortalService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    private final IntegrationService integrationService;
//    SecurityContext securityContext;

    @Autowired
    public UserPortalService(UserRepository userRepository, StudentRepository studentRepository, IntegrationService integrationService) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.integrationService = integrationService;
    }

    public User createNewUser(User newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            throw new UserNotValidException();
        }
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
//        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.STUDENT);

        return userRepository.save(newUser);
    }

    public User getStudentByEmail(String email) {
        return userRepository.findStudentByEmail(email);
    }

    public Student findStudentFromUser(@NotNull User user) {
        if(user.getStudent() == null) {
            return null;
        }
        return studentRepository.findStudentByStudentId(user.getStudent().getStudentId());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotValidException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

    public User createStudentFromUser(@NotNull User user) {
        if (studentRepository.findStudentByStudentId(user.getUserId()) != null) {
            throw new StudentNotValidException(user.getEmail());
        }
        Student student = new Student();
        user.setStudent(student);
        userRepository.saveAndFlush(user);
        notifySubscribers(student);
        return user;
    }

    private void notifySubscribers(Student student) {
        Account account = new Account();
        account.setStudentId(student.getStudentId());
        integrationService.notifyStudentCreated(account);
    }

//    public User getLoggedInUser() {
//        if (securityContext == null || securityContext.getAuthentication() == null) {
//            setSecurityContext(SecurityContextHolder.getContext());
//        }
//        Authentication authentication = securityContext.getAuthentication();
//        User user = null;
//        if (authentication != null && authentication.isAuthenticated()) {
//            user = userRepository.findStudentByEmail(authentication.getName());
//        }
//        return user;
//    }
//
//    public void setSecurityContext(SecurityContext securityContext) {
//        this.securityContext = securityContext;
//    }
}
