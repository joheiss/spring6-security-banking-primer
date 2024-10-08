package com.jovisco.springsecurity.primer.config;

import java.util.Collections;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.jovisco.springsecurity.primer.exceptionhandling.CustomBasicAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;

@EnableMethodSecurity
@Configuration
@Profile("prod")
public class SecurityConfigProd {

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .sessionManagement(config -> config
            .invalidSessionUrl("/invalidSession")
            .maximumSessions(1)
            .expiredUrl("/expiredSession"))
        .requiresChannel(config -> config.anyRequest().requiresSecure()) // only HTTPS
        .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("https://localhost:4200"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(3600L);
            return config;
          }
        }))

        .csrf(csrfConfig -> csrfConfig.disable())
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/contact", "/notices", "/welcome", "/register", "/error", "/invalidSession",
                "/expiredSession")
            .permitAll()
            .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
            .requestMatchers("/myAccount").hasRole("USER")
            .requestMatchers("/myBalance").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT")
            .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
            .requestMatchers("/myLoans").hasRole("USER")
            .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
            .requestMatchers("/myCards").hasRole("USER")
            .anyRequest().authenticated());

    // httpSecurity.formLogin(config -> config.disable());
    httpSecurity.formLogin(Customizer.withDefaults());
    httpSecurity.httpBasic(config -> config.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

    return httpSecurity.build();
  }

  /*
   * -- REPLACED by xxxUserDetailsService ---
   * 
   * @Bean
   * UserDetailsService userDetailsService(DataSource dataSource) {
   * // UserDetails user = User.withUsername("user")
   * // .password("{bcrypt}$2a$12$9CVTmglu7SXoLhXTRv1Dv.26fo0ZVbtF2tkviEZtzk1pFo//Qc.3u")
   * // .authorities("read")
   * // .build();
   * 
   * // UserDetails admin = User.withUsername("admin")
   * // .password("{bcrypt$2a$12$kfg0mphXmIGS3.fmbWqA5OSGUNokElSVkSJzhZWX/HNTxxpjclllS")
   * // .authorities("admin")
   * // .build();
   * 
   * // return new InMemoryUserDetailsManager(user, admin);
   * return new JdbcUserDetailsManager(dataSource);
   * }
   * 
   * --
   */

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  CompromisedPasswordChecker compromisedPasswordChecker() {
    return new HaveIBeenPwnedRestApiPasswordChecker();
  }
}
