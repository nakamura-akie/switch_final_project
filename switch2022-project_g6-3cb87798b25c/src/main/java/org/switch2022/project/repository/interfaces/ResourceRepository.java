package org.switch2022.project.repository.interfaces;

import org.springframework.stereotype.Component;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.ProjectRole;
import org.switch2022.project.domain.valueobject.ResourceID;

import java.time.LocalDate;

/**
 * Repository interface for managing Resource entities.
 */
@Component
public interface ResourceRepository extends Repository<Resource, ResourceID> {

    /**
     * Retrieves all resources with the given email.
     *
     * @param email the email to search for
     * @return an iterable collection of matching resources
     */
    Iterable<Resource> findByEmail(Email email);

    /**
     * Retrieves all resources with the given project code and project role.
     *
     * @param projectCode the project code to search for
     * @param projectRole the project role to search for
     * @return an iterable collection of matching resources
     */
    Iterable<Resource> findByProjectCodeAndProjectRole(ProjectCode projectCode, ProjectRole projectRole);

    /**
     * Retrieves all resources with the given project code.
     *
     * @param projectCode the project code to search for
     * @return an iterable collection of matching resources
     */
    Iterable<Resource> findByProjectCode(ProjectCode projectCode);

    /**
     * Retrieves all resources with the given account email that have the specified current date.
     *
     * @param accountEmail the account email to search for
     * @param currentDate  the current date to compare
     * @return an iterable collection of matching resources
     */
    Iterable<Resource> findByAccountEmailAndCurrentDate(Email accountEmail, LocalDate currentDate);

    /**
     * Retrieves all resources with the given project code that are allocated in the current date
     *
     * @param projectCode the project code to search for
     * @param searchDate  the current date to compare
     * @return an iterable collection of matching resources
     */
    Iterable<Resource> findByProjectCodeAndIsAllocatedNow(ProjectCode projectCode, LocalDate searchDate);
}
