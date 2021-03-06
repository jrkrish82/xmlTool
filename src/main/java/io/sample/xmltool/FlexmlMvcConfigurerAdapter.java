package io.sample.xmltool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FlexmlMvcConfigurerAdapter implements WebMvcConfigurer{

	
	private static final Logger log = LoggerFactory.getLogger(FlexmlMvcConfigurerAdapter.class);

	@Autowired
	private Environment environment;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		if (environment.acceptsProfiles("dev")) {
			log.info("CrossOrigin is enabled globally for this profile");
			registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
					.allowCredentials(true).allowedOrigins("http://localhost:3000").maxAge(3600);

		} else {
			log.info("CrossOrigin disabled globally for this profile : " + environment);
		}
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}
