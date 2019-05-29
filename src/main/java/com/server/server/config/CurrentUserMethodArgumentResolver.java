package com.server.server.config;

import com.server.server.Util.CurrentUserId;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.lang.annotation.Annotation;

public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Annotation[] annotations =  methodParameter.getParameterAnnotations();
        for(Annotation annotation:annotations){
            if(annotation instanceof CurrentUserId){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Long userId = (Long)nativeWebRequest.getAttribute("CURRENT_USER_ID", RequestAttributes.SCOPE_REQUEST);
        if(userId != null){
            return userId;
        }
        throw new MissingServletRequestPartException("CURRENT_USER_ID");
    }
}
