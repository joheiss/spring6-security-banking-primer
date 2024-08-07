package com.jovisco.springsecurity.primer.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorizationEvents {

  @EventListener
  public void onFailure(AuthorizationDeniedEvent<?> ev) {

    log.error("User {} is not authorized due to {}",
        ev.getAuthentication().get().getName(),
        ev.getAuthorizationDecision().toString());
  }
}
