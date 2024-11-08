package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintFactory;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.SprintRepository;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.utils.assembler.CreatedSprintAssembler;
import org.switch2022.project.utils.assembler.OutputSprintAssembler;
import org.switch2022.project.utils.assembler.UserStoryAssembler;
import org.switch2022.project.utils.dto.*;
import org.switch2022.project.utils.assembler.OpenSprintOutputAssembler;
import org.switch2022.project.utils.dto.OpenSprintOutputDTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static org.switch2022.project.domain.valueobject.ProjectStatus.CLOSED;

@Service
public class SprintService {
    private static final String PROJECT_ERROR_MESSAGE = "Project does not exist.";
    private final Repository<Project, ProjectCode> projectRepository;
    private final SprintRepository sprintRepository;
    private final UserStoryRepository userStoryRepository;
    private final SprintFactory sprintFactory;

    /**

     Constructs a SprintService object with the provided dependencies.

     * @param sprintRepository The repository for managing sprints.
     * @param projectRepository The repository for managing projects.
     * @param userStoryRepository The repository for managing user stories.
     * @param sprintFactory The factory for creating sprints.
     * @throws IllegalArgumentException if any of the dependencies are null.
     */
    @Autowired
    public SprintService(SprintRepository sprintRepository,
                         Repository<Project, ProjectCode> projectRepository,
                         UserStoryRepository userStoryRepository, SprintFactory sprintFactory) {
        if (sprintRepository != null && projectRepository != null && userStoryRepository != null
                && sprintFactory != null) {
            this.sprintRepository = sprintRepository;
            this.projectRepository = projectRepository;
            this.userStoryRepository = userStoryRepository;
            this.sprintFactory = sprintFactory;

        } else {
            throw new IllegalArgumentException("Cannot have null fields");
        }
    }

    /**
     Creates a list of open sprints for a specific project.

     * @param projectCode The project code of the project.
     * @return A list of OpenSprintOutputDTO objects representing the open sprints for the project.
     * @throws RuntimeException if the project with the specified project code does not exist.
     */
    public List<OpenSprintOutputDTO> createSprintList(ProjectCode projectCode){
        if (!projectRepository.existsById(projectCode)){
            throw new RuntimeException(PROJECT_ERROR_MESSAGE);
        }
        Iterable<Sprint> sprintList = sprintRepository.findByProjectCode(projectCode);
        return OpenSprintOutputAssembler.createSprintList(sprintList.iterator());
    }

    private int getSprintCountByProjectCode(ProjectCode projectCode) {
        int sprintCount = 0;
        for (Sprint ignored : sprintRepository.findByProjectCode(projectCode)) {
            sprintCount++;
        }
        return sprintCount;
    }

    private String createSprintCode(ProjectCode projectCode) {
        int sprintCode = getSprintCountByProjectCode(projectCode) + 1;
        return "S" + sprintCode;
    }

    private SprintDuration findSprintDurationOfASpecificProject(ProjectCode projectCode) {
        Optional<Project> selectProject = projectRepository.findById(projectCode);
        if (selectProject.isEmpty()) {
            throw new IllegalArgumentException(PROJECT_ERROR_MESSAGE);
        }
        Project project = selectProject.get();
        Optional<SprintDuration> sprintDuration = project.getSprintDuration();
        if (sprintDuration.isEmpty()) {
            throw new IllegalArgumentException("Sprint duration is not defined");
        }
        return sprintDuration.get();
    }

    private TimePeriod findTimePeriodOfASpecificProject(ProjectCode projectCode) {
        Project project = projectRepository.findById(projectCode).orElseThrow(() -> new IllegalArgumentException(
                PROJECT_ERROR_MESSAGE));

        Optional<TimePeriod> projectTimePeriod = project.getPeriod();
        if (projectTimePeriod.isPresent()) {
            return projectTimePeriod.get();
        } else {
            throw new IllegalArgumentException("Project time period is not defined");
        }
    }

    private Optional<Sprint> findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(ProjectCode projectCode) {
        return sprintRepository.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(projectCode);
    }


    private LocalDate calculateSprintEndDate(LocalDate startDate, SprintDuration sprintDuration) {
        int sprintDurationInWeeks = sprintDuration.getSprintDurationValue();
        Period period = Period.ofWeeks(sprintDurationInWeeks);
        return startDate.plus(period);
    }

    private void validateIfStartDateIsAfterPreviousSprintEndDate(ProjectCode projectCode, LocalDate startDate) {
        Optional<Sprint> previousSprint = findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(projectCode);
        if (previousSprint.isPresent() && startDate.isBefore(previousSprint.get().getEndDate())) {
            throw new IllegalArgumentException("Start date must be equal to or after the end date of the previous " +
                    "sprint");
        }
    }

    private void validateSprintDatesWithinProjectRange(ProjectCode projectCode, LocalDate startDate,
                                                       LocalDate endDate) {
        TimePeriod projectPeriod = findTimePeriodOfASpecificProject(projectCode);

        if (!projectPeriod.contains(startDate) || !projectPeriod.contains(endDate)) {
            throw new IllegalArgumentException("Sprint start or/and end dates are outside the project range");
        }
    }

    private void validateProjectStatus (ProjectCode projectCode){
        Project project = projectRepository.findById(projectCode).orElseThrow(() -> new IllegalArgumentException(
                PROJECT_ERROR_MESSAGE));
        ProjectStatus projectStatus = project.getProjectStatus();
        if (projectStatus == CLOSED) {
            throw new IllegalArgumentException("Invalid project status");
        }
    }

    /**
     * Creates a new sprint based on the provided NewSprintDTO object.
     *
     * @param newSprintDTO The NewSprintDTO object containing the details of the new sprint.
     * @return The CreatedSprintDTO object representing the created sprint.
     * @throws IllegalArgumentException if the project does not exist, project status is invalid,
     *                                  start date is not defined, or the sprint already exists.
     */
    public CreatedSprintDTO createSprint(NewSprintDTO newSprintDTO) {
        ProjectCode projectCode = newSprintDTO.projectCode;

        if (!projectExists(projectCode)) {
            throw new IllegalArgumentException(PROJECT_ERROR_MESSAGE);
        }
        validateProjectStatus(projectCode);

        SprintDuration sprintDuration = findSprintDurationOfASpecificProject(projectCode);
        LocalDate startDate = newSprintDTO.startDate;

        if (Objects.isNull(startDate)) {
            throw new IllegalArgumentException("can not create a sprint with start date not defined");
        }

        SprintCode sprintCode = new SprintCode(createSprintCode(projectCode));
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        LocalDate endDate = calculateSprintEndDate(startDate, sprintDuration);

        boolean sprintExists = sprintExists(sprintID);

        if (sprintExists) {
            throw new IllegalArgumentException("Sprint already exists");
        }

        validateSprintDatesWithinProjectRange(projectCode, startDate, endDate);
        validateIfStartDateIsAfterPreviousSprintEndDate(projectCode, startDate);

        Sprint newSprintJPA = sprintFactory.createSprint(sprintID, startDate, sprintDuration, endDate);
        Sprint savedSprint = sprintRepository.save(newSprintJPA);
        return CreatedSprintAssembler.generateDTO(savedSprint);
    }

    /**
     * Adds a user story to the sprint backlog for a specific project and sprint.
     *
     * @param projectCode                The project code of the project.
     * @param sprintCode                 The sprint code of the sprint.
     * @param newSprintBacklogUserStoryDTO The {@link NewSprintBacklogUserStoryDTO} object containing the details
     *                                   of the user story to be added to the sprint backlog.
     * @return The {@link OutputSprintDTO} object representing the updated sprint.
     * @throws IllegalArgumentException if the project does not exist, the user story does not exist
     *                                  on the product backlog, or the sprint does not exist or is not open.
     */

    public OutputSprintDTO addUserStoryToSprintBacklog(ProjectCode projectCode,
                                                       SprintCode sprintCode,
                                                       NewSprintBacklogUserStoryDTO newSprintBacklogUserStoryDTO) {

        UserStoryID userStoryID = new UserStoryID(projectCode, newSprintBacklogUserStoryDTO.userStoryCode);
        SprintID sprintID = new SprintID(projectCode, sprintCode);

        checkIfProjectExists(projectCode);
        checkIfUserStoryExistsOnProductBacklog(userStoryID);

        Sprint sprint = checkIfSprintExistsAndIsOpened(sprintID);
        sprint.addToSprintBacklog(userStoryID);
        Sprint updatedSprint = sprintRepository.save(sprint);
        return OutputSprintAssembler.generateDTO(updatedSprint);
    }

    private void checkIfProjectExists(ProjectCode projectCode) {
        boolean findProject = projectRepository.existsById(projectCode);

        if (!findProject) {
            throw new NoSuchElementException("The selected Project does not exist");
        }
    }

    private void checkIfUserStoryExistsOnProductBacklog(UserStoryID userStoryID) {
        boolean findUserStory = userStoryRepository
                .existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                        (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        if (!findUserStory) {
            throw new NoSuchElementException
                    ("The selected User Story does not exist in the Product Backlog");
        }
    }

    private Sprint checkIfSprintExistsAndIsOpened(SprintID sprintID) {
        Optional<Sprint> findSprint = sprintRepository
                .findBySprintIdAndSprintStatusLike(sprintID, SprintStatus.OPENED);

        if (findSprint.isPresent()) {
            return findSprint.get();
        } else {
            throw new NoSuchElementException("The selected Sprint is currently closed");
        }
    }

    private boolean projectExists(ProjectCode projectCode) {
        return projectRepository.existsById(projectCode);
    }

    private boolean sprintExists(SprintID sprintID) {
        return sprintRepository.existsById(sprintID);
    }

    /**
     * Retrieves the user stories from the sprint backlog for a specific project and sprint, and returns them
     * as a list of {@link UserStoryDTO}.
     *
     * @param viewSprintBacklogDTO The {@link ViewSprintBacklogDTO} object containing the project code and sprint code.
     * @return A list of {@link UserStoryDTO} representing the user stories in the sprint backlog.
     * @throws IllegalArgumentException if the project does not exist or the sprint does not exist.
     */
    public List<UserStoryDTO> showScrumBoard(ViewSprintBacklogDTO viewSprintBacklogDTO) {
        SprintID sprintIdentifier = new SprintID(viewSprintBacklogDTO.projectCode, viewSprintBacklogDTO.sprintCode);

        if (!projectExists(viewSprintBacklogDTO.projectCode)) {
            throw new IllegalArgumentException(PROJECT_ERROR_MESSAGE);
        }

        Optional<Sprint> maybeCurrentSprint = sprintRepository.findById(sprintIdentifier);

        if (maybeCurrentSprint.isEmpty()) {
            throw new IllegalArgumentException("Sprint does not exist.");
        }

        Sprint currentSprint = maybeCurrentSprint.get();
        List<UserStoryID> sprintBacklog = currentSprint.getIDsOfUserStoriesInSprintBacklog();

        List<UserStory> userStoryList = (List<UserStory>) userStoryRepository.findAllById(sprintBacklog);

        return UserStoryAssembler.createUserStoryDTOList(userStoryList.iterator());
    }

    /**
     * Changes the status of a sprint identified by the provided {@link SprintStatusDTO}.
     *
     * @param sprintStatusDTO The {@link SprintStatusDTO} object containing the sprint ID and the new sprint status.
     * @return The {@link CreatedSprintDTO} object representing the sprint with the updated status.
     * @throws IllegalArgumentException if the sprint does not exist.
     */
    public CreatedSprintDTO changeSprintStatus(SprintStatusDTO sprintStatusDTO) {
        SprintID sprintID = sprintStatusDTO.sprintID;
        SprintStatus sprintStatus = sprintStatusDTO.sprintStatus;

        Optional<Sprint> maybeSprint = sprintRepository.findById(sprintID);
        Sprint sprintWithChangedStatus = maybeSprint.map(anySprint -> setSprintInformation(anySprint, sprintStatus))
                .orElseThrow(() -> new IllegalArgumentException("Sprint does not exist"));

        return CreatedSprintAssembler.generateDTO(sprintWithChangedStatus);
    }


    private Sprint setSprintInformation(Sprint sprint, SprintStatus sprintStatus) {
        if (sprint.hasStatus(sprintStatus)) {
            throw new IllegalArgumentException("The sprint already has the desired status.");
        }

        if (sprintStatus == SprintStatus.OPENED) {
            boolean anyOpenSprint = sprintRepository.existsBySprintIdJPAProjectCodeAndSprintStatusLike(sprint
                    .identity().getProjectCode(), SprintStatus.OPENED);
            if (anyOpenSprint) {
                throw new IllegalArgumentException("There is already an open sprint.");
            }
        }

        sprint.defineSprintStatus(sprintStatus);
        return sprintRepository.save(sprint);
    }


    /**
     * Retrieves the open sprint for the specified project and returns it as an {@link OpenSprintOutputDTO}.
     *
     * @param projectCode The project code of the project.
     * @return The {@link OpenSprintOutputDTO} object representing the open sprint.
     * @throws IllegalArgumentException if the project does not exist or there are no open sprints.
     */
    public OpenSprintOutputDTO getOpenSprint(ProjectCode projectCode) {

        if (!projectExists(projectCode)) {
            throw new IllegalArgumentException(PROJECT_ERROR_MESSAGE);
        }

        Optional<Sprint> maybeCurrentSprint =
                sprintRepository.findOpenSprint(projectCode);

        if (maybeCurrentSprint.isPresent()) {
            Sprint openSprint = maybeCurrentSprint.get();
            return OpenSprintOutputAssembler.generateDTO(openSprint);
        }
        throw new IllegalArgumentException("There are no open sprints.");
    }
}