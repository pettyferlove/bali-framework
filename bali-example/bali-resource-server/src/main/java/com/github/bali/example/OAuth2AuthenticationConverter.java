package com.github.bali.example;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * @author Petty
 */
@SuppressWarnings("ALL")
public class OAuth2AuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {



    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return null;
    }
}
