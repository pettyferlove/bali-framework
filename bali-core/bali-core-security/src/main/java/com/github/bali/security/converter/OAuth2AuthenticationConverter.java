package com.github.bali.security.converter;

import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.userdetails.BaliUserDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Petty
 */
@SuppressWarnings("ALL")
public class OAuth2AuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        Collection<SimpleGrantedAuthority> authorities = ((Collection<String>) claims.get("authorities"))
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        List<String> roles = authorities.stream().map(i -> {
            return i.getAuthority().replace(SecurityConstant.ROLE_PREFIX, "");
        }).collect(Collectors.toList());
        BaliUserDetails userDetails = BaliUserDetails.builder()
                .id((String) claims.get("id"))
                .username((String) claims.get("username"))
                .nickname((String) claims.get("nickname"))
                .avatar((String) claims.get("avatar"))
                .email((String) claims.get("email"))
                .status(((Long) claims.get("status")).intValue())
                .tenant((String) claims.get("tenant"))
                .roles(roles)
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }
}
