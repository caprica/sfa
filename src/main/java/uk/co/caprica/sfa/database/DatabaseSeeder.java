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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uk.co.caprica.sfa.services.profile.Profile;
import uk.co.caprica.sfa.services.profile.ProfileSpecification;
import uk.co.caprica.sfa.services.user.User;
import uk.co.caprica.sfa.services.user.UserSpecification;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Support component used to initialise the database with sample data.
 * <p>
 * Sample data will only be inserted if the database is currently "empty" (more specifically if the content of the
 * <code>User</code> collection is empty).
 * <p>
 * A real application may set initial static data, or even implement a database migration solution to manage schema
 * changes.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder {

    private final UserSpecification.Service userService;

    private final ProfileSpecification.Service profileService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    public void handleContextRefreshedEvent(ContextRefreshedEvent event) {
        log.info("handleContextRefreshedEvent(event={})", event);

        if (!userService.getUsers().isEmpty()) {
            log.info("Database is not empty, skipping initialisation of seed data");
            return;
        }

        log.info("Database is empty, preparing seed data");

        userService.createUser(new User(
            null,
            "mark",
            password("toomanysecrets"),
            allAuthorities()
        ));

        userService.createUser(new User(
            null,
            "rick",
            password("nexus"),
            basicAuthorities()
        ));

        userService.createUser(new User(
            null,
            "babayaga",
            password("daisy"),
            basicAuthorities()
        ));

        userService.createUser(new User(
            null,
            "bruce",
            password("iambatman"),
            basicAuthorities()
        ));

        profileService.createProfile(new Profile(
            null,
            "mark",
            "Mark",
            "Lee",
            "The Boss",
            "I am like so totes awesome."
        ));

        profileService.createProfile(new Profile(
            null,
            "rick",
            "Rick",
            "Deckard",
            "Blade Runner",
            "Blade Runner, retired."
        ));

        profileService.createProfile(new Profile(
            null,
            "babayaga",
            "John",
            "Wick",
            "Assassin",
            "I am revenge. And somewhat totally awesome."
        ));

        profileService.createProfile(new Profile(
            null,
            "bruce",
            "Bruce",
            "Wayne",
            "Vigilante",
            "The world's greatest detective. Dark Knight. Eccentric billionaire."
        ));

        log.info("Finished seeding database");
    }

    private String password(String plainText) {
        return bCryptPasswordEncoder.encode(plainText);
    }

    private List<String> basicAuthorities() {
        return Stream.of(
            "GET_MY_USER",
            "GET_MY_PROFILE",
            "UPDATE_MY_PROFILE"
        ).collect(toList());
    }

    private List<String> allAuthorities() {
        return Stream.of(
            "GET_MY_USER",
            "GET_MY_PROFILE",
            "UPDATE_MY_PROFILE",
            "PROFILE_ADMIN"
        ).collect(toList());
    }
}
