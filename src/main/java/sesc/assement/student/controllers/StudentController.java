package sesc.assement.student.controllers;

// StudentController.java
import jakarta.annotation.Nullable;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.exceptions.StudentNotValidException;
import sesc.assement.student.models.Student;
import sesc.assement.student.models.User;
import sesc.assement.student.services.StudentService;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public ModelAndView getProfile(@SessionAttribute("user") User user, @Nullable @SessionAttribute("student") Student student) {
        return studentService.getProfile(user, student, "profile");
    }
//    public ResponseEntity<?> getAllStudents() {
//        return ResponseEntity.ok(studentService.getAllStudents());
//    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentService.getStudentById(id));
        } catch (StudentNotValidException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/student")
    public ResponseEntity<Object> createNewStudent(@RequestBody Student newStudent) {
        Student studentId = studentService.createNewStudent(newStudent);
        Long newStudentId = studentId.getStudentId();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(newStudentId));
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok().build();
        } catch (StudentNotValidException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        try {
            Student existingStudent = studentService.getStudentById(id);
            BeanUtils.copyProperties(existingStudent, updatedStudent);
            studentService.updateStudent(existingStudent);
            return ResponseEntity.ok().build();
        } catch (StudentNotValidException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/student/{id}")
    public ModelAndView editProfile(@PathVariable Long id, @ModelAttribute Student student) {
        return studentService.editProfile(id, student);
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

