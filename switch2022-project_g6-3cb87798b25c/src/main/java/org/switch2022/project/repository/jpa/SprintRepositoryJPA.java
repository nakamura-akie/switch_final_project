package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.SprintIdJPA;
import org.switch2022.project.datamodel.SprintJPA;

import java.util.Optional;

/**
 * Repository interface for managing SprintJPA entities in the database.
 */
public interface SprintRepositoryJPA extends CrudRepository<SprintJPA, SprintIdJPA> {

    /**
     * Retrieves all sprints with a given project code
     *
     * @param projectCode the project code to search for
     * @return an iterable collection of matching sprints
     */
    Iterable<SprintJPA> findBySprintIdJPA_ProjectCode(String projectCode);

    /**
     * Retrieves an optional of a SprintJPA object with the given project code and start date
     *
     * @param projectCode the project code to search for
     * @param startDate   the start date of the sprint to search for
     * @return an Optional containing the SprintJPA object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<SprintJPA> findBySprintIdJPA_ProjectCodeAndStartDate(String projectCode, String startDate);

    /**
     * Retrieves an optional the first SprintJPA object with the specified project code,
     * from a list ordered by the end date in descending order.
     *
     * @param projectCode the project code to search for
     * @return an Optional containing the SprintJPA object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<SprintJPA> findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(String projectCode);

    /**
     * Retrieves an optional of a SprintJPA object with the given sprint ID and sprint status
     *
     * @param sprintIdJPA  the sprint ID to search for
     * @param sprintStatus the sprint status to search for
     * @return an Optional containing the SprintJPA object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<SprintJPA> findBySprintIdJPAAndSprintStatusLike(SprintIdJPA sprintIdJPA, String sprintStatus);

    /**
     * Retrieves an optional of a SprintJPA object with the given project code and sprint status
     *
     * @param projectCode  the project code to search for
     * @param sprintStatus the sprint status to search for
     * @return an Optional containing the SprintJPA object when it is found, or an empty Optional when
     * the sprint is not found
     */
    Optional<SprintJPA> findBySprintIdJPA_ProjectCodeAndSprintStatus(String projectCode, String sprintStatus);

    /**
     * Confirms if a sprint exists with the given project code and sprint status
     *
     * @param projectCode  the project code to search for
     * @param sprintStatus the sprint status to search for
     * @return true if the sprint exists, false otherwise
     */
    boolean existsBySprintIdJPAProjectCodeAndSprintStatusLike(String projectCode, String sprintStatus);
}