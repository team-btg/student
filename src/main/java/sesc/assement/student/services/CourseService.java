package sesc.assement.student.services;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.models.*;
import sesc.assement.student.repositories.CourseRepository;
import sesc.assement.student.repositories.StudentRepository;
import sesc.assement.student.repositories.UserRepository;
import sesc.assement.student.enums.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final EnrolmentService enrolmentService;
    private final UserPortalService userService;
    private final IntegrationService integrationService;
    private Student student;
    private Course course;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, UserRepository userRepository, EnrolmentService enrolmentService, UserPortalService userService, IntegrationService integrationService) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.enrolmentService = enrolmentService;
        this.userService = userService;
        this.integrationService = integrationService;
    }

    public Course createSampleCourse(int index) {
        String[] departments = {"CSCI", "MATH", "ENGL", "HIST", "PHYS", "CHEM"};
        String[] courseNames = {"Introduction to Computer Science", "Calculus II", "English Literature", "World History", "Physics I", "Chemistry Lab"};

        String courseCode = departments[index % departments.length] + (100 + index);
        String courseName = courseNames[index % courseNames.length];
        int courseCredits = index;
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 4);
        Date endDate = calendar.getTime();
        String department = "Department " + (index % departments.length + 1);
        double fee = index * 1000.0;

        return new Course(courseCode, courseName, courseCredits, startDate, endDate, department, fee);
    }

    public void createSampleCourses() {
        if (courseRepository.count() > 0) {
            return;
        }

        for (int i = 1; i <= 10; i++) {
            Course course = createSampleCourse(i);
            courseRepository.save(course);
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public ModelAndView getCourses() {
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = courseRepository.findAll();
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    public ModelAndView getCourse(@NotNull Long id, @NotNull User user) {
        populateStudentAndCourse(user, id);

        ModelAndView modelAndView = new ModelAndView("course");
        modelAndView.addObject("course", course);
        Enrolment existingEnrolment = enrolmentService.findEnrolment(course, student);
        modelAndView.addObject("isEnrolled", existingEnrolment != null);
        return modelAndView;
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private Invoice notifySubscribers(Student student, Course course) {
        Account account = new Account();
        account.setStudentId(student.getStudentId());
        Invoice invoice = new Invoice();
        invoice.setAccount(account);
        invoice.setType(Invoice.PayType.TUITION_FEES);
        invoice.setAmount(course.getFee());
        invoice.setDueDate(LocalDate.now().plusMonths(1));
        return integrationService.createCourseFeeInvoice(invoice);
    }

    public ModelAndView enrolInCourse(@NotNull Long id, @NotNull User user) {
        populateStudentAndCourse(user, id);
        if (student == null) {
            userService.createStudentFromUser(user);
        }
        student = userService.findStudentFromUser(user);
        enrolmentService.createEnrolment(course, student);
        Invoice invoice = notifySubscribers(student, course);
        return getModelAndView(true, invoice);
    }

    private void populateStudentAndCourse(User user, Long courseId) {
        student = userService.findStudentFromUser(user);
        course = courseRepository.findById(courseId).orElse(null);
    }

    private ModelAndView getModelAndView(Boolean isEnrolled, @Nullable Invoice invoice) {
        ModelAndView modelAndView = new ModelAndView("course");
        modelAndView.addObject("course", course);
        modelAndView.addObject("student", student);
        modelAndView.addObject("isEnrolled", isEnrolled);
        StringBuilder message = new StringBuilder("You are enrolled in this course.");
        if (invoice != null && invoice.getReference() != null && !invoice.getReference().isEmpty()) {
            message.append(" Please log into the Payment Portal to pay the invoice reference: ")
                    .append(invoice.getReference())
                    .append(".");
        }
        if (isEnrolled) {
            modelAndView.addObject("message", message.toString());
        }
        return modelAndView;
    }
}