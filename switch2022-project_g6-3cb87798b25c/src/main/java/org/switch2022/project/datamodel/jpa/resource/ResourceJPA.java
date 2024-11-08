package org.switch2022.project.datamodel.jpa.resource;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "resources")
public class ResourceJPA {

    @EmbeddedId
    private ResourceIdJPA resourceIDJpa;
    private String projectRole;
    private Integer percentageOfAllocation;
    private Double costPerHour;

    protected ResourceJPA() {
    }

    /**
     * Instantiates a new ResourceJpa.
     * Used to persist entity's of the type Resource
     *
     * @param resourceIDJpa          as an object
     * @param projectRole            as a String
     * @param percentageOfAllocation as an Integer
     * @param costPerHour            as a double
     */
    public ResourceJPA(ResourceIdJPA resourceIDJpa,
                       String projectRole,
                       Integer percentageOfAllocation,
                       Double costPerHour) {
        this.resourceIDJpa = resourceIDJpa;
        this.projectRole = projectRole;
        this.percentageOfAllocation = percentageOfAllocation;
        this.costPerHour = costPerHour;
    }

    /**
     * Gets the ID of the JPA Entity
     *
     * @return ResourceIDJpa
     */
    public ResourceIdJPA getResourceIDJpa() {
        return resourceIDJpa;
    }

    /**
     * Gets project role of JPA Entity
     *
     * @return ProjectRole as a String
     */
    public String getProjectRole() {
        return projectRole;
    }

    /**
     * Gets percentage of allocation of JPA Entity
     *
     * @return percentage of allocation as an Integer
     */
    public Integer getPercentageOfAllocation() {
        return percentageOfAllocation;
    }

    /**
     * Gets cost per hour of JPA Entity
     *
     * @return cost per hour as a double
     */
    public Double getCostPerHour() {
        return costPerHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResourceJPA that = (ResourceJPA) o;
        return Objects.equals(resourceIDJpa, that.resourceIDJpa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceIDJpa);
    }
}