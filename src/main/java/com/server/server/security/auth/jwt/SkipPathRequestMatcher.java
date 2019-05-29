package com.server.server.security.auth.jwt;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class SkipPathRequestMatcher implements RequestMatcher {
    private OrRequestMatcher matchers;
    private RequestMatcher requestMatcher;
    @Autowired
    public SkipPathRequestMatcher(List<String> pathToSkip, String processingPath){
        Assert.notNull(pathToSkip);
        List<RequestMatcher> m = pathToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
        matchers = new OrRequestMatcher(m);
        requestMatcher = new AntPathRequestMatcher(processingPath);
    }
    @Override
    public boolean matches(HttpServletRequest request) {
        if(matchers.matches(request))
            return false;
        return requestMatcher.matches(request)? true:false;
    }
}
