package io.github.costa.library.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoInitRunner implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    public MongoInitRunner(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!mongoTemplate.collectionExists("request_logs")) {
            mongoTemplate.createCollection("request_logs");
            System.out.println("Coleção 'request_logs' criada!");
        } else {
            System.out.println("Coleção 'request_logs' já existe.");
        }
    }
}
