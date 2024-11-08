package org.switch2022.project.repository;

import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.ProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A fake implementation of the {@link ProfileRepository} interface that uses an in-memory list to store
 * {@link Profile} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@org.springframework.context.annotation.Profile("test")
public class ProfileRepositoryFake implements ProfileRepository {

    private static final List<Profile> profileList = new ArrayList<>();

    /**
     * Saves the profile in the repository.
     *
     * @param profile the profile to be saved
     * @return the saved profile
     * @throws IllegalArgumentException if the profile already exists in the repository
     */
    @Override
    public Profile save(Profile profile) {
        if (profileList.contains(profile)) {
            throw new IllegalArgumentException("Profile already exists");
        }
        profileList.add(profile);
        return profile;
    }

    /**
     * Retrieves all profiles from the repository.
     *
     * @return an iterable collection of all profiles
     */
    @Override
    public Iterable<Profile> findAll() {
        return new ArrayList<>(profileList);
    }

    /**
     * Retrieves a profile by its profile name.
     *
     * @param profileName the profile name to search for
     * @return an optional containing the profile if found, an empty optional otherwise
     */
    @Override
    public Optional<Profile> findById(ProfileName profileName) {
        for (Profile profile : profileList) {
            if (profile.identity().equals(profileName)) {
                return Optional.of(profile);
            }
        }
        return Optional.empty();
    }

    /**
     * Confirms if a profile with the given profile name exists in the repository.
     *
     * @param profileName the profile name to search for
     * @return true if a profile with the given profile name exists, false otherwise
     */
    @Override
    public boolean existsById(ProfileName profileName) {
        for (Profile profile : profileList) {
            if (profile.identity().equals(profileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all profiles from the repository.
     */
    @Override
    public void deleteAll() {
        profileList.clear();
    }

    /**
     * Deletes a profile with a given profile name.
     *
     * @param profileName the name of the profile to delete
     */
    @Override
    public void deleteById(ProfileName profileName) {
        profileList.removeIf(profile -> profile.identity().equals(profileName));
    }

}
