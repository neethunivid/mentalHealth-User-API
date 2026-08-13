package com.mentalhealthapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MentalhealthapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentalhealthapiApplication.class, args);
	}

}
