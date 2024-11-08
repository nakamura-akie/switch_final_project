package org.switch2022.project.datamodel.assembler;

import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.jpa.resource.ResourceIdJPA;
import org.switch2022.project.datamodel.jpa.resource.ResourceJPA;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.resource.ResourceFactory;
import org.switch2022.project.domain.valueobject.*;

import java.time.LocalDate;

/**
 * The ResourceDomainDataAssembler class is responsible for converting between
 * Resource and ResourceJPA objects.
 */
@Component
public class ResourceDomainDataAssembler {

    private final ResourceFactory resourceFactory;

    /**
     * Constructs a new ResourceDomainDataAssembler with the specified ResourceFactory.
     *
     * @param resourceFactory the ResourceFactory to be used for creating Resource objects.
     * @throws IllegalArgumentException if the resourceFactory is null.
     */
    public ResourceDomainDataAssembler(ResourceFactory resourceFactory) {
        if (resourceFactory == null) {
            throw new IllegalArgumentException("Resource Factory cannot be null.");
        }
        this.resourceFactory = resourceFactory;
    }

    /**
     * Converts a Resource object to its corresponding ResourceJPA representation.
     *
     * @param resource the Resource object to be converted.
     * @return the corresponding ResourceJPA object.
     */
    public ResourceJPA toData(Resource resource) {
        ResourceID resourceID = resource.identity();

        ResourceIdJPA resourceIdJpa = new ResourceIdJPA(
                resourceID.getResourceEmail().getEmailValue(),
                resourceID.getProjectCode().getProjectCodeValue(),
                resourceID.getPeriodOfAllocation().getStartDate().toString(),
                resourceID.getPeriodOfAllocation().getEndDate().toString());

        String resourceRole = resource.getRoleInProject().name();
        Integer resourceAllocation = resource.getPercentageOfAllocation().getPercentageValue();
        Double resourceCostPerHour = resource.getCostPerHour().getCostPerHourValue();

        return new ResourceJPA(resourceIdJpa, resourceRole, resourceAllocation, resourceCostPerHour);
    }

    /**
     * Converts a ResourceJPA object to its corresponding Resource representation.
     *
     * @param resourceJpa the ResourceJPA object to be converted.
     * @return the corresponding Resource object.
     */
    public Resource toDomain(ResourceJPA resourceJpa) {
        ResourceIdJPA resourceIdJpa = resourceJpa.getResourceIDJpa();

        ResourceID resourceID = new ResourceID(
                new Email(resourceIdJpa.getEmail()),
                new ProjectCode(resourceIdJpa.getProjectCode()),
                new TimePeriod(
                        LocalDate.parse(resourceIdJpa.getStartDate()),
                        LocalDate.parse(resourceIdJpa.getEndDate())));

        return resourceFactory.createResource(
                resourceID,
                ProjectRole.valueOf(resourceJpa.getProjectRole()),
                new PercentageOfAllocation(resourceJpa.getPercentageOfAllocation()),
                new CostPerHour(resourceJpa.getCostPerHour()));
    }
}
