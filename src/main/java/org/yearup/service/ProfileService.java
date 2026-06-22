package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile) {
        return profileRepository.save(profile);
    }

    public Optional<Profile> getById (int userId) {
        return profileRepository.findById(userId);
    }

    public Profile update(int userId, Profile profile) {
        profile.setUserId(userId);
        return profileRepository.save(profile);
    }
}
