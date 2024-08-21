package com.jovisco.springsecurity.primer.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.jovisco.springsecurity.primer.filters.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.var;

@EnableMethodSecurity
@Configuration
@Profile("!prod")
public class SecurityConfig {

  /*
   * @Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
   * String introspectionUri;
   * 
   * @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
   * String clientId;
   * 
   * @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
   * String clientSecret;
   */

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

    var csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

    httpSecurity
        .sessionManagement(config -> config
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .requiresChannel(config -> config.anyRequest().requiresInsecure()) // only HTTP
        .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setExposedHeaders(Arrays.asList("Authorization"));
            config.setMaxAge(3600L);
            return config;
          }
        }))
        .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
            .ignoringRequestMatchers("/contact", "/register")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/contact", "/notices", "/error", "/invalidSession")
            .permitAll()
            .requestMatchers("/myAccount").hasRole("USER")
            .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/myLoans").authenticated()
            .requestMatchers("/myCards").hasRole("USER")
            .requestMatchers("/user").authenticated()
            .anyRequest().authenticated());

    httpSecurity.oauth2ResourceServer(
        config -> config.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));

    // httpSecurity.oauth2ResourceServer(
    // rsc -> rsc.opaqueToken(otc -> otc.authenticationConverter(new KeycloakOpaqueRoleConverter())
    // .introspectionUri(this.introspectionUri)
    // .introspectionClientCredentials(this.clientId, this.clientSecret)));

    return httpSecurity.build();
  }
}
