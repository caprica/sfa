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

package uk.co.caprica.sfa.services.healthcheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simple health-check controller that responds to a GET request with application build information.
 * <p>
 * The build properties are created at project build time when using the Spring Boot Maven plugin.
 * <p>
 * The <code>spring-boot:build-info</code> Maven goal generates a file in the project target classes directory:
 * <pre>target/classes/META-INF/build-info.properties</pre>
 * <p>
 * The contents of this directory become included on the application run-time classpath and are automatically read by
 * the {@link BuildProperties} component.
 * <p>
 * This controller is intentionally <strong>not</strong> under the <code>/api</code> path. FIXME document WHY in the README
 */
@Api(
    tags = {"Health Check"}
)
@RestController
@RequestMapping("/health-check")
@RequiredArgsConstructor
@Slf4j
public class HealthCheckController {

    private final BuildProperties buildProperties;

    @ApiOperation(value = "Get the application health-check build information")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Application health-check result containing build information"),
    })

    @GetMapping
    public String healthCheck() {
        return String.format("OK: name %s, group %s, artifact %s, version %s, time %s",
            buildProperties.getName(),
            buildProperties.getGroup(),
            buildProperties.getArtifact(),
            buildProperties.getVersion(),
            buildProperties.getTime()
        );
    }
}
