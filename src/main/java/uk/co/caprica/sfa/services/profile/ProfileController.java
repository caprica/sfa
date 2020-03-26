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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Api(
    tags = {"Profile"}
)
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProfileController {

    private final ProfileSpecification.Service profileService;

    @ApiOperation(value = "Get the profile details for the given username")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The profile for the user, if it exists"),
        @ApiResponse(code = 404, message = "If the profile does not exist"),
    })
    @GetMapping(path = "{username}")
    @PreAuthorize("hasAnyAuthority('GET_PROFILE', 'PROFILE_ADMIN')")
    public Profile getProfile(@PathVariable String username) {
        log.debug("getProfile(username={})", username);
        return profileService.getProfile(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create a new profile")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The profile for the user, if it exists")
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_PROFILE', 'PROFILE_ADMIN')")
    public Profile createProfile(@Valid @RequestBody Profile profile) {
        log.debug("createProfile(profile={})", profile);
        return profileService.createProfile(profile);
    }

    @ApiOperation(value = "Delete the profile for the give username, if it exists")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "If the profile was deleted, or was already non-existent")
    })
    @DeleteMapping(path = "{username}")
    @PreAuthorize("hasAnyAuthority('DELETE_PROFILE', 'PROFILE_ADMIN')")
    public void deleteProfile(@PathVariable String username) {
        log.debug("deleteProfile(username={})", username);
        profileService.deleteProfile(username);
    }

    @ApiOperation(value = "Update the profile for the given username")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The updated profile for the user"),
        @ApiResponse(code = 404, message = "If the profile does not exist")
    })
    @PutMapping(path = "{username}")
    @PreAuthorize("hasAnyAuthority('UPDATE_PROFILE', 'PROFILE_ADMIN') && #updatedProfile.username == #username")
    public Profile updateProfile(@PathVariable String username, @Valid @RequestBody Profile updatedProfile) {
        log.debug("updateProfile(username={}, updatedProfile={})", username, updatedProfile);
        Profile existingProfile = profileService.getProfile(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.debug("existingProfile={}", existingProfile);
        updatedProfile.setId(existingProfile.getId());
        return profileService.updateProfile(updatedProfile);
    }

    @ApiOperation(value = "Get the collection of all profiles")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Collection of zero or more profile")
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('LIST_PROFILES', 'PROFILE_ADMIN')")
    public List<Profile> getProfiles() {
        log.debug("getProfiles()");
        return profileService.getProfiles();
    }
}
