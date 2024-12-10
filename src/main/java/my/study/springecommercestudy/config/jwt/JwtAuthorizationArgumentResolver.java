package my.study.springecommercestudy.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


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
        // TODO https://velog.io/@haerong22/Argument-Resolver-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Authorizationwith-JWT#-jwtauthorization-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-%EC%83%9D%EC%84%B1
        // 헤더 값 체크
        /*if (httpServletRequest != null) {
            String token = httpServletRequest.getHeader("Authorization");

            if (token != null && !token.trim().equals("")) {
                // 토큰 있을 경우 검증
                if (jwtGenerator.validateToken(token)) {
                    // 검증 후 MemberInfo 리턴
                    return jwtGenerator.extractClaims(token);
                }
            }

            // 토큰은 없지만 필수가 아닌 경우 체크
            JwtAuthorization annotation = parameter.getParameterAnnotation(JwtAuthorization.class);
            if (annotation != null && !annotation.required()) {
                // 필수가 아닌 경우 기본 객체 리턴
                return new MemberInfo();
            }
        }*/

        // 토큰 값이 없으면 에러
        throw new RuntimeException("권한 없음.");
    }
}