package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.repository.interfaces.AccountRepository;
import org.switch2022.project.repository.interfaces.ResourceRepository;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.resource.ResourceFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.assembler.OutputResourceAssembler;
import org.switch2022.project.utils.assembler.ProjectByResourceAssembler;
import org.switch2022.project.utils.assembler.ResourcesInProjectAssembler;
import org.switch2022.project.utils.dto.NewResourceDTO;
import org.switch2022.project.utils.dto.OutputResourceDTO;
import org.switch2022.project.utils.dto.ProjectByResourceDTO;
import org.switch2022.project.utils.dto.ResourceDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;
import org.switch2022.project.utils.exception.DataValidationException;
import org.switch2022.project.utils.exception.EntityNotFoundException;
import org.switch2022.project.utils.mapper.ResourceMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final Repository<Project, ProjectCode> projectRepository;
    private final AccountRepository accountRepository;
    private final ResourceFactory resourceFactory;
    private static final int MAXIMUM_ALLOCATION = 100;



    /**
     * Constructs a new ResourceService object with the provided dependencies.

     * @param resourceRepository The repository for managing resources. Must not be null.
     * @param projectRepository The repository for managing projects. Must not be null.
     * @param accountRepository The repository for managing accounts. Must not be null.
     * @param resourceFactory The factory for creating new resources. Must not be null.
     * @throws NullConstructorArgumentException if any of the dependencies is null.
     */
    @Autowired
    public ResourceService(ResourceRepository resourceRepository,
                           Repository<Project, ProjectCode> projectRepository,
                           AccountRepository accountRepository,
                           ResourceFactory resourceFactory) {

        if (projectRepository == null) {
            throw new NullConstructorArgumentException("Project Repository cannot be null");
        }
        if (accountRepository == null) {
            throw new NullConstructorArgumentException("Account Repository cannot be null");
        }
        if (resourceRepository == null) {
            throw new NullConstructorArgumentException("Resource Repository cannot be null");
        }
        if (resourceFactory == null) {
            throw new NullConstructorArgumentException("Resource Factory cannot be null");
        }

        this.resourceRepository = resourceRepository;
        this.projectRepository = projectRepository;
        this.accountRepository = accountRepository;
        this.resourceFactory = resourceFactory;
    }

    /**
     * Adds a new resource to a project.

     * @param newResourceDTO The DTO (Data Transfer Object) containing the information of the new resource.
     * @return The output ResourceDTO representing the added resource.
     * @throws EntityNotFoundException if the project or account referenced in the new resource DTO do not exist.
     */
    public OutputResourceDTO addResourceToProject(NewResourceDTO newResourceDTO) {
        Email resourceEmail = newResourceDTO.getEmail();
        ProjectCode resourceProjectCode = newResourceDTO.getProjectCode();

        if (!projectRepository.existsById(resourceProjectCode)) {
            throw new EntityNotFoundException("Project does not exist.");
        }

        if (!accountRepository.existsById(resourceEmail)) {
            throw new EntityNotFoundException("Account does not exist.");
        }

        Resource newResource =
                ResourceMapper.createDomainObject(newResourceDTO, this.resourceFactory);

        validateResource(newResource);

        Resource resource = resourceRepository.save(newResource);

        return OutputResourceAssembler.generateOutputResourceDTO(resource);
    }

    private void validateResource(Resource newResource) {
        if (!isProfileUser(newResource)) {
            throw new DataValidationException("This account does not have a User Profile.");
        }

        if (isResourceOverlapping(newResource)) {
            throw new DataValidationException("This resource is already exists in the given time period.");
        }

        if (!isAllocationPercentageUnderOneHundred(newResource)) {
            throw new DataValidationException("Allocation exceed 100%");
        }

        if (roleAlreadyExistsInTimePeriod(newResource)) {
            throw new DataValidationException(newResource.getRoleInProject() + " already exists in time period.");
        }

        validateResourceTimePeriod(newResource);

    }

    private boolean isProfileUser(Resource resource) {
        ResourceID resourceID = resource.identity();
        Email accountID = resourceID.getResourceEmail();

        return this.accountRepository.existsByEmailAndProfileName(accountID, new ProfileName("user"));
    }

    private void validateResourceTimePeriod(Resource resource) {
        ProjectCode projectCode = resource.identity().getProjectCode();
        LocalDate startDate = resource.identity().getPeriodOfAllocation().getStartDate();
        LocalDate endDate = resource.identity().getPeriodOfAllocation().getEndDate();

        Project newResourceProject = projectRepository
                .findById(projectCode)
                .orElseThrow(() -> new EntityNotFoundException("Project does not exist."));

        if (newResourceProject.isDateBeforeProjectCreationDate(startDate)) {
            throw new DataValidationException("Resource start date cannot be before the project start date.");
        }

        if (newResourceProject.isDateAfterProjectEndDate(endDate)) {
            throw new DataValidationException("Resource end date cannot be after project end date.");
        }
    }

    private boolean isResourceOverlapping(Resource newResource) {
        Iterable<Resource> resources = resourceRepository.findByProjectCode(newResource.identity().getProjectCode());

        for (Resource element : resources) {
            if (element.hasAccount(newResource.identity().getResourceEmail())
                    && element.isPeriodOverlapping(newResource.identity().getPeriodOfAllocation())) {
                return true;
            }
        }
        return false;
    }

    private boolean roleAlreadyExistsInTimePeriod(Resource newResource) {
        if (newResource.getRoleInProject().equals(ProjectRole.TEAM_MEMBER)) {
            return false;
        }
        Iterable<Resource> resourceList =
                resourceRepository.findByProjectCodeAndProjectRole(newResource.identity().getProjectCode(),
                        newResource.getRoleInProject());

        for (Resource element : resourceList) {
            if (element.isPeriodOverlapping(newResource.identity().getPeriodOfAllocation())) {
                return true;
            }
        }
        return false;
    }


    private boolean isAllocationPercentageUnderOneHundred(Resource newResource) {
        Iterable<Resource> resources = resourceRepository.findByEmail(newResource.identity().getResourceEmail());

        AtomicReference<Integer> totalAllocation = new AtomicReference<>(0);
        resources.forEach(rc -> {
            Integer partialAllocation = rc.getPercentageOfAllocation().getPercentageValue();
            totalAllocation.updateAndGet(tA -> tA + partialAllocation);
        });
        int updatedAllocation = totalAllocation.get() + newResource.getPercentageOfAllocation().getPercentageValue();

        return updatedAllocation <= MAXIMUM_ALLOCATION;
    }


    /**
     Creates a list of projects associated with a specific resource.

     * @param email The email of the account associated with the resource.
     * @param currentDate The current date to filter the resources.
     * @return A list of {@link ProjectByResourceDTO} objects representing the projects associated with the resource.
     * @throws IllegalArgumentException if the currentDate is null.
     */
    public List<ProjectByResourceDTO> createProjectListByResource(String email, LocalDate currentDate) {
        Email accountEmail = new Email(email);
        if (currentDate != null) {
            Iterable<Resource> resources = resourceRepository.findByAccountEmailAndCurrentDate
                    (accountEmail, currentDate);

            List<Project> projects = new ArrayList<>();
            for (Resource resource : resources) {
                if (resource != null) {
                    ResourceID resourceID = resource.identity();
                    ProjectCode projectCode = resourceID.getProjectCode();
                    Optional<Project> optionalProject = projectRepository.findById(projectCode);
                    if (optionalProject.isPresent()) {
                        projects.add(optionalProject.get());
                    }
                }
            }

            return ProjectByResourceAssembler.createProjectByResourceList(projects);
        } else {
            throw new IllegalArgumentException("Non valid data");
        }
    }


    /**
     Creates a list of human resources allocated to a specific project on a given date.

     * @param projectCode The project code of the project.
     * @param date The date to filter the resources.
     * @return A list of ResourceDTO objects representing the human resources allocated to the project.
     */
    public List<ResourceDTO> createHumanResourcesInProjectList(ProjectCode projectCode, LocalDate date) {
        List<Resource> resourceList = (List<Resource>) resourceRepository.
                findByProjectCodeAndIsAllocatedNow(projectCode, date);

        return ResourcesInProjectAssembler.resourceInProjectList(resourceList.iterator());
    }

    /**

     Finds a resource by its resource ID and returns the corresponding OutputResourceDTO.

     @param resourceID The resource ID to search for.
     @return The OutputResourceDTO representing the found resource.
     @throws EntityNotFoundException if the resource ID does not exist.
     */
    public OutputResourceDTO findResourceByID(ResourceID resourceID) {
        return resourceRepository
                .findById(resourceID)
                .map(OutputResourceAssembler::generateOutputResourceDTO)
                .orElseThrow(() -> new EntityNotFoundException("ResourceID does not exist."));

    }
}