package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.resource.ResourceFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.AccountRepository;
import org.switch2022.project.repository.interfaces.ProjectRepository;
import org.switch2022.project.repository.interfaces.ResourceRepository;
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

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ResourceServiceTest {

    @Autowired
    ResourceService resourceService;
    @MockBean
    private ResourceRepository resourceRepositoryFake;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private AccountRepository accountRepositoryFake;
    @MockBean
    private ResourceFactory resourceFactory;
    private NewResourceDTO newResourceDTO;
    private MockedStatic<ResourceMapper> resourceMapper;
    private Resource newResource;
    private Resource existingResource;

    private List<Resource> resourceList;
    private MockedStatic<OutputResourceAssembler> outputResourceAssembler;

    @BeforeEach
    void init() {
        resourceMapper = mockStatic(ResourceMapper.class);
        resourceRepositoryFake = mock(ResourceRepository.class);
        projectRepository = mock(ProjectRepository.class);
        accountRepositoryFake = mock(AccountRepository.class);
        resourceFactory = mock(ResourceFactory.class);
        newResourceDTO = mock(NewResourceDTO.class);
        outputResourceAssembler = mockStatic(OutputResourceAssembler.class);
        resourceService = new ResourceService(
                resourceRepositoryFake,
                projectRepository,
                accountRepositoryFake,
                resourceFactory);

        String email = "john@john.com";
        String projectCode = "P1";
        String startDate = "2022-01-01";
        String endDate = "2022-02-02";
        int percentageOfAllocation = 50;
        double costPerHour = 2.5;

        this.newResourceDTO.setProjectRole(ProjectRole.TEAM_MEMBER);
        this.newResourceDTO.setEmail( new Email(email));
        this.newResourceDTO.setProjectCode(new ProjectCode(projectCode));
        this.newResourceDTO.setCostPerHour(new CostPerHour(costPerHour));
        this.newResourceDTO.setPercentageOfAllocation(new PercentageOfAllocation(percentageOfAllocation));
        this.newResourceDTO.setStartDate(LocalDate.parse(startDate));
        this.newResourceDTO.setEndDate(LocalDate.parse(endDate));

        newResource = mock(Resource.class);
        existingResource = mock(Resource.class);

        this.resourceList = new ArrayList<>();
        resourceList.add(existingResource);

        this.resourceMapper.when(() -> ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);
    }

    @AfterEach
    void tearDown() {
        resourceMapper.close();
        outputResourceAssembler.close();
        resourceRepositoryFake.deleteAll();
        accountRepositoryFake.deleteAll();
        projectRepository.deleteAll();
        this.resourceList = null;
    }

    @Test
    void ResourceService_InvalidResourceRepository_ThrowsException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new ResourceService(null, projectRepository, accountRepositoryFake, resourceFactory));
        assertEquals("Resource Repository cannot be null", exception.getMessage());
    }

    @Test
    void ResourceService_InvalidProjectRepository_ThrowsException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new ResourceService(resourceRepositoryFake, null, accountRepositoryFake, resourceFactory));
        assertEquals("Project Repository cannot be null", exception.getMessage());
    }

    @Test
    void ResourceService_InvalidAccountRepository_ThrowsException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new ResourceService(resourceRepositoryFake, projectRepository, null, resourceFactory));
        assertEquals("Account Repository cannot be null", exception.getMessage());
    }

    @Test
    void ResourceService_InvalidResourceFactory_ThrowsException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new ResourceService(resourceRepositoryFake, projectRepository, accountRepositoryFake, null));
        assertEquals("Resource Factory cannot be null", exception.getMessage());
    }

    @Test
    void addResourceToProject_ProjectDoesNotExist_False() {
        ProjectCode resourceProjectCode = mock(ProjectCode.class);

        when(projectRepository.existsById(resourceProjectCode)).thenReturn(false);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void addResourceToProject_AccountDoesNotExist_False() {
        Email resourceEmail = mock(Email.class);
        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(resourceEmail)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Account does not exist.", exception.getMessage());
    }

    @Test
    void addResourceToProject_AddTeamMember_HappyPath_True() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        OutputResourceDTO outputResourceDTO = mock(OutputResourceDTO.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);


        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(50);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        when(resourceRepositoryFake.save(newResource)).thenReturn(newResource);
        this.outputResourceAssembler.when(() -> OutputResourceAssembler.generateOutputResourceDTO(newResource)).thenReturn(outputResourceDTO);


        OutputResourceDTO result = resourceService.addResourceToProject(newResourceDTO);

        assertEquals(outputResourceDTO, result);
    }

    @Test
    void addResourceToProject_AddProductOwner_HappyPath_True() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        OutputResourceDTO outputResourceDTO = mock(OutputResourceDTO.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);


        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.PRODUCT_OWNER);
        when(resourceRepositoryFake.findByProjectCodeAndProjectRole(testResourceProjectCode, ProjectRole.PRODUCT_OWNER)).thenReturn(resourceList);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(50);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(resourceRepositoryFake.save(newResource)).thenReturn(newResource);
        this.outputResourceAssembler.when(() -> OutputResourceAssembler.generateOutputResourceDTO(newResource)).thenReturn(outputResourceDTO);


        OutputResourceDTO result = resourceService.addResourceToProject(newResourceDTO);

        assertEquals(outputResourceDTO, result);
    }

    @Test
    void addResourceToProject_AddProjectManager_HappyPath_True() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        OutputResourceDTO outputResourceDTO = mock(OutputResourceDTO.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));


        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);


        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.PROJECT_MANAGER);
        when(resourceRepositoryFake.findByProjectCodeAndProjectRole(testResourceProjectCode, ProjectRole.PROJECT_MANAGER)).thenReturn(resourceList);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(50);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(resourceRepositoryFake.save(newResource)).thenReturn(newResource);
        this.outputResourceAssembler.when(() -> OutputResourceAssembler.generateOutputResourceDTO(newResource)).thenReturn(outputResourceDTO);


        OutputResourceDTO result = resourceService.addResourceToProject(newResourceDTO);

        assertEquals(outputResourceDTO, result);
    }

    @Test
    void addResourceToProject_AddScrumMaster_HappyPath_True() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        OutputResourceDTO outputResourceDTO = mock(OutputResourceDTO.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));


        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(any())).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.SCRUM_MASTER);
        when(resourceRepositoryFake.findByProjectCodeAndProjectRole(testResourceProjectCode, ProjectRole.SCRUM_MASTER)).thenReturn(resourceList);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(50);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(resourceRepositoryFake.save(newResource)).thenReturn(newResource);
        this.outputResourceAssembler.when(() -> OutputResourceAssembler.generateOutputResourceDTO(newResource)).thenReturn(outputResourceDTO);


        OutputResourceDTO result = resourceService.addResourceToProject(newResourceDTO);

        assertEquals(outputResourceDTO, result);
    }

    @Test
    void addResourceToProject_NotUserProfile_ThrowsException() {
        Account testAccount = mock(Account.class);

        ProfileName testProfileName = new ProfileName("administrator");
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("This account does not have a User Profile.", exception.getMessage());

    }

    @Test
    void addResourceToProject_AddTeamMember_AllocationAboveOneHundred_False() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));


        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(100);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Allocation exceed 100%", exception.getMessage());

    }

    @Test
    void addResourceToProject_AddScrumMaster_AllocationAboveOneHundred_False() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);


        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.SCRUM_MASTER);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(100);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Allocation exceed 100%", exception.getMessage());
    }

    @Test
    void addResourceToProject_AddProjectManager_AllocationAboveOneHundred_False() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);


        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.PROJECT_MANAGER);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(100);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Allocation exceed 100%", exception.getMessage());
    }

    @Test
    void addResourceToProject_AddProductOwner_AllocationAboveOneHundred_False() {
        Account testAccount = mock(Account.class);

        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));


        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.PRODUCT_OWNER);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(100);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Allocation exceed 100%", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceAlreadyExistsOverlapping_Exception() {
        Account testAccount = mock(Account.class);
        ProfileName testProfileName = new ProfileName("user");
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Project testProject = mock(Project.class);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(existingResource);

        when(existingResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));


        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(resourceList.get(0).hasAccount(any())).thenReturn(true);
        when(resourceList.get(0).isPeriodOverlapping(any())).thenReturn(true);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }


    @Test
    void addResourceToProject_DifferentProductOwnerAlreadyExistsInTimePeriod_False() {
        Account testAccount = mock(Account.class);
        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        Email existingResourceEmail = mock(Email.class);
        Project testProject = mock(Project.class);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(any())).thenReturn(true);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(100);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(newResource.getRoleInProject()).thenReturn(ProjectRole.PRODUCT_OWNER);
        when(resourceRepositoryFake.findByProjectCodeAndProjectRole(testResourceProjectCode, ProjectRole.PRODUCT_OWNER)).thenReturn(resourceList);


        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("PRODUCT_OWNER already exists in time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_DifferentScrumMasterAlreadyExistsInTimePeriod_False() {
        Account testAccount = mock(Account.class);
        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        Email existingResourceEmail = mock(Email.class);
        Project testProject = mock(Project.class);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(any())).thenReturn(true);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(100);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(newResource.getRoleInProject()).thenReturn(ProjectRole.SCRUM_MASTER);
        when(resourceRepositoryFake.findByProjectCodeAndProjectRole(testResourceProjectCode, ProjectRole.SCRUM_MASTER)).thenReturn(resourceList);


        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("SCRUM_MASTER already exists in time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_DifferentProjectManagerAlreadyExistsInTimePeriod_False() {
        Account testAccount = mock(Account.class);
        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        Email existingResourceEmail = mock(Email.class);
        Project testProject = mock(Project.class);

        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);
        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(100);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(newResource.getRoleInProject()).thenReturn(ProjectRole.PROJECT_MANAGER);
        when(resourceRepositoryFake.findByProjectCodeAndProjectRole(testResourceProjectCode, ProjectRole.PROJECT_MANAGER)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(any())).thenReturn(true);


        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("PROJECT_MANAGER already exists in time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_NewResourceStartDateBeforeProjectStartDate_ThrowsException() {
        Account testAccount = mock(Account.class);
        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(true);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(false);


        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(50);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Resource start date cannot be before the project start date.", exception.getMessage());
    }

    @Test
    void addResourceToProject_NewResourceEndDateAfterProjectEndDate_ThrowsException() {
        Account testAccount = mock(Account.class);
        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        ProfileName testProfileName = new ProfileName("user");
        TimePeriod testTimePeriod = mock(TimePeriod.class);
        ResourceID testResourceID = mock(ResourceID.class);
        Email testResourceEmail = mock(Email.class);
        ProjectCode testResourceProjectCode = mock(ProjectCode.class);
        Email existingResourceEmail = mock(Email.class);
        ResourceID existingResourceID = mock(ResourceID.class);
        ProjectCode existingResourceProjectCode = mock(ProjectCode.class);
        Project testProject = mock(Project.class);

        when(projectRepository.findById(any())).thenReturn(Optional.ofNullable(testProject));
        assert testProject != null;
        when(testProject.isDateBeforeProjectCreationDate(any())).thenReturn(false);
        when(testProject.isDateAfterProjectEndDate(any())).thenReturn(true);


        when(projectRepository.existsById(any())).thenReturn(true);
        when(accountRepositoryFake.existsById(any())).thenReturn(true);
        when(ResourceMapper.createDomainObject(newResourceDTO, resourceFactory)).thenReturn(newResource);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(accountRepositoryFake.findById(testResourceEmail)).thenReturn(Optional.ofNullable(testAccount));

        when(accountRepositoryFake.existsByEmailAndProfileName(testResourceEmail, testProfileName)).thenReturn(true);

        when(resourceRepositoryFake.findByProjectCode(testResourceProjectCode)).thenReturn(resourceList);
        when(existingResource.isPeriodOverlapping(testTimePeriod)).thenReturn(false);

        when(newResource.identity()).thenReturn(testResourceID);
        when(testResourceID.getProjectCode()).thenReturn(testResourceProjectCode);
        when(testResourceID.getResourceEmail()).thenReturn(testResourceEmail);
        when(resourceRepositoryFake.findByEmail(testResourceEmail)).thenReturn(resourceList);
        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(10);

        when(newResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);

        when(existingResource.identity()).thenReturn(existingResourceID);
        when(existingResourceID.getResourceEmail()).thenReturn(existingResourceEmail);
        when(existingResourceID.getProjectCode()).thenReturn(existingResourceProjectCode);
        when(testResourceID.getPeriodOfAllocation()).thenReturn(testTimePeriod);

        when(existingResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);
        when(anotherPercentageOfAllocation.getPercentageValue()).thenReturn(50);
        when(newResource.getPercentageOfAllocation()).thenReturn(anotherPercentageOfAllocation);

        Exception exception = assertThrows(DataValidationException.class, () ->
                resourceService.addResourceToProject(newResourceDTO));
        assertEquals("Resource end date cannot be after project end date.", exception.getMessage());
    }



    @Test
    void createProjectListByResource_NoResourcesExist_ReturnsEmptyList() {
        String testEmail = "test@example.com";
        LocalDate testDate = LocalDate.of(2021, 10, 10);

        List<Resource> emptyResourceList = Collections.emptyList();

        when(resourceRepositoryFake.findByAccountEmailAndCurrentDate(any(), any())).thenReturn(emptyResourceList);

        List<ProjectByResourceDTO> result = resourceService.createProjectListByResource(testEmail, testDate);

        assertEquals(0, result.size());
    }

    @Test
    void createProjectListByResource_NoProjectExist_ReturnsEmptyList() {
        String testEmail = "test@example.com";
        LocalDate testDate = LocalDate.of(2021, 10, 10);

        List<Resource> resourceList = new ArrayList<>();

        when(resourceRepositoryFake.findByAccountEmailAndCurrentDate(any(), any())).thenReturn(resourceList);

        List<ProjectByResourceDTO> result = resourceService.createProjectListByResource(testEmail, testDate);

        assertEquals(0, result.size());
    }

    @Test
    void findById_ProjectExists_ReturnsProject() {
        ProjectCode projectId = new ProjectCode("P1");

        Project projectTest = mock(Project.class);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectTest));

        Optional<Project> result = projectRepository.findById(projectId);

        assertTrue(result.isPresent());
        assertEquals(projectTest, result.get());
    }

    @Test
    void createProjectListByResource_InvalidData_ThrowsIllegalArgumentException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.createProjectListByResource("test@test.com", null);
        });
        String expectedMessage = "Non valid data";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createProjectListByResource_HappyPath_ReturnsCorrectDTOList() {
        String email = "test@test.com";
        LocalDate currentDate = LocalDate.of(2021, 10, 10);
        ProjectCode projectCode1 = new ProjectCode("P1");
        ProjectCode projectCode2 = new ProjectCode("P2");
        Email accountEmail = new Email(email);

        Resource resource1 = mock(Resource.class);
        Resource resource2 = mock(Resource.class);

        Iterable<Resource> resources = Arrays.asList(resource1, resource2);
        when(resourceRepositoryFake.findByAccountEmailAndCurrentDate(any(), any())).thenReturn(resources);
        when(resource1.hasAccount(accountEmail)).thenReturn(true);
        when(resource1.resourceVerificationOfDate(currentDate)).thenReturn(true);

        Project project1 = mock(Project.class);
        Project project2 = mock(Project.class);

        List<Project> projectList = new ArrayList<>();
        projectList.add(project1);
        projectList.add(project2);

        ResourceID resourceID1 = mock(ResourceID.class);
        when(resource1.identity()).thenReturn(resourceID1);
        when(resourceID1.getProjectCode()).thenReturn(projectCode1);

        ResourceID resourceID2 = mock(ResourceID.class);
        when(resource2.identity()).thenReturn(resourceID2);
        when(resourceID2.getProjectCode()).thenReturn(projectCode2);

        when(projectRepository.findById(projectCode1)).thenReturn(Optional.of(project1));
        when(projectRepository.findById(projectCode2)).thenReturn(Optional.of(project2));

        List<ProjectByResourceDTO> projectByResourceDTOList = new ArrayList<>();
        ProjectByResourceDTO projectDTOOne = mock(ProjectByResourceDTO.class);
        ProjectByResourceDTO projectDTOTwo = mock(ProjectByResourceDTO.class);
        projectByResourceDTOList.add(projectDTOOne);
        projectByResourceDTOList.add(projectDTOTwo);

        MockedStatic<ProjectByResourceAssembler> projectByResourceAssemblerDouble =
                mockStatic(ProjectByResourceAssembler.class, invocation -> {
                    if (invocation.getMethod().getName().equals("createProjectByResourceList")) {
                        return projectByResourceDTOList;
                    }
                    return null;
                });

        List<ProjectByResourceDTO> result = resourceService.createProjectListByResource(email, currentDate);

        assertEquals(projectByResourceDTOList, result);

        projectByResourceAssemblerDouble.close();
    }

    @Test
    void createHumanResourcesInProjectList_ReturnsList() {
        ResourceRepository resourceRepositoryDoubleD = mock(ResourceRepository.class);
        MockedStatic<ResourcesInProjectAssembler> assemblerDouble = mockStatic(ResourcesInProjectAssembler.class);

        ProjectCode projectCode = new ProjectCode("P001");
        LocalDate currentDate = LocalDate.of(2022, 1, 20);

        Resource mariaResource = mock(Resource.class);
        Resource anaResource = mock(Resource.class);
        resourceRepositoryDoubleD.save(mariaResource);
        resourceRepositoryDoubleD.save(anaResource);

        Iterable<Resource> resourceIterable = Arrays.asList(mariaResource, anaResource);


        when(mariaResource.isAllocated(any())).thenReturn(true);
        when(anaResource.isAllocated(any())).thenReturn(true);
        when(resourceRepositoryDoubleD.findByProjectCodeAndIsAllocatedNow(any(), eq(currentDate)))
                .thenReturn(resourceIterable);

        ResourceDTO resourceDTOMaria = new ResourceDTO();
        resourceDTOMaria.projectRole = "sm";
        resourceDTOMaria.projectCode = "P001";
        resourceDTOMaria.email = "email1@email.com";

        ResourceDTO resourceDTOAna = new ResourceDTO();
        resourceDTOAna.projectRole = "tm";
        resourceDTOAna.projectCode = "P001";
        resourceDTOAna.email = "email2@email.com";

        List<ResourceDTO> resourceDTOList = Arrays.asList(resourceDTOMaria, resourceDTOAna);

        assemblerDouble.when(() -> ResourcesInProjectAssembler.resourceInProjectList(any())).thenReturn(resourceDTOList);

        List<ResourceDTO> result = resourceService.createHumanResourcesInProjectList(projectCode, currentDate);

        assertEquals(resourceDTOList, result);
        assemblerDouble.close();
    }

    @Test
    void createProjectListByResource_emptyEmailAndNullDate() {
        String expected = "Non valid data";
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                resourceService.createProjectListByResource("mail@mail.com",
                        null));

        assertEquals(expected, e.getMessage());
    }

    @Test
    void findResourceByID_ResourceExists_Equals() {
        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
        "john@john.com",
        "P1",
        "TEAM_MEMBER",
        "2022-01-01",
        "2022-02-02",
        50,
        2.5);

        ResourceID searchID = resourceList.get(0).identity();

        when(resourceRepositoryFake.findById(searchID)).thenReturn(Optional.of(resourceList.get(0)));
        outputResourceAssembler.when(() ->
                OutputResourceAssembler.generateOutputResourceDTO(resourceList.get(0))).thenReturn(outputResourceDTO);

        OutputResourceDTO result = resourceService.findResourceByID(searchID);

        assertEquals(outputResourceDTO,result);
    }

    @Test
    void findResourceByID_ResourceDoesNotExist_ThrowsException() {
        ResourceID searchID = resourceList.get(0).identity();

        when(resourceRepositoryFake.findById(searchID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                resourceService.findResourceByID(searchID));
        assertEquals("ResourceID does not exist.", exception.getMessage());

    }

}
