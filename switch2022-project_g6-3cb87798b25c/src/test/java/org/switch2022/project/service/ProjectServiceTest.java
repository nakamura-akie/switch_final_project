package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.ProjectRepositoryPersistence;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
    @MockBean
    private Repository<Project, ProjectCode> projectRepository;
    @MockBean
    private UserStoryRepository userStoryRepository;
    @MockBean
    private Repository<Customer, TaxIdentificationNumber> customerRepository;
    @MockBean
    private Repository<BusinessSector, BusinessSectorName> businessSectorRepository;
    @MockBean
    private Repository<ProjectTypology, ProjectTypologyName> projectTypologyRepository;
    @MockBean
    private ProjectFactory projectFactory;
    private NewProjectDTO newProjectDTO;
    private OutputProjectDTO outputProjectDTODouble;
    private MockedStatic<ProjectMapper> projectMapperDouble;
    private MockedStatic<OutputProjectAssembler> createdProjectAssemblerDouble;
    private MockedStatic<CreateProjectListAssembler> createProjectListAssembler;
    private MockedStatic<UserStoryAssembler> outputUserStoryAssembler;

    @BeforeEach
    void init() {
        Project projectDouble = mock(Project.class);

        String projectCode = "P001";
        String projectName = "Project One";
        String description = "Project description";
        String customerID = "235140236";
        String businessSectorName = "Technology";
        String projectTypologyName = "Fixed-Cost";

        this.newProjectDTO = new NewProjectDTO(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                null,
                null,
                null,
                null,
                null
        );

        this.outputProjectDTODouble = mock(OutputProjectDTO.class);
        this.projectMapperDouble = mockStatic(ProjectMapper.class);
        this.createdProjectAssemblerDouble = mockStatic(OutputProjectAssembler.class);

        this.outputUserStoryAssembler = mockStatic(UserStoryAssembler.class);

        when(this.projectRepository.existsById(new ProjectCode(projectCode))).thenReturn(false);

        when(this.customerRepository.existsById(new TaxIdentificationNumberPortugalImplementation(customerID))).thenReturn(true);

        when(this.businessSectorRepository.existsById(new BusinessSectorName(businessSectorName))).thenReturn(true);

        when(this.projectTypologyRepository.existsById(new ProjectTypologyName(projectTypologyName))).thenReturn(true);

        this.projectMapperDouble.when(() -> ProjectMapper.createDomainObject(this.projectFactory, newProjectDTO))
                .thenReturn(projectDouble);

        when(this.projectRepository.save(projectDouble)).thenReturn(projectDouble);
        this.createdProjectAssemblerDouble.when(() -> OutputProjectAssembler.generateDTO(projectDouble))
                .thenReturn(this.outputProjectDTODouble);
    }

    @AfterEach
    void tearDown() {
        this.projectService = null;
        this.projectRepository = null;
        this.userStoryRepository = null;
        this.customerRepository = null;
        this.businessSectorRepository = null;
        this.projectTypologyRepository = null;
        this.newProjectDTO = null;
        this.outputProjectDTODouble = null;
        this.createProjectListAssembler = null;
        this.createdProjectAssemblerDouble.close();
        this.createdProjectAssemblerDouble = null;
        this.projectMapperDouble.close();
        this.projectMapperDouble = null;
        this.outputUserStoryAssembler.close();
        this.outputUserStoryAssembler = null;
    }

    @Test
    void constructor_NullProjectRepository_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectService(null, this.userStoryRepository, this.customerRepository,
                        this.businessSectorRepository, this.projectTypologyRepository, this.projectFactory));
        assertEquals("Project Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_NullUserStoryRepository_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectService(this.projectRepository, null, this.customerRepository,
                        this.businessSectorRepository, this.projectTypologyRepository, this.projectFactory));
        assertEquals("User Story Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_NullCustomerRepository_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectService(this.projectRepository, this.userStoryRepository, null,
                        this.businessSectorRepository, this.projectTypologyRepository, this.projectFactory));
        assertEquals("Customer Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_NullBusinessSectorRepository_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectService(this.projectRepository, this.userStoryRepository, this.customerRepository,
                        null, this.projectTypologyRepository, this.projectFactory));
        assertEquals("Business Sector Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_NullProjectTypologyRepository_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectService(this.projectRepository, this.userStoryRepository, this.customerRepository,
                        this.businessSectorRepository, null, this.projectFactory));
        assertEquals("Project Typology Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_NullProjectFactory_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectService(this.projectRepository, this.userStoryRepository, this.customerRepository,
                        this.businessSectorRepository, this.projectTypologyRepository, null));
        assertEquals("Project Factory cannot be null", exception.getMessage());
    }

    @Test
    void createUserStoryList_ProjectCodeDoNotExist_ReturnsEmptyList() {
        when(this.projectRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                projectService.createUserStoryList(new ProjectCode("nonExistentProjectCode")));
        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void createUserStoryList_ProjectCodeExists_ReturnsList() {
        Project projectDouble = mock(Project.class);
        ProjectCode projectCodeDouble = mock(ProjectCode.class);
        UserStoryStatus statusDouble = mock(UserStoryStatus.class);
        UserStory userStoryDouble = mock(UserStory.class);
        UserStoryID userStoryIDDouble = mock(UserStoryID.class);
        UserStoryDTO userStoryDTODouble = mock(UserStoryDTO.class);

        List<UserStory> userStoryList = new ArrayList<>();
        userStoryList.add(userStoryDouble);

        List<UserStoryID> userStoryCodeList = new ArrayList<>();
        userStoryCodeList.add(userStoryIDDouble);

        List<UserStoryDTO> userStoryDTOList = new ArrayList<>();
        userStoryDTOList.add(userStoryDTODouble);

        List<UserStoryDTO> expected = new ArrayList<>();
        expected.add(userStoryDTODouble);

        when(this.projectRepository.findById(any())).thenReturn(Optional.of(projectDouble));
        when(userStoryRepository.findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot
                (projectCodeDouble, statusDouble, statusDouble))
                .thenReturn(userStoryList);

        when(projectDouble.getUserStoryPriorityList()).thenReturn(userStoryCodeList);
        when(userStoryDouble.identity()).thenReturn(userStoryIDDouble);

        this.outputUserStoryAssembler.when(() -> UserStoryAssembler.createUserStoryDTOList(any()))
                .thenReturn(userStoryDTOList);

        List<UserStoryDTO> result = projectService.createUserStoryList(projectCodeDouble);

        assertEquals(expected, result);
    }

    @Test
    void createProject_ProjectCodeAlreadyExists_ThrowsException() {
        when(this.projectRepository.existsById(newProjectDTO.getProjectCode())).thenReturn(true);
        when(this.customerRepository.existsById(newProjectDTO.getCustomerID())).thenReturn(true);
        when(this.businessSectorRepository.existsById(newProjectDTO.getBusinessSectorName())).thenReturn(true);
        when(this.projectTypologyRepository.existsById(newProjectDTO.getProjectTypologyName())).thenReturn(true);

        Exception exception = assertThrows(DataValidationException.class,
                () -> this.projectService.createProject(this.newProjectDTO));

        assertEquals("Project already exists", exception.getMessage());
    }

    @Test
    void createProject_CustomerDoesntExist_ThrowsException() {
        when(this.projectRepository.existsById(newProjectDTO.getProjectCode())).thenReturn(false);
        when(this.customerRepository.existsById(newProjectDTO.getCustomerID())).thenReturn(false);
        when(this.businessSectorRepository.existsById(newProjectDTO.getBusinessSectorName())).thenReturn(true);
        when(this.projectTypologyRepository.existsById(newProjectDTO.getProjectTypologyName())).thenReturn(true);

        Exception exception = assertThrows(DataValidationException.class,
                () -> this.projectService.createProject(this.newProjectDTO));

        assertEquals("Customer doesn't exist", exception.getMessage());
    }

    @Test
    void createProject_BusinessSectorDoesntExist_ThrowsException() {
        when(this.projectRepository.existsById(newProjectDTO.getProjectCode())).thenReturn(false);
        when(this.customerRepository.existsById(newProjectDTO.getCustomerID())).thenReturn(true);
        when(this.businessSectorRepository.existsById(newProjectDTO.getBusinessSectorName())).thenReturn(false);
        when(this.projectTypologyRepository.existsById(newProjectDTO.getProjectTypologyName())).thenReturn(true);

        Exception exception = assertThrows(DataValidationException.class,
                () -> this.projectService.createProject(this.newProjectDTO));

        assertEquals("Business Sector doesn't exist", exception.getMessage());
    }

    @Test
    void createProject_ProjectTypologyDoesntExist_ThrowsException() {
        when(this.projectRepository.existsById(newProjectDTO.getProjectCode())).thenReturn(false);
        when(this.customerRepository.existsById(newProjectDTO.getCustomerID())).thenReturn(true);
        when(this.businessSectorRepository.existsById(newProjectDTO.getBusinessSectorName())).thenReturn(true);
        when(this.projectTypologyRepository.existsById(newProjectDTO.getProjectTypologyName())).thenReturn(false);

        Exception exception = assertThrows(DataValidationException.class,
                () -> this.projectService.createProject(this.newProjectDTO));
        ;
        assertEquals("Project Typology doesn't exist", exception.getMessage());
    }

    @Test
    void createProject_ValidDataForProjectCreation_OutputProjectDTO() {
        OutputProjectDTO expected = this.outputProjectDTODouble;

        OutputProjectDTO result = this.projectService.createProject(this.newProjectDTO);

        assertEquals(expected, result);
    }

    @Test
    void createProjectList_ReturnsListOfProjects_Equals() {

        this.createProjectListAssembler = mockStatic(CreateProjectListAssembler.class);

        ProjectCode projectCodeOne = new ProjectCode("P001");

        Project projectOne = mock(Project.class);
        when(projectOne.identity()).thenReturn(projectCodeOne);


        ProjectDTO projectDTOne = new ProjectDTO();
        projectDTOne.projectCode = "P001";
        projectDTOne.projectName = "one";
        projectDTOne.projectStatus = "PLANNED";
        projectDTOne.projectCustomer = "C01";

        ProjectRepositoryPersistence projectRepositoryPersistence = mock(ProjectRepositoryPersistence.class);

        List<Project> projectListOne = new ArrayList<>();
        projectListOne.add(projectOne);

        List<ProjectDTO> projectDTOList = new ArrayList<>();
        projectDTOList.add(projectDTOne);


        when(projectRepositoryPersistence.findAll()).thenReturn(projectListOne);
        this.createProjectListAssembler.when(() -> CreateProjectListAssembler.createProjectList(any()))
                .thenReturn(projectDTOList);

        List<ProjectDTO> result = projectService.createProjectList();

        assertEquals(projectDTOList, result);
        createProjectListAssembler.close();
    }

    @Test
    void createProjectList_ReturnsEmptyList_Equals() {

        this.createProjectListAssembler = mockStatic(CreateProjectListAssembler.class);

        ProjectRepositoryPersistence projectRepositoryPersistence = mock(ProjectRepositoryPersistence.class);

        List<Project> emptyList = new ArrayList<>();
        List<ProjectDTO> emptyDTOList = new ArrayList<>();

        when(projectRepositoryPersistence.findAll()).thenReturn(emptyList);
        this.createProjectListAssembler.when(() -> CreateProjectListAssembler.createProjectList(emptyList))
                .thenReturn(emptyDTOList);

        List<ProjectDTO> result = projectService.createProjectList();

        assertEquals(emptyDTOList, result);
        createProjectListAssembler.close();
    }

    @Test
    void findProjectById_ProjectDoesNotExist_ThrowsException() {
        String expectedErrorMessage = "Project does not exist.";
        when(this.projectRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> this.projectService.findProjectById(new ProjectCode("PROJECT ONE")));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void findProjectById_ProjectExist_True() {
        Project projectOne = mock(Project.class);

        when(this.projectRepository.findById(any())).thenReturn(Optional.of(projectOne));

        this.createdProjectAssemblerDouble.when(() -> OutputProjectAssembler.generateDTO(projectOne))
                .thenReturn(this.outputProjectDTODouble);

        OutputProjectDTO result = projectService.findProjectById(new ProjectCode("PROJECT ONE"));

        assertEquals(outputProjectDTODouble, result);
    }

    @Test
    void changeProjectStatus_ExistingProject() {
        Project projectOne = mock(Project.class);
        ProjectCode projectCode = mock(ProjectCode.class);
        ProjectStatus projectStatus = mock(ProjectStatus.class);

        when(this.projectRepository.findById(projectCode)).thenReturn(Optional.of(projectOne));

        this.projectService.changeProjectStatus(projectCode, projectStatus);

        verify(projectOne).changeStatus(projectStatus);
        verify(projectRepository).save(projectOne);
    }

    @Test
    void changeProjectStatus_NonExistentProject_Exception() {
        ProjectCode projectCode = mock(ProjectCode.class);
        ProjectStatus projectStatus = mock(ProjectStatus.class);

        when(this.projectRepository.findById(projectCode)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                this.projectService.changeProjectStatus(projectCode, projectStatus));
        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void addUserStoryToProductBacklog_ProjectExists() {
        Project projectDouble = mock(Project.class);
        UserStoryID userStoryIdDouble = mock(UserStoryID.class);
        ProjectCode projectCodeDouble = mock(ProjectCode.class);
        when(userStoryIdDouble.getProjectCode()).thenReturn(projectCodeDouble);
        when(projectRepository.findById(any())).thenReturn(Optional.of(projectDouble));

        this.projectService.addUserStoryToProductBacklog(userStoryIdDouble);

        verify(projectDouble).addUserStoryToProductBacklog(userStoryIdDouble);
        verify(projectRepository).save(projectDouble);
    }

    @Test
    void addUserStoryToProductBacklog_ProjectDoesntExists_ThrowsException() {
        UserStoryID userStoryIdDouble = mock(UserStoryID.class);
        ProjectCode projectCodeDouble = mock(ProjectCode.class);
        when(userStoryIdDouble.getProjectCode()).thenReturn(projectCodeDouble);
        when(projectRepository.findById(projectCodeDouble)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidOperationException.class,
                () -> this.projectService.addUserStoryToProductBacklog(userStoryIdDouble));

        String expected = "Project couldn't be found when trying to add User Story to the Product Backlog";

        assertEquals(expected, exception.getMessage());
    }

    @Test
    void removeUserStoryToProductBacklog() {
        Project projectDouble = mock(Project.class);
        UserStoryID userStoryIdDouble = mock(UserStoryID.class);
        ProjectCode projectCodeDouble = mock(ProjectCode.class);
        when(userStoryIdDouble.getProjectCode()).thenReturn(projectCodeDouble);
        when(projectRepository.findById(any())).thenReturn(Optional.of(projectDouble));

        this.projectService.removeUserStoryFromProductBacklog(userStoryIdDouble);

        verify(projectDouble).removeUserStoryFromProductBacklog(userStoryIdDouble);
        verify(projectRepository).save(projectDouble);
    }

    @Test
    void removeUserStoryFromProductBacklog_ProjectDoesntExists_ThrowsException() {
        UserStoryID userStoryIdDouble = mock(UserStoryID.class);
        ProjectCode projectCodeDouble = mock(ProjectCode.class);
        when(userStoryIdDouble.getProjectCode()).thenReturn(projectCodeDouble);
        when(projectRepository.findById(projectCodeDouble)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidOperationException.class,
                () -> this.projectService.removeUserStoryFromProductBacklog(userStoryIdDouble));

        String expected = "Project couldn't be found when trying to remove User Story from the Product Backlog";

        assertEquals(expected, exception.getMessage());
    }

    @Test
    void importProjectStatusHistory_ExistingProject() {
        Project projectOne = mock(Project.class);
        ProjectCode projectCode = mock(ProjectCode.class);
        Map projectStatus = mock(Map.class);

        when(this.projectRepository.findById(projectCode)).thenReturn(Optional.of(projectOne));

        this.projectService.importProjectStatusHistory(projectCode, projectStatus);

        verify(projectOne).importStatusHistory(projectStatus);
        verify(projectRepository).save(projectOne);
    }

    @Test
    void importProjectStatusHistory_NonExistentProject_Exception() {
        Project projectOne = mock(Project.class);
        ProjectCode projectCode = mock(ProjectCode.class);
        Map projectStatus = mock(Map.class);

        when(this.projectRepository.findById(projectCode)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                this.projectService.importProjectStatusHistory(projectCode, projectStatus));
        assertEquals("Project does not exist.", exception.getMessage());
    }


}
