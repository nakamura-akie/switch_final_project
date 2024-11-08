package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.repository.interfaces.UserStoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A fake implementation of the {@link UserStoryRepository} interface that uses an in-memory list to store
 * {@link UserStory} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class UserStoryRepositoryFake implements UserStoryRepository {

    private static final List<UserStory> userStoryList = new ArrayList<>();

    /**
     * Saves a user story in the repository.
     *
     * @param userStory the user story to save
     * @return the saved user story
     * @throws IllegalArgumentException if the user story already exists
     */
    @Override
    public UserStory save(UserStory userStory) {
        if (!userStoryList.contains(userStory)) {
            userStoryList.add(userStory);
        }
        return userStory;
    }

    /**
     * Retrieves all user stories from the repository.
     *
     * @return an iterable collection of all user stories
     */
    @Override
    public Iterable<UserStory> findAll() {
        return new ArrayList<>(userStoryList);
    }

    /**
     * Retrieves a user story with a given id, in this case, a userStoryID.
     *
     * @param id the userStoryID to search for
     * @return an optional containing the user story if found, or an empty optional if not found
     */
    @Override
    public Optional<UserStory> findById(UserStoryID id) {
        for (UserStory userStory : userStoryList) {
            if (userStory.identity().equals(id)) {
                return Optional.of(userStory);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a user story with the given ID exists in the repository.
     *
     * @param id the ID of the user story to check
     * @return true if the user story exists, false otherwise
     */
    @Override
    public boolean existsById(UserStoryID id) {
        for (UserStory userStory : userStoryList) {
            if (userStory.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds user stories in the repository that have the specified project
     * code and a status not equal to either of the given status.
     *
     * @param projectCode  the project code to match
     * @param firstStatus  the first status to exclude
     * @param secondStatus the second status to exclude
     * @return an iterable collection of matching user stories
     */
    @Override
    public Iterable<UserStory> findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(ProjectCode projectCode,
                                                                                           UserStoryStatus firstStatus,
                                                                                           UserStoryStatus
                                                                                                   secondStatus) {
        Iterable<UserStory> list = findAll();

        List<UserStory> userStoriesList = new ArrayList<>();

        for (UserStory userStory : list) {
            if (userStory.getProjectCode().equals(projectCode)
                    && (!userStory.getUserStoryStatus().equals(firstStatus)
                    && !userStory.getUserStoryStatus().equals(secondStatus))) {
                userStoriesList.add(userStory);
            }
        }
        return userStoriesList;
    }

    /**
     * Deletes all user stories from the repository.
     */
    @Override
    public void deleteAll() {
        userStoryList.clear();
    }

    /**
     * Deletes the user story with the given id, in this case, a userStoryID.
     *
     * @param id the userStoryID to search for
     */
    @Override
    public void deleteById(UserStoryID id) {
        userStoryList.removeIf(userStory -> userStory.identity().equals(id));
    }

    /**
     * Retrieves all user stories with an ID that match the list of user story IDs provided
     *
     * @param idList list of user story IDs to search for
     * @return an iterable collection of matching user stories
     */
    @Override
    public Iterable<UserStory> findAllById(Iterable<UserStoryID> idList) {
        List<UserStory> userStoryListResult = new ArrayList<>();

        for (UserStoryID userStoryID : idList) {
            findById(userStoryID).ifPresent(userStoryListResult::add);
        }

        return userStoryListResult;
    }

    /**
     * Updates a user story with the given changes
     *
     * @param patchedUserStory the user story with the changes
     * @return the updated user story
     */
    public UserStory patch(UserStory patchedUserStory) {
        deleteById(patchedUserStory.identity());
        save(patchedUserStory);
        return patchedUserStory;
    }

    /**
     * Confirms if a user story exists with the given user story ID and user story status not equal to either of the
     * two status provided
     *
     * @param userStoryID  the user story ID to search for
     * @param firstStatus  the first user story status to exclude
     * @param secondStatus the second user story status to exclude
     * @return true if the user story exists, false otherwise
     */
    @Override
    public boolean existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
            (UserStoryID userStoryID, UserStoryStatus firstStatus, UserStoryStatus secondStatus) {
        for (UserStory userStory : userStoryList) {
            if (userStory.identity().equals(userStoryID) && !userStory.getUserStoryStatus().equals(firstStatus)
                    && !userStory.getUserStoryStatus().equals(secondStatus)) {
                return true;
            }
        }
        return false;
    }
}
