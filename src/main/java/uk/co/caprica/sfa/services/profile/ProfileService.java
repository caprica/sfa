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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileService implements ProfileSpecification.Service {

    private final ProfileRepository profileRepository;

    @Override
    public Optional<Profile> getByUsername(String username) {
        log.debug("getByUsername(username={})", username);
        return profileRepository.findByUsername(username);
    }

    @Override
    public Optional<Profile> getProfile(String username) {
        log.debug("getProfile(profileId={})", username);
        return profileRepository.findByUsername(username);
    }

    @Override
    public Profile createProfile(Profile profile) {
        log.debug("createProfile(profile={})", profile);
        return profileRepository.insert(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        log.debug("updateProfile(profile={})", profile);
        return profileRepository.save(profile);
    }

    @Override
    public void deleteProfile(String username) {
        log.debug("deleteProfile(profileId={})", username);
        profileRepository.deleteByUsername(username);
    }

    @Override
    public List<Profile> getProfiles() {
        log.debug("getProfiles()");
        return profileRepository.findAll();
    }
}
