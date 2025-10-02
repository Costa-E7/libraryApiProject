package io.github.costa.library.model.mongodb;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "request_logs")
@Data
public class RequestLog {

    @Id
    private String id;
    private String method;
    private String uri;
    private Object params;
    private Object response;
    private LocalDateTime timestamp;
}
