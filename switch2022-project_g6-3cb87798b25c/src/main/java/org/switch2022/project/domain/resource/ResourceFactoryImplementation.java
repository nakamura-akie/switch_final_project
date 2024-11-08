package org.switch2022.project.domain.resource;

import org.springframework.stereotype.Service;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.CostPerHour;

/**
 * The ResourceFactoryImplementation class is an implementation of the ResourceFactory interface.
 */
@Service
public class ResourceFactoryImplementation implements ResourceFactory {

    /**
     * Creates a new Resource instance with the provided parameters.
     *
     * @param resourceID             the resource ID
     * @param roleInProject          the role of the resource in the project
     * @param percentageOfAllocation the percentage of allocation for the resource
     * @param costPerHour            the cost per hour for the resource
     * @return the created Resource instance
     */
    @Override
    public Resource createResource(ResourceID resourceID,
                                   ProjectRole roleInProject,
                                   PercentageOfAllocation percentageOfAllocation,
                                   CostPerHour costPerHour) {

        return new Resource(resourceID, roleInProject,percentageOfAllocation,costPerHour);
    }
}
