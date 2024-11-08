package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.utils.dto.OutputResourceDTO;

/**
 * Assembler class for converting a Resource object to an OutputResourceDTO object.
 */
public final class OutputResourceAssembler {

    /**
     * Private constructor to prevent instantiation of the OutputResourceAssembler class.
     */
    private OutputResourceAssembler() {
    }

    /**
     * Generates an OutputResourceDTO object from a Resource object.
     *
     * @param createdResource The Resource object to convert.
     * @return The generated OutputResourceDTO.
     */
    public static OutputResourceDTO generateOutputResourceDTO(Resource
    createdResource) {

        String email = createdResource.identity().getResourceEmail().getEmailValue();
        String projectCode = createdResource.identity().getProjectCode().getProjectCodeValue();
        String startDate = createdResource.identity().getPeriodOfAllocation().getStartDate().toString();
        String endDate = createdResource.identity().getPeriodOfAllocation().getEndDate().toString();
        String projectRole = createdResource.getRoleInProject().toString();
        Integer percentageOfAllocation = createdResource.getPercentageOfAllocation().getPercentageValue();
        Double costPerHour = createdResource.getCostPerHour().getCostPerHourValue();

        return new OutputResourceDTO(
                email,
                projectCode,
                startDate,
                endDate,
                projectRole,
                percentageOfAllocation,
                costPerHour
        );
    }
}
