package com.github.bali.auth.provider.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.stereotype.Component;

/**
 * @author Pettyfer
 */
@Slf4j
@Component
public class AuthenticationFailureCredentialsExpiredEventListener
		implements ApplicationListener<AuthenticationFailureCredentialsExpiredEvent> {

	@Override
	public void onApplicationEvent(AuthenticationFailureCredentialsExpiredEvent e) {
		log.info(e.getException().getLocalizedMessage());
	}
}
