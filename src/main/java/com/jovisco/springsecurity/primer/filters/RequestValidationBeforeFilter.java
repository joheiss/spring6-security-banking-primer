package com.jovisco.springsecurity.primer.filters;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Base64;
import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestValidationBeforeFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    log.debug("*** Running custom filter: RequestValidationBeforeFilter ***");

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    var header = req.getHeader(HttpHeaders.AUTHORIZATION);
    if (null != header) {
      header = header.trim();
      if (StringUtils.startsWithIgnoreCase(header, "Basic ")) {
        var base64Token = header.substring(6).getBytes();
        byte[] decoded;
        try {
          decoded = Base64.getDecoder().decode(base64Token);
          var token = new String(decoded);
          var delimiterPos = token.indexOf(":");
          if (delimiterPos == -1) throw new BadCredentialsException("Invalid basic authentication token");
          var email = token.substring(0, delimiterPos);
          if (email.toLowerCase().contains("test")) {
            res.setStatus((HttpServletResponse.SC_BAD_REQUEST));
            return;
          }
        } catch (IllegalArgumentException ex) {
          throw new BadCredentialsException("Failed to decode basic authentication token");
        }
      }
      chain.doFilter(request, response);
    }
  }

}
