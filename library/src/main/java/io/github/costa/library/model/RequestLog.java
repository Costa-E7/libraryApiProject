package io.github.costa.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;        // método do controller
    private String uri;           // opcional, se você pegar URI do HttpServletRequest
    private String params;        // parâmetros
    private String response;      // resposta da API
    private LocalDateTime timestamp;
}