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

package uk.co.caprica.sfa.security;

import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Spring UserSpecification component that authorises a request by checking a valid JSON Web Token is present on the request.
 * <p>
 * Since this is a servlet filter, and not a Spring-managed component, the configured values are passed in via the
 * constructor rather than @{@link Value} annotations.
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    private final String jwtSecret;

    private final String tokenPrefix;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService, String jwtSecret, String tokenPrefix) {
        super(authManager);
        this.userDetailsService = userDetailsService;
        this.jwtSecret = jwtSecret;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.debug("doFilterInternal()");
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("header={}", header);
        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        log.debug("authentication={}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        log.debug("getAuthentication()");
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String username = JWT
                .require(HMAC512(jwtSecret.getBytes()))
                .build()
                .verify(token.replace(tokenPrefix, ""))
                .getSubject();
            log.debug("username={}", username);
            if (username != null) {
                return convertUser(userDetailsService.loadUserByUsername(username));
            }
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken convertUser(UserDetails userDetails) {
        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
            );
        } else {
            return null;
        }
    }
}