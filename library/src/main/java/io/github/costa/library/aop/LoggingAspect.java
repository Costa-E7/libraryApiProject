package io.github.costa.library.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.costa.library.model.mongodb.RequestLog;
import io.github.costa.library.repository.mongodb.RequestLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jakarta.inject.Provider;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final RequestLogRepository logRepository;
    private final ObjectMapper objectMapper;
    private final Provider<HttpServletRequest> requestProvider;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logEndpoints(ProceedingJoinPoint joinPoint) throws Throwable {

        LocalDateTime now = LocalDateTime.now();
        String methodName = joinPoint.getSignature().toShortString();
        HttpServletRequest request = requestProvider.get();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String fullUri = uri + (queryString != null ? "?" + queryString : "");
        String params = objectMapper.writeValueAsString(joinPoint.getArgs());
        Object result = joinPoint.proceed();
        String response = objectMapper.writeValueAsString(result);

        RequestLog log = new RequestLog();
        log.setMethod(methodName);
        log.setUri(fullUri);
        Object[] paramsArgs = joinPoint.getArgs();
        if (paramsArgs.length == 1) {
            log.setParams(paramsArgs[0]);
        } else {
            log.setParams(paramsArgs);
        }
        Object[] responseArgs = joinPoint.getArgs();
        if (responseArgs.length == 1) {
            log.setParams(responseArgs[0]);
        } else {
            log.setParams(responseArgs);
        }
        log.setResponse(responseArgs);
        log.setTimestamp(now);

        logRepository.save(log);

        return result;
    }
}
