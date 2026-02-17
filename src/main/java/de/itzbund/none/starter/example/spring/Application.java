package de.itzbund.none.starter.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot Application Klasse
 */
@SpringBootApplication
public class Application {

	/**
	 * Haupteinstiegspunkt
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}