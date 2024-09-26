package br.com.kapplanapi.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface CustonProviderAuth {
  Authentication authenticate(Authentication paramAuthentication) throws AuthenticationException;
  
  boolean supports(Class<?> paramClass);
}

