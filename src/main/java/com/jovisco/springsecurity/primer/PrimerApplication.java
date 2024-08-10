package com.jovisco.springsecurity.primer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
// @EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class PrimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimerApplication.class, args);
	}

}
