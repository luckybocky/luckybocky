package com.project.luckybocky.common;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${front_uri}")
	private String frontUri;

	//	@Override
	//	public void addInterceptors(InterceptorRegistry registry) {
	//		registry.addInterceptor(sessionInterceptor())
	//			.excludePathPatterns("/api/user/**", "/error", "/api/swagger-ui/**",
	//				"swagger-resources/**", "/api/docs/**", "/api/api-docs/**");
	//	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(frontUri)
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true).maxAge(3000);
	}
}