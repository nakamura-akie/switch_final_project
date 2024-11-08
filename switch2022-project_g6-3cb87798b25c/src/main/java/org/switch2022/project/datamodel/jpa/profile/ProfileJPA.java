package org.switch2022.project.datamodel.jpa.profile;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profiles")
public class ProfileJPA {

    @Id
    private String profileName;

    /**
     * Protected constructor for the ProfileJPA class.
     * Used by the JPA provider for object instantiation during deserialization.
     * Should not be called directly.
     */
    protected ProfileJPA() {}

    /**
     * Constructs a new instance of the ProfileJPA class.
     *
     * @param profileName the profile name
     */
    public ProfileJPA(String profileName) {
        this.profileName = profileName;
    }

    /**
     * Retrieves the profile name.
     *
     * @return the profile name
     */
    public String getProfileName() {
        return profileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileJPA that = (ProfileJPA) o;
        return Objects.equals(profileName, that.profileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileName);
    }

}