package com.jovisco.springsecurity.primer.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jovisco.springsecurity.primer.ApplicationConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (null != authentication) {
      var env = getEnvironment();
      if (null != env) {
        var secret = env.getProperty(
            ApplicationConstants.JWT_SECRET_KEY,
            ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        var jwt = Jwts.builder()
            .issuer("Jovisco Bank")
            .subject("JWT Token")
            .claim("username", authentication.getName())
            .claim("authorities", authentication.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + 30000000))
            .signWith(secretKey)
            .compact();
        response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
      }
    }
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return !request.getServletPath().equals("/user");
  }

}
