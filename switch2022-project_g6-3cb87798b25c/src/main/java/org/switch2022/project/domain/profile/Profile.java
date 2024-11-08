package org.switch2022.project.domain.profile;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.ProfileName;

import java.util.Objects;

/**
 * The Profile class represents a user profile in the domain model.
 * It implements the AggregateRoot interface with the identity of the user profile being the profile name.
 */
public class Profile implements AggregateRoot<ProfileName> {

    private final ProfileName profileName;

    /**
     * Instantiates a new Profile.
     *
     * @param profileName the profile name
     * @throws IllegalArgumentException if the profileName parameter is null
     */
    protected Profile(ProfileName profileName) {
        if (profileName == null) {
            throw new IllegalArgumentException("Profile name cannot be null");
        }
        this.profileName = profileName;
    }

    /**
     * Returns the identity of the Profile.
     *
     * @return the profile name
     */
    @Override
    public ProfileName identity() {
        return this.profileName;
    }

    /**
     * Checks if this Profile is the same as the specified object.
     * Two Profile objects are considered the same if their profileName is equal.
     *
     * @param object the object to compare
     * @return true if the Profile is the same as the specified object, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof Profile) {
            Profile that = (Profile) object;

            return this.profileName.equals(that.profileName);
        }
        return false;
    }

    /**
     * Checks if this profile is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this profile, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile that = (Profile) o;
        return profileName.equals(that.profileName);
    }

    /**
     * Generates the hash code value for the profile.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(profileName);
    }

}
