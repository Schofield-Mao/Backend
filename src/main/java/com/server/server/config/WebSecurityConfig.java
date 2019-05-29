package com.server.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.server.security.CustomerCorFilter;
import com.server.server.security.RestAuthenticationEntryPoint;
import com.server.server.security.auth.ajax.AjaxAuthenticationProvider;
import com.server.server.security.auth.ajax.AjaxAwareAuthenticationFailureHanddler;
import com.server.server.security.auth.ajax.AjaxAwareAuthenticationSuccessHandller;
import com.server.server.security.auth.ajax.AjaxLoginProcessFilter;
import com.server.server.security.auth.jwt.JwtAuthenticationProvider;
import com.server.server.security.auth.jwt.JwtTokenAuthenticationProcessFilter;
import com.server.server.security.auth.jwt.SkipPathRequestMatcher;
import com.server.server.security.auth.jwt.extractor.JwtHeaderTokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_HEADER_NAME = "X-Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String ADMIN_LOGIN_URL = "/api/auth/adminLogin";
    public static final String REGISTER_URL = "/api/auth/register";
    public static final String API_ROOT_URL = "/api/**";

    @Autowired
    private AjaxAwareAuthenticationSuccessHandller successHandller;
    @Autowired
    private AjaxAwareAuthenticationFailureHanddler failureHanddler;
    @Autowired
    private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtHeaderTokenExtractor extractor;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ObjectMapper mapper;
    protected AjaxLoginProcessFilter buildAjaxLoginProcessFilter(String loginEntryPoint) throws Exception{
        AjaxLoginProcessFilter filter =
                new AjaxLoginProcessFilter(loginEntryPoint,successHandller,failureHanddler,mapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    private JwtTokenAuthenticationProcessFilter buildJwtTokenLoginProcessFilter(List<String> pathToSkip, String pattern) throws Exception{
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathToSkip,pattern);
        JwtTokenAuthenticationProcessFilter filter = new JwtTokenAuthenticationProcessFilter(failureHanddler,extractor,matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndPointList = Arrays.asList(
                AUTHENTICATION_URL,
                REFRESH_TOKEN_URL,
                REGISTER_URL,
                ADMIN_LOGIN_URL
        );

        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                    .authorizeRequests()
                    .antMatchers(permitAllEndPointList.toArray(new String[permitAllEndPointList.size()]))
                    .permitAll()

                .and()
                    .addFilterBefore(new CustomerCorFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildAjaxLoginProcessFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildJwtTokenLoginProcessFilter(
                            permitAllEndPointList, API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);
    }
}
