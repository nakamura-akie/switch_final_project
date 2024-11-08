package org.switch2022.project.domain.profile;

import org.springframework.stereotype.Service;
import org.switch2022.project.domain.valueobject.ProfileName;

/**
 * The ProfileFactoryImplementation class is an implementation of the ProfileFactory interface.
 */
@Service
public class ProfileFactoryImplementation implements ProfileFactory {

    /**
     * Creates a new Profile instance.
     *
     * @param profileName the profile name
     * @return the created Profile instance
     */
    @Override
    public Profile createProfile(ProfileName profileName) {
        return new Profile(profileName);
    }

}
