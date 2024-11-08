package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.SprintStatus;
import org.switch2022.project.repository.interfaces.SprintRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A fake implementation of the {@link SprintRepository} interface that uses an in-memory list to store
 * {@link Sprint} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class SprintRepositoryFake implements SprintRepository {

    private static final List<Sprint> sprintList = new ArrayList<>();

    /**
     * Saves a sprint to the repository, if it is not already present in the sprint list.
     *
     * @param sprint the sprint to be saved
     * @return the saved sprint
     */
    @Override
    public Sprint save(Sprint sprint) {
        if (!sprintList.contains(sprint)) {
            sprintList.add(sprint);
        }
        return sprint;
    }

    /**
     * Retrieves all sprints from the repository.
     *
     * @return An iterable collection of all sprints in the repository
     */
    @Override
    public Iterable<Sprint> findAll() {
        return new ArrayList<>(sprintList);
    }

    /**
     * Retrieves a sprint with a given id, in this case, a SprintID
     *
     * @param id the sprintID to search for
     * @return An optional containing the sprint if found, an empty optional otherwise
     */
    @Override
    public Optional<Sprint> findById(SprintID id) {
        for (Sprint sprint : sprintList) {
            if (sprint.identity().equals(id)) {
                return Optional.of(sprint);
            }
        }
        return Optional.empty();
    }

    /**
     * Confirms if a sprint exists with the given id, in this case, a SprintID.
     *
     * @param id the sprintID to search for
     * @return true if the sprint exists, false otherwise
     */
    @Override
    public boolean existsById(SprintID id) {
        for (Sprint sprint : sprintList) {
            if (sprint.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all sprints from the repository.
     */
    @Override
    public void deleteAll() {
        sprintList.clear();
    }

    /**
     * Deletes the sprint with the given id, in this case, a SprintID.
     *
     * @param id the sprintID to search for
     * @throws UnsupportedOperationException when method is called because it is not implemented yet
     */
    @Override
    public void deleteById(SprintID id) {
        throw new UnsupportedOperationException("Sprint Repository doesn't support the deleteByID method yet");
    }

    /**
     * Returns all sprints with a given project code.
     *
     * @param projectCode the project code to search for
     * @return an iterable collection of the matching sprints
     */
    @Override
    public Iterable<Sprint> findByProjectCode(ProjectCode projectCode) {
        return sprintList.stream()
                .filter(sprint -> sprint.identity().getProjectCode().equals(projectCode))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an optional of a Sprint object with the given project code and start date
     *
     * @param projectCode the project code to search for
     * @param startDate   the start date of the sprint to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional otherwise
     */
    @Override
    public Optional<Sprint> findBySprintIdJPA_ProjectCodeAndStartDate(ProjectCode projectCode, LocalDate startDate) {
        for (Sprint sprint : sprintList) {
            if (sprint.getProjectCode().equals(projectCode) && sprint.getStartDate().equals(startDate)) {
                return Optional.of(sprint);
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves an optional of a Sprint object with the given sprint ID and sprint status
     *
     * @param sprintID  the sprint ID to search for
     * @param sprintStatus the sprint status to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional otherwise
     */
    @Override
    public Optional<Sprint> findBySprintIdAndSprintStatusLike(SprintID sprintID, SprintStatus sprintStatus) {
        for (Sprint sprint : sprintList) {
            if (sprint.identity().equals(sprintID) && sprint.getSprintStatus().equals(sprintStatus)) {
                return Optional.of(sprint);
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves an optional the first Sprint object with the specified project code,
     * from a list ordered by the end date in descending order.
     *
     * @param projectCode the project code to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional otherwise
     */
    @Override
    public Optional<Sprint> findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(ProjectCode projectCode) {
        List<Sprint> filteredSprints = sprintList.stream()
                .filter(sprint -> sprint.identity().getProjectCode().equals(projectCode))
                .collect(Collectors.toList());

        Sprint previousSprint = null;
        LocalDate maxEndDate = LocalDate.MIN;

        for (Sprint sprint : filteredSprints) {
            if (sprint.getEndDate().isAfter(maxEndDate)) {
                maxEndDate = sprint.getEndDate();
                previousSprint = sprint;
            }
        }

        return Optional.ofNullable(previousSprint);
    }

    /**
     * Retrieves an optional of a sprint with the given project code and with the status "OPENED"
     *
     * @param projectCode the project code to search for
     * @return an optional of a sprint when it is found, an empty optional otherwise
     */
    @Override
    public Optional<Sprint> findOpenSprint (ProjectCode projectCode) {
        SprintStatus status = SprintStatus.OPENED;
        for (Sprint sprint : sprintList) {
            if (sprint.getProjectCode().equals(projectCode) && sprint.getSprintStatus().equals(status)) {
                return Optional.of(sprint);
            }
        }
        return Optional.empty();
    }

    /**
     * Confirms if a sprint exists with the given project code and sprint status
     *
     * @param projectCode  the project code to search for
     * @param sprintStatus the sprint status to search for
     * @return true if the sprint exists, false otherwise
     */
    @Override
    public boolean existsBySprintIdJPAProjectCodeAndSprintStatusLike(ProjectCode projectCode,
                                                                    SprintStatus sprintStatus) {
        return false;
    }

}
