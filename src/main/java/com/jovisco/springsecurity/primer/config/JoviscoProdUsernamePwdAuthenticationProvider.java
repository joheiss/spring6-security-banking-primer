package com.jovisco.springsecurity.primer.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile("prod")
public class JoviscoProdUsernamePwdAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    log.trace("### JoviscoProdUsernamePwdAuthenticationProvider - authenticate is invoked");

    String username = authentication.getName();
    String pwd = authentication.getCredentials().toString();
    var userDetails = userDetailsService.loadUserByUsername(username);
    if (passwordEncoder.matches(pwd, userDetails.getPassword())) {
      return new UsernamePasswordAuthenticationToken(username, pwd, userDetails.getAuthorities());
    } else {
      throw new BadCredentialsException("Invalid password");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
