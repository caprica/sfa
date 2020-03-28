/*
 * This file is part of Some Fine Application.
 *
 * Copyright (C) 2020
 * Caprica Software Limited (capricasoftware.co.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.caprica.sfa.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;

/**
 * Configuration for the application data repository.
 * <p>
 * This configuration replaces the <code>spring.data.mongodb.uri</code> settings in <code>application.properties</code>.
 */
@Configuration
public class DatabaseConfiguration extends AbstractMongoClientConfiguration {

    @Value("${app.database.databaseName:db}")
    private String databaseName;

    @Value("${app.database.serverHost:localhost}")
    private String serverHost;

    @Value("${app.database.serverPort:27017}")
    private int serverPort;

    @Value("${app.database.username:#{null}}")
    private String username;

    @Value("${app.database.password:#{null}}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings;
        if (!isNull(username)) {
            settings = authenticatedClient();
        } else {
            settings = anonymousClient();
        }
        return MongoClients.create(settings);
    }

    /**
     * Turn off automatic index creation.
     * <p>
     * It is recommended instead to create indices "out-of-band", i.e. in the database itself or perhaps via some external script.
     * <p>
     * Returning <code>false</code> from this method clears a Spring Data MongoDB startup warning.
     *
     * @return <code>false</code>
     */
    @Override
    public boolean autoIndexCreation() {
        return false;
    }

    /**
     * For connecting to a secured database.
     * <p>
     * <strong>By default MongoDB does <u>not</u> have any access controls enabled.</strong>
     *
     * @return client settings for an autenticated data source connection
     */
    private MongoClientSettings authenticatedClient() {
        return MongoClientSettings.builder()
            .credential(MongoCredential.createCredential(username, databaseName, password.toCharArray()))
            .applyToClusterSettings(cluster -> cluster.hosts(singletonList(new ServerAddress(serverHost, serverPort))))
            .build();
    }

    /**
     * For local developer testing against an unsecured database.
     * <p>
     * <strong>By default MongoDB does <u>not</u> have any access controls enabled.</strong>
     *
     * @return client settings for an anonymous unsecured data source connection
     */
    private MongoClientSettings anonymousClient() {
        return MongoClientSettings.builder()
            .applyToClusterSettings(cluster -> cluster.hosts(singletonList(new ServerAddress(serverHost, serverPort))))
            .build();
    }
}
