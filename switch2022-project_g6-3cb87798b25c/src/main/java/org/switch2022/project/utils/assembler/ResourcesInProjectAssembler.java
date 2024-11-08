package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.utils.dto.ResourceDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The ResourcesInProjectAssembler class is responsible for converting
 * Resource objects into ResourceDTO objects.
 */
public final class ResourcesInProjectAssembler {

    /**
     * Private constructor to prevent instantiation of the ResourcesInProjectAssembler class.
     */
    private ResourcesInProjectAssembler() {
    }

    /**
     * Creates a list of ResourceDTO objects from the given iterator of Resource objects.
     *
     * @param resourceIterator the iterator of Resource objects
     * @return the list of ResourceDTO objects
     */
    public static List<ResourceDTO> resourceInProjectList(Iterator<Resource> resourceIterator) {
        List<ResourceDTO> resourceDTOList = new ArrayList<>();

        while (resourceIterator.hasNext()) {
            Resource resource = resourceIterator.next();
            ResourceDTO resourceDTO = generateDTO(resource);
            resourceDTOList.add(resourceDTO);
        }
        return resourceDTOList;
    }

    /**
     * Generates a ResourceDTO object from the given Resource object.
     *
     * @param resource the Resource object
     * @return the ResourceDTO object
     */
    private static ResourceDTO generateDTO(Resource resource) {
        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.email = resource.identity().getResourceEmail().getEmailValue();
        resourceDTO.projectCode = resource.identity().getProjectCode().getProjectCodeValue();
        resourceDTO.projectRole = resource.getRoleInProject().toString();
        resourceDTO.startDate = resource.identity().getPeriodOfAllocation().getStartDate().toString();
        resourceDTO.endDate = resource.identity().getPeriodOfAllocation().getEndDate().toString();

        return resourceDTO;

    }
}
