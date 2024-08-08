package com.jovisco.springsecurity.primer.filters;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthoritiesLoggingAfterFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (null != auth) {
      log.info("User {} is sucessfully authenticated, and has authorities: {}",
          auth.getName(),
          auth.getAuthorities().toString());

    }
    chain.doFilter(request, response);
  }

}
