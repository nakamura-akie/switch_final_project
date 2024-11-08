package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.utils.assembler.OutputProfileAssembler;
import org.switch2022.project.utils.dto.OutputProfileDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutputProfileAssemblerTest {

    private static Profile profileDouble;
    private static ProfileName profileName;

    @BeforeAll
    static void init() {
        profileDouble = mock(Profile.class);

        profileName = new ProfileName("profile's identity");

        when(profileDouble.identity()).thenReturn(profileName);
    }

    @AfterAll
    static void tearDown() {
        profileDouble = null;
        profileName = null;
    }

    @Test
    void createOutputProfileDTO_SuccessfulCreation_True() {
        OutputProfileDTO expected = new OutputProfileDTO();
        expected.profileName = "profile's identity";

        OutputProfileDTO result = OutputProfileAssembler.createOutputProfileDTO(profileDouble);

        assertEquals(expected, result);
    }

}