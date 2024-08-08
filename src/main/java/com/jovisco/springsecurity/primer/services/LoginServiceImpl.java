package com.jovisco.springsecurity.primer.services;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.jovisco.springsecurity.primer.ApplicationConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

        private final AuthenticationManager authenticationManager;
        private final Environment env;

        public String login(String username, String password) {

                String jwt = "";

                Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);

                Authentication authenticationResponse = authenticationManager.authenticate(authentication);

                if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
                        if (null != env) {
                                String secret = env.getProperty(
                                                ApplicationConstants.JWT_SECRET_KEY,
                                                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                                jwt = Jwts.builder()
                                                .issuer("Jovisco Bank")
                                                .subject("JWT Token")
                                                .claim("username", authenticationResponse.getName())
                                                .claim("authorities",
                                                                authenticationResponse.getAuthorities().stream().map(
                                                                                GrantedAuthority::getAuthority)
                                                                                .collect(Collectors.joining(",")))
                                                .issuedAt(new java.util.Date())
                                                .expiration(new java.util.Date(
                                                                (new java.util.Date()).getTime() + 30000000))
                                                .signWith(secretKey)
                                                .compact();
                        }
                }
                return jwt;
        }
}
