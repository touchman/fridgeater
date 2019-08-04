package com.zhdanovich.fridgeater.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
@AllArgsConstructor
public class TokenAuthenticationManager implements AuthenticationManager, AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof TokenAuthentication && ((TokenAuthentication) authentication).getToken() != null) {
                return processAuthentication((TokenAuthentication) authentication);
            } else {
                authentication.setAuthenticated(false);
                return authentication;
            }
        } catch (final Exception ex) {
            if (ex instanceof AuthenticationServiceException)
                throw ex;
        }
        return authentication;
    }

    @Override
    public boolean supports(final Class<? extends Object> authentication) {
        return (TokenAuthentication.class.isAssignableFrom(authentication));
    }

    private TokenAuthentication processAuthentication(final TokenAuthentication authentication) throws AuthenticationException {
        final String token = authentication.getToken();
        final String key = "key123";
        final DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        } catch (final Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        if (claims.get("token_expiration_date", Long.class) == null)
            throw new AuthenticationServiceException("Invalid token");
        final Date expiredDate = new Date(claims.get("token_expiration_date", Long.class));
        if (expiredDate.after(new Date()))
            return buildFullTokenAuthentication(authentication, claims);
        else
            throw new AuthenticationServiceException("Token expired date error");
    }

    private TokenAuthentication buildFullTokenAuthentication(final TokenAuthentication authentication, final DefaultClaims claims) {
        final User user = (User) userDetailsService.loadUserByUsername(claims.get("username", String.class));
        if (user.isEnabled()) {
            final Collection<GrantedAuthority> authorities = user.getAuthorities();
            return new TokenAuthentication(authentication.getToken(), authorities, true, user);
        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }

    public void setUserDetailsService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
