package org.switch2022.project.domain.profile;

import org.switch2022.project.domain.valueobject.ProfileName;

/**
 * The ProfileFactory interface defines methods for creating Profile objects.
 */
public interface ProfileFactory {

    /**
     * Creates a new Profile instance.
     *
     * @param profileName the profile name
     * @return the created Profile instance
     */
    Profile createProfile(ProfileName profileName);

}
