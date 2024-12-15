package my.study.springecommercestudy.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import my.study.springecommercestudy.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;


@Component
public class JwtAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final JwtGenerator jwtGenerator;

    public JwtAuthorizationArgumentResolver(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    // @JwtAuthorization 어노테이션이 있을 경우 동작
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtAuthorization.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {


        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if (httpServletRequest != null) {
            String token = httpServletRequest.getHeader("Authorization");

            if (Objects.nonNull(token) && !token.isBlank()) {
                if (jwtGenerator.validateToken(token)) {
                    return jwtGenerator.extractClaims(token);
                }
            }

            JwtAuthorization annotation = parameter.getParameterAnnotation(JwtAuthorization.class);
            if (annotation != null && !annotation.required()) {
                return Member.emptyMember();

            }
        }

        throw new RuntimeException("토큰 없음");
    }
}