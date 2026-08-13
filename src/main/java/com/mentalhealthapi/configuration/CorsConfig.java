package com.mentalhealthapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

	  @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	        //.allowedOrigins("http://localhost:3000", "http://localhost:3001") // Allow multiple origins
	                .allowedOriginPatterns("*")
	                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
	                .allowedHeaders("Content-Type","Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With") 
	                .allowCredentials(true) // Add only if you want to access cookie
	                .exposedHeaders("Authorization");
	    }
}
