package io.github.costa.library.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.costa.library.model.RequestLog;
import io.github.costa.library.repository.RequestLogRepository;
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
    private final Provider<HttpServletRequest> requestProvider; // tipado corretamente

        @Around("@within(org.springframework.web.bind.annotation.RestController)")
//        @Around("execution(* io.github.costa.library.controller..*(..))")
        public Object logEndpoints(ProceedingJoinPoint joinPoint) throws Throwable {

            LocalDateTime now = LocalDateTime.now();
            String methodName = joinPoint.getSignature().toShortString();

            // Obtém a requisição atual
            HttpServletRequest request = requestProvider.get();

            // Captura path, query e headers
            String uri = request.getRequestURI();
            String httpMethod = request.getMethod();
            String queryString = request.getQueryString();
            String fullUri = uri + (queryString != null ? "?" + queryString : "");

            // Captura argumentos do método
            String params = objectMapper.writeValueAsString(joinPoint.getArgs());

            // Executa o método
            Object result = joinPoint.proceed();

            // Converte retorno para JSON
            String response = objectMapper.writeValueAsString(result);

            // Cria log
            RequestLog log = new RequestLog();
            log.setMethod(methodName);
            log.setUri(fullUri);                // inclui query string
            log.setParams(params);
            log.setResponse(response);
            log.setTimestamp(now);

            // Salva no banco
            logRepository.save(log);

            return result;
        }

}
