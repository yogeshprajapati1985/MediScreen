package com.mediscreen;

import org.springframework.boot.SpringApplication;

public class TestMediscreenApplication {

	public static void main(String[] args) {
		SpringApplication.from(MediscreenApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
