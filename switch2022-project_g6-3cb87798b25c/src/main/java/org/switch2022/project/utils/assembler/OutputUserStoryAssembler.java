package org.switch2022.project.utils.assembler;

import org.springframework.stereotype.Component;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;

/**
 * The OutputUserStoryAssembler class is responsible for converting User Story objects
 * into OutputUserStoryDTOs.
 */
@Component
public final class OutputUserStoryAssembler {

    /**
     * Private constructor to prevent instantiation of the OutputUserStoryAssembler class.
     */
    private OutputUserStoryAssembler() {
    }

    /**
     * Converts a User Story to an OutputUserStoryDTO
     *
     * @param userStory the user story
     * @return the Output UserStory DTO of the provided
     * UserStory
     */
    public static OutputUserStoryDTO createOutputUserStoryDTO(UserStory userStory) {
        OutputUserStoryDTO outputUserStoryDTO = new OutputUserStoryDTO();
        outputUserStoryDTO.projectCode = userStory.getProjectCode().getProjectCodeValue();
        outputUserStoryDTO.userStoryCode = userStory.getUserStoryCode().getUserStoryCodeValue();
        outputUserStoryDTO.description = userStory.getDescription().getDescriptionValue();
        outputUserStoryDTO.status = userStory.getUserStoryStatus().toString();
        outputUserStoryDTO.userStoryEffort = userStory.getEffort().getValue();

        return outputUserStoryDTO;
    }
}
