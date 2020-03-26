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

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import javax.annotation.security.RolesAllowed;

/**
 * Spring configuration component to enable method-level security authorisation annotations.
 * <p>
 * For the "pre/post" configuration:
 * <ul>
 *     <li>@{@link PreAuthorize}</li>
 *     <li>@{@link PostAuthorize}</li>
 * </ul>
 * For the "secured" configuration:
 * <ul>
 *     <li>@{@link Secured}</li>
 * </ul>
 * For the JSR 250 configuration:
 * <ul>
 *     <li>@{@link RolesAllowed}</li>
 * </ul>
 * Note that when using method-level annotations in conjunction with the expression language, for a given role/authority named e.g. "ADMIN":
 * <ul>
 *     <li>Using "hasAuthority" requires the bare authority name, i.e. "ADMIN";</li>
 *     <li>Using "hasRole" requires that the name contain the prefix "ROLE_", i.e. "ROLE_ADMIN"</li>
 * </ul>
 */
@Configuration
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
