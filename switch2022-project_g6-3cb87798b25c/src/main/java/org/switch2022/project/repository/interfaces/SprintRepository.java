package org.switch2022.project.repository.interfaces;

import org.springframework.stereotype.Component;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.SprintStatus;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing Sprint entities.
 */
@Component
public interface SprintRepository extends Repository<Sprint, SprintID> {

    /**
     * Retrieves all sprints with a given project code
     *
     * @param projectCode the project code to search for
     * @return an iterable collection of matching sprints
     */
    Iterable<Sprint> findByProjectCode(ProjectCode projectCode);

    /**
     * Retrieves an optional of a Sprint object with the given project code and start date
     *
     * @param projectCode the project code to search for
     * @param startDate   the start date of the sprint to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<Sprint> findBySprintIdJPA_ProjectCodeAndStartDate(ProjectCode projectCode, LocalDate startDate);

    /**
     * Retrieves an optional the first Sprint object with the specified project code,
     * ordered by the end date in descending order.
     *
     * @param projectCode the project code to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<Sprint> findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(ProjectCode projectCode);

    /**
     * Retrieves an optional of a Sprint object with the given sprint ID and sprint status
     *
     * @param sprintID     the sprint ID to search for
     * @param sprintStatus the sprint status to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<Sprint> findBySprintIdAndSprintStatusLike(SprintID sprintID, SprintStatus sprintStatus);

    /**
     * Retrieves an optional of the open Sprint object with the given project code
     *
     * @param projectCode the project code to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<Sprint> findOpenSprint(ProjectCode projectCode);

    /**
     * Confirms if a sprint exists with the given project code and sprint status
     *
     * @param projectCode  the project code to search for
     * @param sprintStatus the sprint status to search for
     * @return true if the sprint exists, false otherwise
     */
    boolean existsBySprintIdJPAProjectCodeAndSprintStatusLike(ProjectCode projectCode, SprintStatus sprintStatus);
}
