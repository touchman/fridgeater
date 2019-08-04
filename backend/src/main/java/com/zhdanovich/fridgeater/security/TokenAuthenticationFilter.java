package com.zhdanovich.fridgeater.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public TokenAuthenticationFilter() {
        super("/**");
        setAuthenticationSuccessHandler((request, response, authentication) ->
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getRequestDispatcher(request.getServletPath() + request.getPathInfo()).forward(request, response);
        });
        setAuthenticationFailureHandler((request, response, authenticationException) -> {
            response.getOutputStream().print(authenticationException.getMessage());
        });
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        String token = request.getHeader("token");
        if (token == null)
            token = request.getParameter("token");
        if (token == null) {
            final TokenAuthentication authentication = new TokenAuthentication(null);
            authentication.setAuthenticated(false);
            this.setDetails(request, authentication);
            if (!request.getRequestURI().contains("login") && !request.getRequestURI().contains("recipe/recipes")) {
                throw new AuthenticationServiceException("Token need to be provided!");
            }
            return authentication;
        }
        final TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
        this.setDetails(request, tokenAuthentication);
        return getAuthenticationManager().authenticate(tokenAuthentication);
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
    }

    protected void setDetails(final HttpServletRequest request, final TokenAuthentication authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain,
                                            final Authentication authResult) throws IOException, ServletException {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                              final AuthenticationException failed) throws IOException {
        logger.debug("failed authentication while attempting to access ");
        //Add more descriptive message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }
}