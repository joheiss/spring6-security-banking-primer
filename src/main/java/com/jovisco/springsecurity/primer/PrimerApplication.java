package com.jovisco.springsecurity.primer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableWebSecurity(debug = true)
public class PrimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimerApplication.class, args);
	}

}
