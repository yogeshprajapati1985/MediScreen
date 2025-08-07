package com.mediscreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.mediscreen.repository")
public class MediscreenApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediscreenApplication.class, args);
	}
}
