package io.github.costa.library.repository;

import io.github.costa.library.model.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
