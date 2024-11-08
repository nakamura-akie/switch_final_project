package org.switch2022.project.domain.sprint;

import org.switch2022.project.domain.valueobject.SprintDuration;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.SprintStatus;
import org.switch2022.project.domain.valueobject.UserStoryID;

import java.time.LocalDate;
import java.util.List;

/**
 * The SprintFactory interface defines methods for creating Sprint objects.
 */
public interface SprintFactory {

    /**
     * Creates a new Sprint object with the specified attributes.
     *
     * @param sprintID       the sprint ID
     * @param startDate      the start date of the sprint
     * @param sprintDuration the duration of the sprint
     * @param endDate        the end date of the sprint
     * @return the created Sprint object
     */
    Sprint createSprint(SprintID sprintID, LocalDate startDate, SprintDuration sprintDuration, LocalDate endDate);

    /**
     * Creates a new Sprint object with the specified attributes, including the user story code list and sprint status.
     *
     * @param sprintID           the sprint ID
     * @param startDate          the start date of the sprint
     * @param sprintDuration     the duration of the sprint
     * @param endDate            the end date of the sprint
     * @param userStoryCodeList  the list of user story IDs in the sprint backlog
     * @param sprintStatus       the status of the sprint
     * @return the created Sprint object
     */
    Sprint createSprint(SprintID sprintID, LocalDate startDate, SprintDuration sprintDuration, LocalDate endDate,
                        List<UserStoryID> userStoryCodeList, SprintStatus sprintStatus);
}