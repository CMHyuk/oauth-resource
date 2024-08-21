package com.oauth.resource.domain.auth;

import com.oauth.resource.domain.user.validator.UserInfoValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RequiredRoleInterceptor implements HandlerInterceptor {

    private final UserInfoValidator userInfoValidator;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        if (handler instanceof HandlerMethod) {
            return handleHandlerMethod((HandlerMethod) handler);
        }
        return true;
    }

    private boolean handleHandlerMethod(HandlerMethod handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        RequiredMaster requiredMaster = handler.getMethodAnnotation(RequiredMaster.class);
        if (Objects.nonNull(requiredMaster)) {
            userInfoValidator.validateMaster(userId);
            return true;
        }

        RequiredAdmin requiredAdmin = handler.getMethodAnnotation(RequiredAdmin.class);
        if (Objects.nonNull(requiredAdmin)) {
            userInfoValidator.validateAdminOrMaster(userId);
            return true;
        }

        return true;
    }
}
