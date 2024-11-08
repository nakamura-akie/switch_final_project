package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.utils.assembler.CreateProjectListAssembler;
import org.switch2022.project.utils.assembler.OutputProjectAssembler;
import org.switch2022.project.utils.assembler.UserStoryAssembler;
import org.switch2022.project.utils.dto.NewProjectDTO;
import org.switch2022.project.utils.dto.OutputProjectDTO;
import org.switch2022.project.utils.dto.ProjectDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;
import org.switch2022.project.utils.exception.DataValidationException;
import org.switch2022.project.utils.exception.EntityNotFoundException;
import org.switch2022.project.utils.exception.InvalidOperationException;
import org.switch2022.project.utils.mapper.ProjectMapper;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {

    private final Repository<Project, ProjectCode> projectRepository;
    private final UserStoryRepository userStoryRepository;
    private final Repository<Customer, TaxIdentificationNumber> customerRepository;
    private final Repository<BusinessSector, BusinessSectorName> businessSectorRepository;
    private final Repository<ProjectTypology, ProjectTypologyName> projectTypologyRepository;
    private final ProjectFactory projectFactory;
    private static final String PROJECT_ERROR_MESSAGE = "Project does not exist.";

    /**
     * Constructs a new instance of the ProjectService class with the specified parameters.
     *
     * @param projectRepository         The repository used for accessing and managing project data.
     *                                  Must not be null.
     * @param userStoryRepository       The repository used for accessing and managing user story data.
     *                                  Must not be null.
     * @param customerRepository        The repository used for accessing and managing customer data.
     *                                  Must not be null.
     * @param businessSectorRepository  The repository used for accessing and managing business sector data.
     *                                  Must not be null.
     * @param projectTypologyRepository The repository used for accessing and managing project typology data.
     *                                  Must not be null.
     * @param projectFactory            The factory used for creating new instances of Project objects.
     *                                  Must not be null.
     * @throws IllegalArgumentException If any of the repositories or the projectFactory is null.
     */
    @Autowired
    public ProjectService(Repository<Project, ProjectCode> projectRepository,
                          UserStoryRepository userStoryRepository,
                          Repository<Customer, TaxIdentificationNumber> customerRepository,
                          Repository<BusinessSector, BusinessSectorName> businessSectorRepository,
                          Repository<ProjectTypology, ProjectTypologyName> projectTypologyRepository,
                          ProjectFactory projectFactory) {

        if (projectRepository == null) {
            throw new IllegalArgumentException("Project Repository cannot be null");
        }

        if (userStoryRepository == null) {
            throw new IllegalArgumentException("User Story Repository cannot be null");
        }

        if (customerRepository == null) {
            throw new IllegalArgumentException("Customer Repository cannot be null");
        }

        if (businessSectorRepository == null) {
            throw new IllegalArgumentException("Business Sector Repository cannot be null");
        }

        if (projectTypologyRepository == null) {
            throw new IllegalArgumentException("Project Typology Repository cannot be null");
        }

        if (projectFactory == null) {
            throw new IllegalArgumentException("Project Factory cannot be null");
        }

        this.projectRepository = projectRepository;
        this.userStoryRepository = userStoryRepository;
        this.customerRepository = customerRepository;
        this.businessSectorRepository = businessSectorRepository;
        this.projectTypologyRepository = projectTypologyRepository;
        this.projectFactory = projectFactory;
    }

    /**
     * Creates a list of user stories for the specified project and returns a list of
     * user story DTOs (Data Transfer Objects).
     *
     * @param projectCode The code of the project for which to retrieve the user stories.
     * @return A list of user story DTOs representing the user stories of the project.
     * @throws EntityNotFoundException If the project does not exist.
     */
    public List<UserStoryDTO> createUserStoryList(ProjectCode projectCode) {
        UserStoryStatus firstStatus = UserStoryStatus.FINISHED;
        UserStoryStatus secondStatus = UserStoryStatus.CANCELLED;

        Optional<Project> projectOptional = projectRepository.findById(projectCode);

        if (projectOptional.isEmpty()) {
            throw new EntityNotFoundException(PROJECT_ERROR_MESSAGE);
        }

        Project project = projectOptional.get();
        List<UserStory> userStoryList = (List<UserStory>) userStoryRepository.
                findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot
                (projectCode, firstStatus, secondStatus);

        List<UserStoryID> userStoryPriorityList = project.getUserStoryPriorityList();

        sortUserStoriesByPriority(userStoryList, userStoryPriorityList);
        return UserStoryAssembler.createUserStoryDTOList(userStoryList.iterator());
    }

    private static void sortUserStoriesByPriority(List<UserStory> userStoryList,
                                                  List<UserStoryID> userStoryPriorityList) {

        userStoryList.sort(Comparator.comparing(us -> userStoryPriorityList.indexOf(us.identity())));
    }

    /**
     * Creates a new project based on the provided new project DTO and returns the corresponding output project DTO.
     *
     * @param newProjectDTO The DTO containing the data for the new project.
     * @return The output project DTO representing the newly created project.
     * @throws DataValidationException If the provided new project DTO is invalid or contains inconsistent data.
     */
    public OutputProjectDTO createProject(NewProjectDTO newProjectDTO) {
        validateProjectData(newProjectDTO);

        Project newProject = ProjectMapper.createDomainObject(this.projectFactory, newProjectDTO);

        Project project = projectRepository.save(newProject);

        return OutputProjectAssembler.generateDTO(project);
    }

    private void validateProjectData(NewProjectDTO newProjectDTO) {
        if (projectRepository.existsById(newProjectDTO.getProjectCode())) {
            throw new DataValidationException("Project already exists");
        }

        if (!customerRepository.existsById(newProjectDTO.getCustomerID())) {
            throw new DataValidationException("Customer doesn't exist");
        }

        if (!businessSectorRepository.existsById(newProjectDTO.getBusinessSectorName())) {
            throw new DataValidationException("Business Sector doesn't exist");
        }

        if (!projectTypologyRepository.existsById(newProjectDTO.getProjectTypologyName())) {
            throw new DataValidationException("Project Typology doesn't exist");
        }
    }

    /**
     * Retrieves a list of all projects and returns a list of project DTOs.
     *
     * @return A list of project DTOs representing all the projects.
     */
    public List<ProjectDTO> createProjectList() {
        Iterable<Project> projectList = projectRepository.findAll();
        return CreateProjectListAssembler.createProjectList(projectList);
    }

    /**
     * Finds a project by its project code and returns the corresponding output project DTO.
     *
     * @param projectCode The project code of the project to be found.
     * @return The output project DTO representing the found project.
     * @throws EntityNotFoundException If the project with the specified project code does not exist.
     */
    public OutputProjectDTO findProjectById(ProjectCode projectCode) {
        return projectRepository.findById(projectCode)
                .map(OutputProjectAssembler::generateDTO)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_ERROR_MESSAGE));
    }

    /**
     * Changes the status of a project identified by its project code.
     *
     * @param projectCode The project code of the project whose status is to be changed.
     * @param status      The new status to be assigned to the project.
     * @throws EntityNotFoundException If the project with the specified project code does not exist.
     */
    public void changeProjectStatus(ProjectCode projectCode, ProjectStatus status) {
        Project project = projectRepository.findById(projectCode)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_ERROR_MESSAGE));

        project.changeStatus(status);
        projectRepository.save(project);
    }

    /**
     * Imports the status history of a project identified by its project code.
     *
     * @param projectCode       The project code of the project to import the status history for.
     * @param statusHistoryMap  A map representing the status history, where the keys are project statuses and
     *                          the values are corresponding dates.
     * @throws IllegalArgumentException If the project with the specified project code does not exist.
     */
    public void importProjectStatusHistory(ProjectCode projectCode, Map<ProjectStatus, LocalDate> statusHistoryMap) {
        Project project = projectRepository.findById(projectCode)
                .orElseThrow(() -> new IllegalArgumentException(PROJECT_ERROR_MESSAGE));

        project.importStatusHistory(statusHistoryMap);
        projectRepository.save(project);
    }

    /**
     * Adds a user story to the product backlog of a project.
     *
     * @param userStoryID The ID of the user story to be added to the product backlog.
     * @throws InvalidOperationException If the project corresponding to the user story cannot be found.
     */
    public void addUserStoryToProductBacklog(UserStoryID userStoryID) {
        Project project = projectRepository.findById(userStoryID.getProjectCode())
                .orElseThrow(() -> new InvalidOperationException("Project couldn't be found when trying to add " +
                        "User Story to the Product Backlog"));

        project.addUserStoryToProductBacklog(userStoryID);
        projectRepository.save(project);
    }

    /**
     * Removes a user story from the product backlog of a project.
     *
     * @param userStoryID The ID of the user story to be removed from the product backlog.
     * @throws InvalidOperationException If the project corresponding to the user story cannot be found.
     */
    public void removeUserStoryFromProductBacklog(UserStoryID userStoryID) {
        Project project = projectRepository.findById(userStoryID.getProjectCode())
                .orElseThrow(() -> new InvalidOperationException("Project couldn't be found when trying to remove " +
                        "User Story from the Product Backlog"));

        project.removeUserStoryFromProductBacklog(userStoryID);
        projectRepository.save(project);
    }
}
