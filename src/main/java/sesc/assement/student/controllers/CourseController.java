package sesc.assement.student.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import sesc.assement.student.models.Course;
import sesc.assement.student.models.User;
import sesc.assement.student.services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ModelAndView courses() {
        return courseService.getCourses();
    }

//    @GetMapping("/courses/{id}")
//    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
//        return ResponseEntity.ok(courseService.getCourseById(id));
//    }

    @GetMapping("/courses/{id}")
    public ModelAndView showCourse(@PathVariable Long id, @SessionAttribute("user") User user) {
        return courseService.getCourse(id, user);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/courses/{courseId}/enrol")
    public ModelAndView enrolInCourse(@PathVariable Long courseId, @SessionAttribute("user") User user){
        return courseService.enrolInCourse(courseId, user);
    }
}
