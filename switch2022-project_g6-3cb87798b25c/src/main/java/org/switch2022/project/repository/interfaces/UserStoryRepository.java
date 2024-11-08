package org.switch2022.project.repository.interfaces;

import org.springframework.stereotype.Component;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.domain.valueobject.UserStoryID;

/**
 * Repository interface for managing UserStory entities.
 */
@Component
public interface UserStoryRepository extends Repository<UserStory, UserStoryID> {

    /**
     * Retrieves all user stories with the given project code and user story status not equal to either of the
     * two status provided
     *
     * @param projectCode  the project code to search for
     * @param firstStatus  the first user story status to exclude
     * @param secondStatus the second user story status to exclude
     * @return an iterable collection of matching user stories
     */
    Iterable<UserStory> findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(ProjectCode projectCode,
                                                                                    UserStoryStatus firstStatus,
                                                                                    UserStoryStatus secondStatus);

    /**
     * Retrieves all user stories with an ID that match the list of user story IDs provided
     *
     * @param listOfIDs list of user story IDs to search for
     * @return an iterable collection of matching user stories
     */
    Iterable<UserStory> findAllById(Iterable<UserStoryID> listOfIDs);

    /**
     * Confirms if a user story exists with the given user story ID and user story status not equal to either of the
     * two status provided
     *
     * @param userStoryID  the user story ID to search for
     * @param firstStatus  the first user story status to exclude
     * @param secondStatus the second user story status to exclude
     * @return true if the user story exists, false otherwise
     */
    boolean existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
            (UserStoryID userStoryID, UserStoryStatus firstStatus, UserStoryStatus secondStatus);

    /**
     * Updates a user story with the given changes
     *
     * @param userStory the user story with the changes
     * @return the updated user story
     */
    UserStory patch(UserStory userStory);

}
