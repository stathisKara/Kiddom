package kiddom;

//import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
//import org.springframework.security.web.context.*;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@ComponentScan//({"kiddom.kid.controller.model","kiddom.kid.controller.repository","kiddom.kid.controller.service"})
public class WarApplication  extends SpringBootServletInitializer {


//	@Bean
//	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//		ServletRegistrationBean registration = new ServletRegistrationBean(
//				dispatcherServlet);
//		registration.addUrlMappings("/*", "/whatever2/*");
//		return registration;
//	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WarApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WarApplication.class, args);
	}
}
/*import org.springframework.security.web.context.*;

public class SecurityWebApplicationInitializer
		extends AbstractSecurityWebApplicationInitializer {

}*//*
public static void main(String[] args) {
	SpringApplication.run(WarApplication.class, args);
}*/

//}