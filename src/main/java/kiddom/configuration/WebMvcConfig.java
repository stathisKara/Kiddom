package kiddom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/**Define Url Pages **/
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/").setViewName("/index");
		registry.addViewController("/index").setViewName("/index");
		registry.addViewController("/activity_reg").setViewName("/activity_reg");
		registry.addViewController("/about").setViewName("/about");
		registry.addViewController("/faq").setViewName("/faq");
		registry.addViewController("/profile").setViewName("/profile");
		registry.addViewController("/profileProvider").setViewName("/profileProvider");
		registry.addViewController("/register_prov").setViewName("/register_prov");
		registry.addViewController("/register").setViewName("/register");
		registry.addViewController("/search").setViewName("/search");
		registry.addViewController("/activity").setViewName("/activity");
		registry.addViewController("/activityProvider").setViewName("/activityProvider");
		registry.addViewController("/google_map").setViewName("/google_map");
		registry.addViewController("/category_submit").setViewName("/category_submit");
		registry.addViewController("/buy_points").setViewName("/buy_points");
		registry.addViewController("/admin").setViewName("/admin");
		registry.addViewController("/search2").setViewName("/search");
	}
}