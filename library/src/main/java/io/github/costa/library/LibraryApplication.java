package io.github.costa.library;

import io.github.costa.library.model.mongodb.RequestLog;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing

// Ativa a serialização estável de páginas (Page<T>) para JSON,
// evitando o WARN sobre PageImpl e garantindo compatibilidade entre versões do Spring Data(usado para paginação).
@EnableJpaRepositories(basePackages = "io.github.costa.library.repository.jpa")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableMongoRepositories(
		basePackages = "io.github.costa.library.repository.mongodb"
)
public abstract class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
