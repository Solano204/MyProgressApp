package com.example.myprogress.app.ConfigurationTest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackages = "com.example.myprogress.app.Repositories") // This will enable the repositories to use with the MongoTemplate 
@Configuration
@Profile("test") // This will enable the test profile (TEST)
public class TestMongoConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017/testdb"); // Here we will connect to the testdb
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "testdb"); // Here we will use the testdb
    }
}
