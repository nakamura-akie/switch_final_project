package org.switch2022.project.domain.resource;

import org.switch2022.project.domain.valueobject.ProjectRole;
import org.switch2022.project.domain.valueobject.CostPerHour;
import org.switch2022.project.domain.valueobject.PercentageOfAllocation;
import org.switch2022.project.domain.valueobject.ResourceID;

/**
 * The ResourceFactory interface defines methods for creating Resource objects.
 */
public interface ResourceFactory {

    /**
     * Creates a new Resource instance with the provided parameters.
     *
     * @param resourceID             the resource ID
     * @param roleInProject          the role of the resource in the project
     * @param percentageOfAllocation the percentage of allocation for the resource
     * @param costPerHour            the cost per hour for the resource
     * @return the created Resource instance
     */
    Resource createResource(ResourceID resourceID,
                            ProjectRole roleInProject,
                            PercentageOfAllocation percentageOfAllocation,
                            CostPerHour costPerHour);
}

