package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.utils.dto.OutputProfileDTO;

/**
 * The OutputProfileAssembler class is responsible for converting profiles into OutputProfileDTOs.
 */
public final class OutputProfileAssembler {

    /**
     * Private constructor to prevent instantiation of the OutputProfileAssembler class.
     */
    private OutputProfileAssembler() {}

    /**
     * Creates an OutputProfileDTO object from a Profile object.
     *
     * @param profile the Profile object
     * @return the created OutputProfileDTO object
     */
    public static OutputProfileDTO createOutputProfileDTO(Profile profile) {
        OutputProfileDTO outputProfileDTO = new OutputProfileDTO();
        outputProfileDTO.profileName = profile.identity().getProfileNameValue();

        return outputProfileDTO;
    }

}
