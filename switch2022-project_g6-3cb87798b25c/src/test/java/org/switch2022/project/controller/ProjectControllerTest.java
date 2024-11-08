package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.businesssector.BusinessSectorFactory;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.projecttypology.ProjectTypologyFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.service.BusinessSectorService;
import org.switch2022.project.service.CustomerService;
import org.switch2022.project.service.ProjectService;
import org.switch2022.project.service.ProjectTypologyService;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.dto.NewProjectDTO;
import org.switch2022.project.utils.dto.OutputProjectDTO;
import org.switch2022.project.utils.dto.ProjectDTO;
import org.switch2022.project.utils.exception.DataValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@ActiveProfiles("test")
class ProjectControllerTest {

    @Autowired
    ProjectController projectController;
    @Autowired
    ProjectService projectService;
    @Autowired
    CustomerService customerService;
    @Autowired
    BusinessSectorService businessSectorService;
    @Autowired
    Repository<Project, ProjectCode> projectRepository;
    @Autowired
    Repository<BusinessSector, BusinessSectorName> businessSectorRepository;
    @Autowired
    Repository<Customer, TaxIdentificationNumber> customerRepository;
    @Autowired
    ProjectTypologyService projectTypologyService;
    @Autowired
    Repository<ProjectTypology, ProjectTypologyName> projectTypologyRepository;
    @Autowired
    BusinessSectorFactory businessSectorFactory;
    @Autowired
    ProjectTypologyFactory projectTypologyFactory;
    NewProjectDTO newProjectDTO;
    OutputProjectDTO outputProjectDTO;

    @BeforeEach
    void init() {
        CustomerValueObjectDTO newCustomerDTO = new CustomerValueObjectDTO();
        newCustomerDTO.taxIdentificationNumber = new TaxIdentificationNumberPortugalImplementation("229846777");
        newCustomerDTO.customerName = new CustomerName("Customer Name");
        newCustomerDTO.country = new Country("portugal");
        this.customerService.createCustomer(newCustomerDTO);

        BusinessSectorName businessSectorName = new BusinessSectorName("Technology");
        BusinessSector newBusinessSector = businessSectorFactory.createBusinessSector(businessSectorName);

        businessSectorRepository.save(newBusinessSector);

        ProjectTypologyName newProjectTypologyName = new ProjectTypologyName("Time And Materials");
        ProjectTypology newProjectTypology = projectTypologyFactory.createProjectTypology(newProjectTypologyName);

        ProjectTypologyName newProjectTypologyNameTwo = new ProjectTypologyName("Construction");
        ProjectTypology newProjectTypologyTwo = projectTypologyFactory.createProjectTypology(newProjectTypologyNameTwo);

        projectTypologyRepository.save(newProjectTypology);
        projectTypologyRepository.save(newProjectTypologyTwo);


        NewProjectDTO projectDTO = new NewProjectDTO(
                "P001",
                "Project One",
                "Project Description",
                "229846777",
                businessSectorName.getBusinessSectorNameValue(),
                newProjectTypologyName.getProjectTypologyNameValue(),
                null,
                null,
                null,
                null,
                null
        );
        this.projectService.createProject(projectDTO);

        this.newProjectDTO = new NewProjectDTO(
                "P002",
                "Project Two",
                "Project Description",
                "229846777",
                businessSectorName.getBusinessSectorNameValue(),
                newProjectTypologyName.getProjectTypologyNameValue(),
                null,
                null,
                null,
                null,
                null
        );

        this.outputProjectDTO = new OutputProjectDTO(
                newProjectDTO.getProjectCode().getProjectCodeValue(),
                newProjectDTO.getProjectName().getProjectNameValue(),
                newProjectDTO.getDescription().getDescriptionValue(),
                newProjectDTO.getCustomerID().getTaxIdentificationNumber(),
                newProjectDTO.getBusinessSectorName().getBusinessSectorNameValue(),
                newProjectDTO.getProjectTypologyName().getProjectTypologyNameValue(),
                Collections.emptyList(),
                ProjectStatus.PLANNED.toString(),
                Map.of("PLANNED", LocalDate.now().toString()),
                null,
                null,
                null,
                null,
                null
        );
    }

    @AfterEach
    void tearDown() {
        this.customerRepository.deleteAll();
        this.businessSectorRepository.deleteAll();
        this.projectRepository.deleteAll();
        this.projectTypologyRepository.deleteAll();
    }

    @Test
    void constructor_NullProjectService_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectController(null));
        assertEquals("Project Service cannot be null", exception.getMessage());
    }

    @Test
    void createProject_ProjectCodeAlreadyExists_False() {
        String existentProjectCode = "P001";
        newProjectDTO.setProjectCode(new ProjectCode(existentProjectCode));

        Exception exception = assertThrows(DataValidationException.class, () ->
                projectController.createProject(newProjectDTO));
        assertEquals("Project already exists", exception.getMessage());

    }

    @Test
    void createProject_CustomerIDDoesntExist_False() {
        String nonExistentCustomerID = "299479498";
        newProjectDTO.setCustomerID(new TaxIdentificationNumberPortugalImplementation(nonExistentCustomerID));

        Exception exception = assertThrows(DataValidationException.class, () ->
                projectController.createProject(newProjectDTO));
        assertEquals("Customer doesn't exist", exception.getMessage());
    }

    @Test
    void createProject_BusinessSectorDoesntExist_False() {
        String nonexistentBusinessSector = "Construction";
        newProjectDTO.setBusinessSectorName(new BusinessSectorName(nonexistentBusinessSector));

        Exception exception = assertThrows(DataValidationException.class, () ->
                projectController.createProject(newProjectDTO));
        assertEquals("Business Sector doesn't exist", exception.getMessage());
    }

    @Test
    void createProject_ProjectTypologyDoesntExists_False() {
        String nonexistentProjectTypology = "Cost-plus";
        newProjectDTO.setProjectTypologyName(new ProjectTypologyName(nonexistentProjectTypology));

        Exception exception = assertThrows(DataValidationException.class, () ->
                projectController.createProject(newProjectDTO));
        assertEquals("Project Typology doesn't exist", exception.getMessage());
    }

    @Test
    void createProject_MinimumValidDataForProjectCreation_True() {
        EntityModel<OutputProjectDTO> entityModel = EntityModel.of(
                this.outputProjectDTO,
                linkTo(methodOn(ProjectController.class).findProjectById(newProjectDTO.getProjectCode())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).createProjectList()).withRel("projects")
        );

        ResponseEntity<?> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<?> result = projectController.createProject(newProjectDTO);

        assertEquals(expected, result);
    }

    @Test
    void createProject_CompleteValidDataForProjectCreation_True() {
        this.newProjectDTO.setSprintDuration(new SprintDuration(3));
        this.newProjectDTO.setNumberPlannedSprints(new NumberPlannedSprints(10));
        this.newProjectDTO.setPeriod(new TimePeriod(LocalDate.parse("2023-10-05"),
                LocalDate.parse("2025-10-05")));
        this.newProjectDTO.setBudget(new Budget(10000.0));

        this.outputProjectDTO.setSprintDuration(newProjectDTO.getSprintDuration().getSprintDurationValue());
        this.outputProjectDTO.setNumberPlannedSprints(
                newProjectDTO.getNumberPlannedSprints().getNumberOfPlannedSprintsValue()
        );
        this.outputProjectDTO.setBudget(newProjectDTO.getBudget().getBudgetValue());
        this.outputProjectDTO.setStartDate(newProjectDTO.getPeriod().getStartDate());
        this.outputProjectDTO.setEndDate(newProjectDTO.getPeriod().getEndDate());

        EntityModel<OutputProjectDTO> entityModel = EntityModel.of(
                this.outputProjectDTO,
                linkTo(methodOn(ProjectController.class).findProjectById(newProjectDTO.getProjectCode())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).createProjectList()).withRel("projects")
        );

        ResponseEntity<?> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<?> result = projectController.createProject(newProjectDTO);

        assertEquals(expected, result);
    }

    @Test
    void findProjectById_ExistingProject_True() {
        projectController.createProject(newProjectDTO);

        EntityModel<OutputProjectDTO> entityModel = EntityModel.of(
                this.outputProjectDTO,
                linkTo(methodOn(ProjectController.class).findProjectById(newProjectDTO.getProjectCode())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).createProjectList()).withRel("projects")
        );

        ResponseEntity<?> expected =
                ResponseEntity.ok(entityModel);

        ResponseEntity<?> result = projectController.findProjectById(newProjectDTO.getProjectCode());

        assertEquals(expected, result);
    }

    @Test
    void createProjectList_ListOfCreatedProjects_True() {
        List<ProjectDTO> projectList = projectService.createProjectList();

        List<EntityModel<ProjectDTO>> entityModelList = projectList.stream()
                .map(projectDTO -> EntityModel.of(
                        projectDTO,
                        linkTo(methodOn(ProjectController.class)
                                .findProjectById(new ProjectCode(projectDTO.projectCode))).withSelfRel(),
                        linkTo(methodOn(ProjectController.class).createProjectList())
                                .withRel("projects")))
                .collect(Collectors.toList());

        CollectionModel<?> expected =
                CollectionModel.of(
                        entityModelList,
                        linkTo(methodOn(ProjectController.class).createProjectList()).withRel(IanaLinkRelations.SELF));

        CollectionModel<?> result = projectController.createProjectList();

        assertEquals(expected, result);
    }

    @Test
    void createProjectList_EmptyProjectList_True() {
        List<ProjectDTO> projectList = new ArrayList<>();
        projectRepository.deleteAll();

        List<EntityModel<ProjectDTO>> entityModelList = projectList.stream()
                .map(projectDTO -> EntityModel.of(
                        projectDTO,
                        linkTo(methodOn(ProjectController.class)
                                .findProjectById(new ProjectCode(projectDTO.projectCode))).withSelfRel(),
                        linkTo(methodOn(ProjectController.class).createProjectList()).withRel("projects")))
                .collect(Collectors.toList());

        CollectionModel<?> expected =
                CollectionModel.of(
                        entityModelList,
                        linkTo(methodOn(ProjectController.class).createProjectList()).withRel(IanaLinkRelations.SELF));

        CollectionModel<?> result = projectController.createProjectList();

        assertEquals(expected, result);
    }
}