package org.kraaknet.authenticarebankapi.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@NonNullApi
@Configuration
@EnableMongoRepositories(basePackages = "org.kraaknet.authenticarebankapi.repository.database.model")
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        var connectionString = new ConnectionString("mongodb://localhost:27017/authenticarebank");
        var mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(MongoClient client) {
        return new MongoTemplate(client, "test");
    }

}