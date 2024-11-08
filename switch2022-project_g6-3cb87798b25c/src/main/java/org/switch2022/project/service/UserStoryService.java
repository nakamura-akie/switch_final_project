package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.utils.assembler.OutputUserStoryAssembler;
import org.switch2022.project.utils.assembler.UserStoryAssembler;
import org.switch2022.project.utils.domainevent.UserStoryEventPublisher;
import org.switch2022.project.utils.dto.NewUserStoryDTO;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserStoryService {

    private final Repository<Project, ProjectCode> projectRepository;
    private final UserStoryFactory userStoryFactory;
    private final UserStoryRepository userStoryRepository;
    private final UserStoryEventPublisher userStoryEventPublisher;

    /**
     * Constructs a new instance of the {@link UserStoryService} class with the provided dependencies.
     *
     * @param userStoryRepository      The {@link UserStoryRepository} used for accessing user story data.
     * @param projectRepository        The {@link Repository} for accessing project data.
     * @param userStoryFactory         The {@link UserStoryFactory} used for creating user story objects.
     * @param userStoryEventPublisher  The {@link UserStoryEventPublisher} used for publishing user story events.
     * @throws IllegalArgumentException if any of the dependencies is null.
     */
    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository,
                            Repository<Project, ProjectCode> projectRepository,
                            UserStoryFactory userStoryFactory,
                            UserStoryEventPublisher userStoryEventPublisher) {
        if (userStoryFactory == null) {
            throw new IllegalArgumentException("User Story Factory cannot be null.");
        }
        if (projectRepository == null) {
            throw new IllegalArgumentException("Project Repository cannot be null.");
        }
        if (userStoryRepository == null) {
            throw new IllegalArgumentException("User Story Repository cannot be null.");
        }
        if (userStoryEventPublisher == null) {
            throw new IllegalArgumentException("User Story Event Publisher cannot be null.");
        }
        this.userStoryFactory = userStoryFactory;
        this.projectRepository = projectRepository;
        this.userStoryRepository = userStoryRepository;
        this.userStoryEventPublisher = userStoryEventPublisher;
    }

    /**
     * Creates a new user story based on the provided {@link NewUserStoryDTO}.
     *
     * @param newUserStoryDTO The {@link NewUserStoryDTO} object containing the project code, user story code, and description.
     * @return The {@link UserStoryDTO} representing the created user story.
     * @throws IllegalArgumentException if the project does not exist or the user story already exists.
     */

    public UserStoryDTO createUserStory(NewUserStoryDTO newUserStoryDTO) {
        ProjectCode projectCode = newUserStoryDTO.projectCode;
        UserStoryCode userStoryCode = newUserStoryDTO.userStoryCode;
        Description userStoryDescription = newUserStoryDTO.description;
        UserStoryID userStoryID = new UserStoryID(projectCode, userStoryCode);

        boolean projectExists = projectExists(projectCode);
        boolean userStoryExists = userStoryExists(userStoryID);

        if (!projectExists) {
            throw new IllegalArgumentException("Project does not exist.");
        }

        if (userStoryExists) {
            throw new IllegalArgumentException("The User Story already exists");
        }

        UserStory newUserStory = userStoryFactory.createUserStory(userStoryID, userStoryDescription);

        UserStory savedUserStory = userStoryRepository.save(newUserStory);

        userStoryEventPublisher.publishAddUserStoryEvent(userStoryID);

        return UserStoryAssembler.createUserStoryDTO(savedUserStory);
    }

    private boolean projectExists(ProjectCode projectCode) {
        return projectRepository.existsById(projectCode);
    }

    private boolean userStoryExists(UserStoryID userStoryID) {
        return userStoryRepository.existsById(userStoryID);
    }

    /**
     * Estimates the effort for a user story identified by the provided {@link UserStoryID}.
     *
     * @param userStoryID  The {@link UserStoryID} object identifying the user story.
     * @param effortValue  The effort value to be assigned to the user story.
     * @return {@code true} if the user story is found and the effort is successfully estimated,
     * {@code false} otherwise.
     */
    public boolean estimateEffort(UserStoryID userStoryID, Effort effortValue) {

        Optional<UserStory> maybeUserStory = userStoryRepository.findById(userStoryID);

        return maybeUserStory.map(userStory -> setUserStoryInformation(userStory, effortValue, userStoryRepository))
                .orElse(false);
    }

    private Boolean setUserStoryInformation(UserStory userStoryToSet,
                                            Effort effortValue, UserStoryRepository repository) {
        userStoryToSet.defineEffort(effortValue);
        repository.save(userStoryToSet);
        return true;
    }

    /**
     * Changes the status of a user story identified by the provided {@link UserStoryCode} and {@link ProjectCode}.
     *
     * @param status         The new status to be assigned to the user story.
     * @param userStoryCode  The {@link UserStoryCode} identifying the user story.
     * @param projectCode    The {@link ProjectCode} identifying the project.
     * @return The {@link OutputUserStoryDTO} representing the user story after the status change.
     * @throws NoSuchElementException if the project or user story does not exist.
     */
    public OutputUserStoryDTO changeUserStoryStatus(UserStoryStatus status,
                                                    UserStoryCode userStoryCode, ProjectCode projectCode) {

        if (!projectRepository.existsById(projectCode)) {
            throw new NoSuchElementException("Project does not exist.");
        }

        UserStoryID selectedUserStoryId = new UserStoryID(projectCode, userStoryCode);

        Optional<UserStory> userStory = userStoryRepository.findById(selectedUserStoryId);

        if (userStory.isPresent()) {
            UserStory selectedUserStory = userStory.get();
            selectedUserStory.changeStatus(status);
            userStoryRepository.patch(selectedUserStory);
            handleUserStoryStatusFinished(status, selectedUserStoryId);
            return OutputUserStoryAssembler.createOutputUserStoryDTO(selectedUserStory);
        } else {
            throw new NoSuchElementException("User Story does not exist.");
        }
    }

    private void handleUserStoryStatusFinished(UserStoryStatus status, UserStoryID userStoryID) {
        if (status.isFinished() || status.isCancelled()) {
            userStoryEventPublisher.publishFinishUserStoryEvent(userStoryID);
        }
    }

    /**
     * Finds a user story by its unique identifier.
     *
     * @param userStoryID The {@link UserStoryID} identifying the user story.
     * @return The {@link OutputUserStoryDTO} representing the found user story.
     * @throws IllegalArgumentException if the user story does not exist.
     */
    public OutputUserStoryDTO findUserStoryByID(UserStoryID userStoryID) {

        return userStoryRepository.findById(userStoryID)
                .map(OutputUserStoryAssembler::createOutputUserStoryDTO)
                .orElseThrow(() -> new IllegalArgumentException("User Story doesn't exist"));
    }
}

