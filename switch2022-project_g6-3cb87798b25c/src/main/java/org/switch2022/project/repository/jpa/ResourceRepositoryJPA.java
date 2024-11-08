package org.switch2022.project.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.switch2022.project.datamodel.jpa.resource.ResourceIdJPA;
import org.switch2022.project.datamodel.jpa.resource.ResourceJPA;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing ResourceJPA entities in the database.
 */
public interface ResourceRepositoryJPA extends CrudRepository<ResourceJPA, ResourceIdJPA> {

    /**
     * Retrieves all resources with the given project code and project role.
     *
     * @param projectCode the project code to search for
     * @param projectRole the project role to search for
     * @return an iterable collection of matching resources
     */
    Iterable<ResourceJPA> findByResourceIDJpaProjectCodeAndProjectRole(String projectCode, String projectRole);

    /**
     * Retrieves all resources with the given email.
     *
     * @param email the email to search for
     * @return an iterable collection of matching resources
     */
    Iterable<ResourceJPA> findByResourceIDJpaEmail(String email);

    /**
     * Retrieves all resources with the given project code.
     *
     * @param projectCode the project code to search for
     * @return an iterable collection of matching resources
     */
    Iterable<ResourceJPA> findByResourceIDJpaProjectCode(String projectCode);

    /**
     * Retrieves the resource with the given resource ID.
     *
     * @param resourceIdJPA the resource ID to search for
     * @return an optional containing the matching resource, or empty if not found
     */
    Optional<ResourceJPA> findByResourceIDJpa(ResourceIdJPA resourceIdJPA);

    /**
     * Retrieves all resources with the given account email, whose start date is after the specified start date,
     * and whose end date is before the specified end date.
     *
     * @param accountEmail the account email to search for
     * @param startDate    the start date to compare
     * @param endDate      the end date to compare
     * @return an iterable collection of matching resources
     */
    Iterable<ResourceJPA> findByResourceIDJpaEmailAndResourceIDJpaStartDateAfterAndResourceIDJpaEndDateIsBefore(
            String accountEmail,
            String startDate, String endDate);

    /**
     * Checks if a resource with the specified resource ID exists.
     *
     * @param resourceIdJPA the resource ID to check
     * @return true if a resource with the ID exists, false otherwise
     */
    boolean existsByResourceIDJpa(ResourceIdJPA resourceIdJPA);

    /**
     * Retrieves a list of allocated resources with a given project code and in a given search time
     *
     * @param projectId  the project code to search for
     * @param searchTime the search time to search for
     * @return an iterable collection of matching resources
     */
    @Query("SELECT r FROM ResourceJPA r JOIN r.resourceIDJpa ri WHERE ri.projectCode = :projectId" +
            " AND :searchTime >= ri.startDate AND :searchTime <= ri.endDate")
    List<ResourceJPA> findAllocatedResourcesByProjectIdAndSearchTime(
            @Param("projectId") String projectId, @Param("searchTime") String searchTime);
}
