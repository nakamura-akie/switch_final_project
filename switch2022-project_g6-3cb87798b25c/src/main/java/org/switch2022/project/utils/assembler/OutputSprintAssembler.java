package org.switch2022.project.utils.assembler;

import org.springframework.stereotype.Component;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.utils.dto.OutputSprintDTO;

import java.util.stream.Collectors;

/**
 * The OutputSprintAssembler class is responsible for converting Sprint objects
 * into OutputSprintDTOs.
 */
@Component
public final class OutputSprintAssembler {

    /**
     * Private constructor to prevent instantiation of the OutputSprintAssembler class.
     */
    private OutputSprintAssembler() {}

    /**
     * Creates an OutputSprintDTO object from a given Sprint object.
     *
     * @param sprint the sprint object
     * @return the generated OutputSprintDTO
     */
    public static OutputSprintDTO generateDTO(Sprint sprint) {
        OutputSprintDTO sprintBacklogDTO = new OutputSprintDTO();
        sprintBacklogDTO.projectCode = sprint.getProjectCode().getProjectCodeValue();
        sprintBacklogDTO.sprintCode = sprint.getSprintCode().getSprintCodeValue();
        sprintBacklogDTO.startDate = sprint.getStartDate().toString();
        sprintBacklogDTO.endDate = sprint.getEndDate().toString();
        sprintBacklogDTO.sprintDuration = sprint.getSprintDuration().getSprintDurationValue();
        sprintBacklogDTO.sprintBacklog = sprint.getIDsOfUserStoriesInSprintBacklog().stream()
                .map(UserStoryID::getUserStoryCode)
                .map(UserStoryCode::getUserStoryCodeValue)
                .collect(Collectors.toList());
        sprintBacklogDTO.sprintStatus = sprint.getSprintStatus().toString();

        return sprintBacklogDTO;
    }

}
