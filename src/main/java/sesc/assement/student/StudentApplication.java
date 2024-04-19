package sesc.assement.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sesc.assement.student.services.CourseService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class StudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CourseService courseService) {
		return args -> {
			// Create sample courses if the database is empty
			courseService.createSampleCourses();
		};
	}

}
