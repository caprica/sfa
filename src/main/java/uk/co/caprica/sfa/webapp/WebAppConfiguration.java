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

package uk.co.caprica.sfa.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.co.caprica.sfa.security.SecurityConfig;

/**
 * Additional web-application configuration.
 * <p>
 * This is used to setup the (non-API) server-side request paths to be routed to the client for client-side routing the single-page application.
 */
@Configuration
public class WebAppConfiguration implements WebMvcConfigurer  {

    private static final String APPLICATION_VIEW_NAME = "forward:/index.html";

    /**
     * Ensure client-side paths redirect to index.html because client handles routing. NOTE: Do NOT use @EnableWebMvc or it will break this.
     * <p>
     * Do <strong>not</strong> use <code>@EnableWebMvc</code>.
     * <p>
     * The <code>api</code> string used in the regular expression must match that used by the configure() method in {@link SecurityConfig#}, and the controller
     * end-point request mappings.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Map "/" (effectively "no-path") to the static HTML page for the single-page application
        registry
            .addViewController("/")
            .setViewName(APPLICATION_VIEW_NAME);

        // FIXME these expressions are sufficient for simple cases, but will fail if the URL path segments contain other characters like "+"

        // For paths one level deep, e.g. "/home", "/login"
        registry
            .addViewController("/{x:[\\w\\-]+}")
            .setViewName(APPLICATION_VIEW_NAME);

        // For paths multiple levels deep, excluding those that start "/api", e.g. "/my-profile/edit" (note that the named groups are required, here "p" and "q"
        // are used, even though nothing will ever reference them)
        registry
            .addViewController("/{p:^(?!api$).*$}/**/{q:[\\w\\-]+}")
            .setViewName(APPLICATION_VIEW_NAME);
    }
}
