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

package uk.co.caprica.sfa.services.profile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Api(
    tags = {"My Profile"}
)
@RestController
@RequestMapping("/api/my-profile")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MyProfileController {

    private final ProfileSpecification.Service profileService;

    @ApiOperation(value = "Get the profile details for the current user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The profile for the user, if it exists"),
        @ApiResponse(code = 404, message = "If the profile does not exist"),
    })
    @GetMapping
    @PreAuthorize("hasAuthority('GET_MY_PROFILE')")
    public Profile getMyProfile(Principal principal) {
        log.debug("getMyProfile(principal={})", principal);
        return profileService.getProfile(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Update the profile for the current user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The updated profile for the user"),
        @ApiResponse(code = 404, message = "If the profile does not exist")
    })
    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_MY_PROFILE') && #updatedProfile.username == authentication.principal")
    public Profile updateMyProfile(Principal principal, @Valid @RequestBody Profile updatedProfile) {
        log.debug("updateMyProfile(principal={}, updatedProfile={})", principal, updatedProfile);
        Profile existingProfile = profileService.getProfile(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.debug("existingProfile={}", existingProfile);
        updatedProfile.setId(existingProfile.getId());
        return profileService.updateProfile(updatedProfile);
    }
}
