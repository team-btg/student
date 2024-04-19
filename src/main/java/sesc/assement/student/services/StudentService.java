package sesc.assement.student.services;

// StudentService.java
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.exceptions.StudentNotValidException;
import sesc.assement.student.models.Student;
import sesc.assement.student.models.User;
import sesc.assement.student.repositories.StudentRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createNewStudent(Student newStudent) {
        if (newStudent.getStudentId() == null || newStudent.getStudentId() == 0) {
            throw new StudentNotValidException();
        }

        return studentRepository.save(newStudent);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotValidException("Student with id " + studentId + " not found");
        }
        studentRepository.deleteById(studentId);
    }

    public Student getStudentById(Long studentId) {
        var student = studentRepository.findStudentByStudentId(studentId);
        if (student == null || studentId == 0) {
            throw new StudentNotValidException("Student with id " + studentId + " not found");
        }
        return student;
    }

    public Student updateStudent(Student student) {
        if (student.getStudentId() == null || student.getStudentId() == 0) {
            throw new StudentNotValidException();
        }
        return studentRepository.save(student);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public ModelAndView getProfile(User user, Student student, @NotNull @NotEmpty String view) {
        if (user == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("user", user);
        if (student != null) {
            Student existingStudent = getStudentById(student.getStudentId());
            modelAndView.addObject("student", existingStudent);
        }
        return modelAndView;
    }

    public ModelAndView editProfile(Long id, Student student) {
        Student existingStudent = getStudentById(id);
        try {
            BeanUtils.copyProperties(existingStudent, student);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean changed = !existingStudent.equals(student);
        Student updatedStudent = studentRepository.saveAndFlush(student);
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("student", updatedStudent);
        modelAndView.addObject("updated", changed);
        modelAndView.addObject("message", changed ? "Successfully Save!" : "Error Saving!");

        return modelAndView;
    }
}
