package sesc.assement.student.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.models.*;
import sesc.assement.student.repositories.StudentRepository;
import sesc.assement.student.services.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PortalInterceptor implements HandlerInterceptor {
    private final UserPortalService userPortalService;
    private final StudentRepository studentRepository;
    private User user;
    private Student student;

    public PortalInterceptor(UserPortalService userPortalService, StudentRepository studentRepository) {
        this.userPortalService = userPortalService;
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        findUserAndStudent(request);
        request.setAttribute("user", user);
        request.setAttribute("student", student);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        findUserAndStudent(request);

        request.setAttribute("isStudent", student != null);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    private void findUserAndStudent(HttpServletRequest request) {
        User existingStudent = (User) request.getSession().getAttribute("user");
        if (existingStudent != null) {
            user = existingStudent;
            if(user.getStudent() != null)
                student = studentRepository.findStudentByStudentId(user.getStudent().getStudentId());
        }
//        user = userPortalService.getLoggedInUser();
//        if (user != null) {
//            student = studentRepository.findStudentByStudentId(user.getUserId());
//        }
    }
}