package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.profile.ProfileFactory;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.ProfileRepository;
import org.switch2022.project.utils.dto.NewProfileDTO;
import org.switch2022.project.utils.assembler.OutputProfileAssembler;
import org.switch2022.project.utils.dto.OutputProfileDTO;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileFactory profileFactory;

    /**
     * Constructs a new instance of the ProfileService class with the specified parameters.
     *
     * @param profileRepository The repository used for accessing and managing profile data.
     *                          Must not be null.
     * @param profileFactory    The factory used for creating new instances of Profile objects.
     *                          Must not be null.
     * @throws IllegalArgumentException If either profileRepository or profileFactory is null.
     */
    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          ProfileFactory profileFactory) {
        if (profileRepository == null) {
            throw new IllegalArgumentException("Profile Repository cannot be null");
        }
        if (profileFactory == null) {
            throw new IllegalArgumentException("Profile Factory cannot be null");
        }
        this.profileRepository = profileRepository;
        this.profileFactory = profileFactory;
    }

    /**
     * Creates a default profile with the specified profile name value.
     *
     * @param profileNameValue The value of the profile name for the default profile.
     */
    public void createDefaultProfile(String profileNameValue) {
        ProfileName profileID = new ProfileName(profileNameValue);

        Profile defaultProfile = profileFactory.createProfile(profileID);
        profileRepository.save(defaultProfile);
    }

    /**
     * Creates a new profile based on the provided information and returns a DTO (Data Transfer Object)
     * representing the created profile.
     *
     * @param newProfileDTO The input DTO containing the information for the new profile.
     * @return The output DTO representing the created profile.
     * @throws IllegalArgumentException If a profile with the same profile name already exists.
     */
    public OutputProfileDTO createProfile(NewProfileDTO newProfileDTO) {
        ProfileName profileName = newProfileDTO.profileName;
        boolean profileExists = profileRepository.existsById(profileName);

        if (profileExists) {
            throw new IllegalArgumentException("Profile already exists");
        }

        Profile newProfile = profileFactory.createProfile(profileName);
        Profile createdProfile = profileRepository.save(newProfile);

        return OutputProfileAssembler.createOutputProfileDTO(createdProfile);
    }

    /**
     * Finds a profile based on the provided profile name and returns a DTO (Data Transfer Object)
     * representing the found profile.
     *
     * @param profileName The name of the profile to find.
     * @return The DTO representing the found profile.
     * @throws NoSuchElementException If the profile is not found.
     */
    public OutputProfileDTO findProfile(String profileName) {
        ProfileName profileID = new ProfileName(profileName);

        Optional<Profile> profile = profileRepository.findById(profileID);

        if (profile.isPresent()) {
            Profile existentProfile = profile.get();
            return OutputProfileAssembler.createOutputProfileDTO(existentProfile);
        } else {
            throw new NoSuchElementException("Profile not found");
        }
    }

}
