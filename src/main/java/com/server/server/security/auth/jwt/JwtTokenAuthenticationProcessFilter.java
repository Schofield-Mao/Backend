package com.server.server.security.auth.jwt;

import com.server.server.config.WebSecurityConfig;
import com.server.server.model.UserContext;
import com.server.server.security.token.RawJwtAccessToken;
import com.server.server.security.auth.JwtAuthenticationToken;
import com.server.server.security.auth.jwt.extractor.JwtHeaderTokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationProcessFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationFailureHandler failureHandler;
    private JwtHeaderTokenExtractor extractor;

    @Autowired
    public JwtTokenAuthenticationProcessFilter(AuthenticationFailureHandler failureHandler,
                                               JwtHeaderTokenExtractor extractor, RequestMatcher matcher){
        super(matcher);
        this.failureHandler = failureHandler;
        this.extractor = extractor;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String tokenPayload = request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME);
        RawJwtAccessToken accessToken = new RawJwtAccessToken(extractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(accessToken));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        UserContext userContext = (UserContext) authResult.getPrincipal();
        request.setAttribute("CURRENT_USER_ID",userContext.getId());
        chain.doFilter(request,response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request,response,failed);
    }
}
