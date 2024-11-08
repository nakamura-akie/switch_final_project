package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.utils.dto.CreatedSprintDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The CreatedSprintAssembler class is responsible for converting Sprint objects into CreatedSprintDTO objects.
 */
public final class CreatedSprintAssembler {

    /**
     * Private constructor to prevent instantiation of the CreatedSprintAssembler class.
     */
    private CreatedSprintAssembler() {}

    /**
     * Creates a list of CreatedSprintDTO objects from the given iterator of Sprints.
     *
     * @param sprintIterator the iterator of Sprints
     * @return the list of CreatedSprintDTOs
     */
    public static List<CreatedSprintDTO> createSprintList(Iterator<Sprint> sprintIterator) {
        List<CreatedSprintDTO> sprintDTOList = new ArrayList<>();

        while (sprintIterator.hasNext()) {
            Sprint currentSprint = sprintIterator.next();
            CreatedSprintDTO sprintDTO = generateDTO(currentSprint);
            sprintDTOList.add(sprintDTO);
        }
        return sprintDTOList;
    }

    /**
     * Generates a CreatedSprintDTO object from the given Sprint.
     *
     * @param sprint the Sprint to convert to a CreatedSprintDTO
     * @return the CreatedSprintDTO object
     */
    public static CreatedSprintDTO generateDTO(Sprint sprint) {
        CreatedSprintDTO createdSprintDTO = new CreatedSprintDTO();
        createdSprintDTO.sprintID = new SprintID(sprint.getProjectCode(), sprint.getSprintCode());
        createdSprintDTO.sprintDuration = sprint.getSprintDuration();
        createdSprintDTO.startDate = sprint.getStartDate();
        createdSprintDTO.endDate = sprint.getEndDate();
        createdSprintDTO.sprintStatus = sprint.getSprintStatus();

        return createdSprintDTO;
    }
}
