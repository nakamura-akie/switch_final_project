package org.switch2022.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.switch2022.project.datamodel.assembler.ProfileDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.profile.ProfileJPA;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.ProfileRepository;
import org.switch2022.project.repository.jpa.ProfileRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Persistence implementation of the {@link ProfileRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link ProfileRepositoryJPA} interface to store profiles.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@org.springframework.context.annotation.Profile("!test")
public class ProfileRepositoryPersistence implements ProfileRepository {

    private final ProfileRepositoryJPA profileRepositoryJPA;
    private final ProfileDomainDataAssembler profileDomainDataAssembler;

    /**
     * Constructs a new ProfileRepositoryPersistence object with the specified dependencies.
     *
     * @param profileRepositoryJPA       the ProfileRepositoryJPA used for persistence
     * @param profileDomainDataAssembler the ProfileDomainDataAssembler used for conversion between domain and data
     *                                   model
     * @throws IllegalArgumentException if either profileRepositoryJPA or profileDomainDataAssembler is null
     */
    @Autowired
    public ProfileRepositoryPersistence(ProfileRepositoryJPA profileRepositoryJPA,
                                        ProfileDomainDataAssembler profileDomainDataAssembler) {
        if (profileRepositoryJPA == null) {
            throw new IllegalArgumentException("Profile Repository JPA cannot be null");
        }
        if (profileDomainDataAssembler == null) {
            throw new IllegalArgumentException("Profile Assembler cannot be null");
        }
        this.profileRepositoryJPA = profileRepositoryJPA;
        this.profileDomainDataAssembler = profileDomainDataAssembler;
    }

    /**
     * Saves the profile in the repository.
     *
     * @param profile the profile to be saved
     * @return the saved profile
     */
    @Override
    public Profile save(Profile profile) {
        ProfileJPA profileJPA = profileDomainDataAssembler.toData(profile);
        ProfileJPA savedProfileJPA = profileRepositoryJPA.save(profileJPA);

        return profileDomainDataAssembler.toDomain(savedProfileJPA);
    }

    /**
     * Retrieves all profiles from the repository.
     *
     * @return an iterable collection of all profiles present in the repository
     */
    @Override
    public Iterable<Profile> findAll() {
        Iterable<ProfileJPA> listOfJPAProfiles = profileRepositoryJPA.findAll();

        List<Profile> listOfDomainProfiles = new ArrayList<>();

        for (ProfileJPA profileJPA : listOfJPAProfiles) {
            Profile domainProfile = profileDomainDataAssembler.toDomain(profileJPA);
            listOfDomainProfiles.add(domainProfile);
        }
        return listOfDomainProfiles;
    }

    /**
     * Retrieves a profile with a given profile name.
     *
     * @param profileName the profile name to search for
     * @return an optional containing the profile if found, an empty optional otherwise
     */
    @Override
    public Optional<Profile> findById(ProfileName profileName) {
        String profileID = profileName.getProfileNameValue();
        Optional<ProfileJPA> optionalProfileJPA = profileRepositoryJPA.findById(profileID);

        if (optionalProfileJPA.isPresent()) {
            ProfileJPA profileJPA = optionalProfileJPA.get();
            Profile domainProfile = profileDomainDataAssembler.toDomain(profileJPA);
            return Optional.of(domainProfile);
        }
        return Optional.empty();
    }

    /**
     * Checks if a profile with the given profile name exists in the repository.
     *
     * @param profileName the profile name to search for
     * @return true if a profile with the given profile name exists, false otherwise
     */
    @Override
    public boolean existsById(ProfileName profileName) {
        String profileID = profileName.getProfileNameValue();

        return profileRepositoryJPA.existsById(profileID);
    }

    /**
     * Deletes all profiles from the repository.
     */
    @Override
    public void deleteAll() {
        profileRepositoryJPA.deleteAll();

    }

    /**
     * Deletes a profile with the given profile name from the repository.
     *
     * @param profileName the name of the profile to delete
     */
    @Override
    public void deleteById(ProfileName profileName) {
        String profileID = profileName.getProfileNameValue();

        profileRepositoryJPA.deleteById(profileID);
    }

}
