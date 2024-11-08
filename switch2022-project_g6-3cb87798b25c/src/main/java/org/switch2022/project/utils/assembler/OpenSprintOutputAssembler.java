package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.utils.dto.OpenSprintOutputDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The OpenSprintOutputAssembler class is responsible for converting sprints into OpenSprintOutputDTOs.
 */
public final class OpenSprintOutputAssembler {

    /**
     * Private constructor to prevent instantiation of the OpenSprintOutputAssembler class.
     */
    private OpenSprintOutputAssembler() {}

    /**
     * Generates an OpenSprintOutputDTO object from the given Sprint object.
     *
     * @param sprint the Sprint object
     * @return the OpenSprintOutputDTO object
     */
    public static OpenSprintOutputDTO generateDTO(Sprint sprint) {
        OpenSprintOutputDTO openSprintOutputDTO = new OpenSprintOutputDTO();
        openSprintOutputDTO.sprintCode = sprint.getSprintCode().getSprintCodeValue();
        openSprintOutputDTO.startDate = sprint.getStartDate().toString();
        openSprintOutputDTO.endDate = sprint.getEndDate().toString();
        openSprintOutputDTO.sprintStatus = sprint.getSprintStatus().toString();

        return openSprintOutputDTO;
    }

    /**
     * Creates a list of OpenSprintOutputDTO objects from the given iterator of Sprint objects.
     *
     * @param sprintIterator the iterator of Sprint objects
     * @return the list of OpenSprintOutputDTO objects
     */
    public static List<OpenSprintOutputDTO> createSprintList(Iterator<Sprint> sprintIterator) {
        List<OpenSprintOutputDTO> sprintDTOList = new ArrayList<>();

        while (sprintIterator.hasNext()) {
            Sprint currentSprint = sprintIterator.next();

            OpenSprintOutputDTO sprintDTO = generateDTO(currentSprint);

            sprintDTOList.add(sprintDTO);
        }

        return sprintDTOList;
    }
}