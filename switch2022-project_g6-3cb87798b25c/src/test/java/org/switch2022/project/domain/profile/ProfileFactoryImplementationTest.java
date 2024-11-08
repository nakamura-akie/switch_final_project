package org.switch2022.project.domain.profile;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProfileName;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProfileFactoryImplementationTest {

    @Test
    void createProfile_CreateProfile_Success() {
        ProfileFactoryImplementation factoryImplementation = new ProfileFactoryImplementation();

        ProfileName profileNameDouble = mock(ProfileName.class);

        Profile result = factoryImplementation.createProfile(profileNameDouble);

        assertEquals(profileNameDouble, result.identity());
    }
}