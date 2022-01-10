package com.runningmate.runningmate.common.Resolver;

import com.runningmate.runningmate.common.annotation.SessionLoginUser;
import com.runningmate.runningmate.common.exception.DuplicateUserException;
import com.runningmate.runningmate.common.exception.NotFoundUserException;
import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.repository.impl.MybatisUserRepository;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

@RequiredArgsConstructor
@Component
public class LoginUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final MybatisUserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionLoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String,String> pathVariables = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        long sessionLoginUserId = SessionUtils.getLoginSessionUserId();
        long requestLoginUserId = Long.parseLong(pathVariables.get("userId"));

        if(sessionLoginUserId != requestLoginUserId) throw new DuplicateUserException("로그인 유저 정보와 요청 유저 정보가 일치하지 않습니다.");
        return userRepository.findByUserId(sessionLoginUserId).orElseThrow(() -> new NotFoundUserException("유저 정보를 찾을 수 없습니다."));
    }
}