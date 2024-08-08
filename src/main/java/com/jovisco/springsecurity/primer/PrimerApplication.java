package com.jovisco.springsecurity.primer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
public class PrimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimerApplication.class, args);
	}

}
