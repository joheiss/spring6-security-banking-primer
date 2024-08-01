package com.jovisco.springsecurity.primer.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationEvents {

  @EventListener
  public void onSuccess(AuthenticationSuccessEvent ev) {
    log.info("Login sucessful for user: {}", ev.getAuthentication().getName());
  }

  @EventListener
  public void onFailure(AbstractAuthenticationFailureEvent ev) {
    log.error("Login failed for user: {}, due to {}",
        ev.getAuthentication().getName(),
        ev.getException().getMessage());
  }

}
