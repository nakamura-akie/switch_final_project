package org.switch2022.project.datamodel.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.jpa.profile.ProfileJPA;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.profile.ProfileFactory;
import org.switch2022.project.domain.valueobject.ProfileName;

@Component
public class ProfileDomainDataAssembler {

    private final ProfileFactory profileFactory;

    /**
     * Constructs a new instance of the ProfileDomainDataAssembler class.
     *
     * @param profileFactory the profile factory to be injected
     * @throws IllegalArgumentException if the profileFactory parameter is null
     */
    @Autowired
    public ProfileDomainDataAssembler(ProfileFactory profileFactory) {
        if (profileFactory == null) {
            throw new IllegalArgumentException("Profile Factory cannot be null");
        }
        this.profileFactory = profileFactory;
    }

    /**
     * Converts a Profile object from the domain model to a ProfileJPA object from the data model.
     *
     * @param profile the Profile object to be converted
     * @return the converted ProfileJPA object
     */
    public ProfileJPA toData(Profile profile) {
        ProfileName profileID = profile.identity();
        String profileName = profileID.getProfileNameValue();

        return new ProfileJPA(profileName);
    }

    /**
     * Converts a ProfileJPA object from the data model to a Profile object from the domain model.
     *
     * @param profileJPA the ProfileJPA object to be converted
     * @return the converted Profile object
     */
    public Profile toDomain(ProfileJPA profileJPA) {
        String profileName = profileJPA.getProfileName();
        ProfileName profileID = new ProfileName(profileName);

        return profileFactory.createProfile(profileID);
    }

}