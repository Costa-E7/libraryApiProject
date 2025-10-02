package io.github.costa.library.repository.mongodb;

import io.github.costa.library.model.mongodb.RequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends MongoRepository<RequestLog, String> {
}
