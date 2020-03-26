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

package uk.co.caprica.sfa.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.co.caprica.sfa.services.user.User;
import uk.co.caprica.sfa.services.user.UserService;
import uk.co.caprica.sfa.services.user.UserSpecification;

import static java.util.stream.Collectors.toList;

/**
 * Spring Security component responsible for loading standard {@link UserDetails} for a particular username.
 * <p>
 * This component provides a bridge between Spring Security and the application-specific implementation (in this case
 * an implementation of {@link UserSpecification.Service}).
 * <p>
 * This bridging class is factored out from the "security" package since this class is application-specific and that
 * package is otherwise generic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityUserDetailsIntegrationService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername(username={})", username);
        return userService.getByUsername(username).map(this::convertUser)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private UserDetails convertUser(User user) {
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(toList())
        );
    }
}
