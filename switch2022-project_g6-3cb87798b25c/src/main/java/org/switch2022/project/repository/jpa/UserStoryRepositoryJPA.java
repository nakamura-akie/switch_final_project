package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryIdJPA;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryJPA;

/**
 * Repository interface for managing UserStoryJPA entities in the database.
 */
public interface UserStoryRepositoryJPA extends CrudRepository<UserStoryJPA, UserStoryIdJPA> {

    /**
     * Retrieves all user stories with the given project code and user story status not equal to either of the
     * two status provided
     *
     * @param projectCode  the project code to search for
     * @param firstStatus  the first user story status to exclude
     * @param secondStatus the second user story status to exclude
     * @return an iterable collection of matching user stories
     */
    Iterable<UserStoryJPA> findByUserStoryIdJpaProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot
            (String projectCode, String firstStatus, String secondStatus);

    /**
     * Confirms if a userStoryJPA exists with the given user story ID and user story status not equal to either of the
     * two status provided
     *
     * @param userStoryIdJPA  the user story ID to search for
     * @param firstStatus  the first user story status to exclude
     * @param secondStatus the second user story status to exclude
     * @return true if the user story exists, false otherwise
     */
    boolean existsByUserStoryIdJpaAndUserStoryStatusNotAndUserStoryStatusNot
            (UserStoryIdJPA userStoryIdJPA, String firstStatus, String secondStatus);

}
