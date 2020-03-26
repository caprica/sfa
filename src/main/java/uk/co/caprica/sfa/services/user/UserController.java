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

package uk.co.caprica.sfa.services.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Api(
    tags = {"User"}
)
@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserSpecification.Service userService;

    @ApiOperation(value = "Get the user details for the given username")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The details for the user, if the user exists"),
        @ApiResponse(code = 404, message = "If the user does not exist"),
    })
    @GetMapping
    @PreAuthorize("hasAuthority('GET_MY_USER')")
    public User getMyUserDetails(Principal principal) {
        log.debug("getMyUserDetails(principal={})", principal);
        return userService.getByUsername(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
