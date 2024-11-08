package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.assembler.UserStoryDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryIdJPA;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryJPA;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.repository.jpa.UserStoryRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Persistence implementation of the {@link UserStoryRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link UserStoryRepositoryJPA} interface to store user stories.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class UserStoryRepositoryPersistence implements UserStoryRepository {

    private final UserStoryRepositoryJPA jpaRepository;
    private final UserStoryDomainDataAssembler jpaAssembler;

    /**
     * Constructs a new instance of the UserStoryRepositoryPersistence class.
     *
     * @param jpaRepository the JPA repository for UserStory entities
     * @param jpaAssembler  the data assembler for converting between domain and data model
     * @throws IllegalArgumentException if either jpaRepository or jpaAssembler is null
     */
    public UserStoryRepositoryPersistence(UserStoryRepositoryJPA jpaRepository,
                                          UserStoryDomainDataAssembler jpaAssembler) {
        if (jpaRepository == null || jpaAssembler == null) {
            throw new IllegalArgumentException("Cannot have null fields.");
        }

        this.jpaRepository = jpaRepository;
        this.jpaAssembler = jpaAssembler;
    }

    /**
     * Saves a user story in the repository.
     *
     * @param domainUserStory the UserStory to save
     * @return the saved UserStory
     */
    @Override
    public UserStory save(UserStory domainUserStory) {
        UserStoryJPA userStoryJpa = jpaAssembler.toData(domainUserStory);
        UserStoryJPA savedUserStoryJPA = jpaRepository.save(userStoryJpa);

        return jpaAssembler.toDomain(savedUserStoryJPA);
    }

    /**
     * Retrieves all user stories from the repository.
     *
     * @return an iterable collection of all user stories in the repository
     */
    @Override
    public Iterable<UserStory> findAll() {
        Iterable<UserStoryJPA> listOfJpaUserStories = jpaRepository.findAll();

        List<UserStory> listOfDomainUserStories = new ArrayList<>();

        for (UserStoryJPA userStoryJpa : listOfJpaUserStories) {
            UserStory domainUserStory = jpaAssembler.toDomain(userStoryJpa);
            listOfDomainUserStories.add(domainUserStory);
        }
        return listOfDomainUserStories;
    }

    /**
     * Retrieves a user story from the repository with the given ID, in this case, a userStoryID.
     *
     * @param id the ID of the user story to retrieve
     * @return an optional containing the retrieved usre story, or an empty optional if not found
     */
    @Override
    public Optional<UserStory> findById(UserStoryID id) {
        ProjectCode projectCode = id.getProjectCode();
        UserStoryCode userStoryCode = id.getUserStoryCode();

        UserStoryIdJPA jpaId = new UserStoryIdJPA();
        jpaId.setProjectCode(projectCode.getProjectCodeValue());
        jpaId.setUserStoryCode(userStoryCode.getUserStoryCodeValue());

        Optional<UserStoryJPA> maybeUserStoryJpa = jpaRepository.findById(jpaId);

        if (maybeUserStoryJpa.isPresent()) {
            UserStoryJPA userStoryJpa = maybeUserStoryJpa.get();
            UserStory domainUserStory = jpaAssembler.toDomain(userStoryJpa);
            return Optional.of(domainUserStory);
        }

        return Optional.empty();
    }


    /**
     * Checks if a user story exists in the repository with the given ID, in this case a user story ID.
     *
     * @param id the ID of the user story to search for
     * @return true if the user story exists, false otherwise
     */
    @Override
    public boolean existsById(UserStoryID id) {
        ProjectCode projectCode = id.getProjectCode();
        UserStoryCode userStoryCode = id.getUserStoryCode();

        UserStoryIdJPA jpaId = new UserStoryIdJPA();
        jpaId.setProjectCode(projectCode.getProjectCodeValue());
        jpaId.setUserStoryCode(userStoryCode.getUserStoryCodeValue());

        return jpaRepository.existsById(jpaId);
    }

    /**
     * Retrieves a list of user story from the repository that have the specified project code and
     * a status not equal to either of the given status.
     *
     * @param projectCode  the project code to search for
     * @param firstStatus  the status to exclude
     * @param secondStatus the status to exclude
     * @return an iterable collection of user stories matching the criteria
     */
    @Override
    public Iterable<UserStory> findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(
            ProjectCode projectCode, UserStoryStatus firstStatus, UserStoryStatus secondStatus) {

        String getProjectCode = projectCode.getProjectCodeValue();
        String firstStatusValue = firstStatus.toString();
        String secondStatusValue = secondStatus.toString();

        Iterable<UserStoryJPA> jpaList = jpaRepository
                .findByUserStoryIdJpaProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(getProjectCode,
                        firstStatusValue, secondStatusValue);

        List<UserStory> userStoriesList = new ArrayList<>();

        for (UserStoryJPA userStoryJpa : jpaList) {
            UserStory userStory = jpaAssembler.toDomain(userStoryJpa);
            userStoriesList.add(userStory);
        }
        return userStoriesList;
    }

    /**
     * Deletes all user stories from the repository.
     */
    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    /**
     * Deletes a user story with the specified ID from the repository.
     *
     * @param id the id of the user story to delete
     */
    @Override
    public void deleteById(UserStoryID id) {
        ProjectCode projectCode = id.getProjectCode();
        UserStoryCode userStoryCode = id.getUserStoryCode();

        UserStoryIdJPA jpaId = new UserStoryIdJPA();
        jpaId.setProjectCode(projectCode.getProjectCodeValue());
        jpaId.setUserStoryCode(userStoryCode.getUserStoryCodeValue());

        jpaRepository.deleteById(jpaId);
    }

    /**
     * Returns user stories from a list of user story IDs
     *
     * @param listOfIDs list of user story Ids
     * @return iterable colleciton of the user stories found
     */
    @Override
    public Iterable<UserStory> findAllById(Iterable<UserStoryID> listOfIDs) {
        List<UserStoryIdJPA> listOfUserStoryIdJPA = jpaAssembler.listOfIdsToData(listOfIDs);
        Iterable<UserStoryJPA> listOfUserStoryJPA = jpaRepository.findAllById(listOfUserStoryIdJPA);
        return jpaAssembler.userStoryJPAListToDomain(listOfUserStoryJPA);
    }

    /**
     * Updates a user story with the given changes
     *
     * @param patchedUserStory the user story with the changes
     * @return the updated user story
     */
    public UserStory patch(UserStory patchedUserStory) {
        return save(patchedUserStory);
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
        UserStoryIdJPA userStoryIdJPA = new UserStoryIdJPA(
                userStoryID.getProjectCode().getProjectCodeValue(),
                userStoryID.getUserStoryCode().getUserStoryCodeValue()
        );

        return jpaRepository.existsByUserStoryIdJpaAndUserStoryStatusNotAndUserStoryStatusNot
                (userStoryIdJPA, firstStatus.name(), secondStatus.name());
    }
}
