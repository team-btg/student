package sesc.assement.student.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sesc.assement.student.controllers.PortalInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final PortalInterceptor studentInterceptor;

    public MvcConfig(PortalInterceptor studentInterceptor) {
        this.studentInterceptor = studentInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(studentInterceptor).addPathPatterns("/courses/**", "/home", "/enrolments/**", "/profile/**", "/graduation");
    }
}
