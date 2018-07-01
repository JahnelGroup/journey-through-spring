package com.example.basicwebvalidations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This project uses Lombok so if you're running the application
 * from within IntelliJ you must enable annotation processing.
 * File > Settings, and search for annotation processors.
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}