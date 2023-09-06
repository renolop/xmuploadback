package com.xmlupload.apirest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registro) {
		registro.addMapping("/**")
			.allowedOrigins("http://localhost:4200")
			.allowedMethods("POST");
	}
}
