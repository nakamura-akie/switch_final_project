package org.switch2022.project.utils.mapper;

import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.resource.ResourceFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.dto.NewResourceDTO;

import java.time.LocalDate;

/**
 * Mapper class for creating a Resource domain object from a NewResourceDTO object.
 */
public final class ResourceMapper {

    /**
     * Private constructor to prevent instantiation of the Resource Mapper class.
     */
    private ResourceMapper() {
    }

    /**
     * Creates a Resource domain object from a NewResourceDTO object.
     *
     * @param newResourceDTO  The NewResourceDTO object containing the resource data.
     * @param resourceFactory The ResourceFactory used for creating the resource.
     * @return The created Resource domain object.
     * @throws IllegalArgumentException if any of the resource attributes are null or blank.
     */
    public static Resource createDomainObject(NewResourceDTO newResourceDTO,
                                              ResourceFactory resourceFactory) {

        Email email = newResourceDTO.getEmail();
        ProjectCode projectCode = newResourceDTO.getProjectCode();
        LocalDate startDate = LocalDate.parse(newResourceDTO.getStartDate().toString());
        LocalDate endDate = LocalDate.parse(newResourceDTO.getEndDate().toString());
        TimePeriod periodOfAllocation = new TimePeriod(startDate, endDate);
        PercentageOfAllocation percentageOfAllocation = newResourceDTO.getPercentageOfAllocation();
        CostPerHour costPerHour = newResourceDTO.getCostPerHour();
        ResourceID resourceID = new ResourceID(email, projectCode, periodOfAllocation);
        ProjectRole roleInProject = newResourceDTO.getProjectRole();

        return resourceFactory.createResource(
                resourceID,
                roleInProject,
                percentageOfAllocation,
                costPerHour
        );
    }
}
