package com.jovisco.springsecurity.primer.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class SecurityConfig {

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.authorizeHttpRequests((requests) -> requests
        .requestMatchers("/contact", "/notices", "/welcome", "/error").permitAll()
        .anyRequest().authenticated());

    // httpSecurity.formLogin(config -> config.disable());
    httpSecurity.formLogin(Customizer.withDefaults());
    httpSecurity.httpBasic(Customizer.withDefaults());

    return httpSecurity.build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
        .password("{bcrypt}$2a$12$9CVTmglu7SXoLhXTRv1Dv.26fo0ZVbtF2tkviEZtzk1pFo//Qc.3u")
        .authorities("read")
        .build();

    UserDetails admin = User.withUsername("admin")
        .password("{bcrypt$2a$12$kfg0mphXmIGS3.fmbWqA5OSGUNokElSVkSJzhZWX/HNTxxpjclllS")
        .authorities("admin")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  CompromisedPasswordChecker compromisedPasswordChecker() {
    return new HaveIBeenPwnedRestApiPasswordChecker();
  }
}
