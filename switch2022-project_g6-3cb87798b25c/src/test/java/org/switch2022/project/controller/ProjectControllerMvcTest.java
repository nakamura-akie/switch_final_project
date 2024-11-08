package org.switch2022.project.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.jpa.BusinessSectorRepositoryJPA;
import org.switch2022.project.repository.jpa.CustomerRepositoryJPA;
import org.switch2022.project.repository.jpa.ProjectTypologyRepositoryJPA;
import org.switch2022.project.service.BusinessSectorService;
import org.switch2022.project.service.CustomerService;
import org.switch2022.project.service.ProjectTypologyService;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.dto.NewBusinessSectorDTO;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integration_test")
class ProjectControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CustomerService customerService;
    @Autowired
    BusinessSectorService businessSectorService;
    @Autowired
    ProjectTypologyService projectTypologyService;
    @Autowired
    CustomerRepositoryJPA customerRepositoryJPA;
    @Autowired
    BusinessSectorRepositoryJPA businessSectorRepositoryJPA;
    @Autowired
    ProjectTypologyRepositoryJPA projectTypologyRepositoryJPA;

    @BeforeEach
    void setUp() {
        CustomerValueObjectDTO customerOneDTO = new CustomerValueObjectDTO();
        customerOneDTO.customerName = new CustomerName("XPTO, SA");
        customerOneDTO.country = new Country("portugal");
        customerOneDTO.taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("211267490");
        customerService.createCustomer(customerOneDTO);

        projectTypologyService.createDefaultProjectTypology("Time and Materials");

        NewBusinessSectorDTO businessSectorDTOOne = new NewBusinessSectorDTO();
        businessSectorDTOOne.businessSectorName = new BusinessSectorName("Technology");
        businessSectorService.createBusinessSector(businessSectorDTOOne);
    }

    @AfterEach
    void tearDown() {
        customerRepositoryJPA.deleteAll();
        businessSectorRepositoryJPA.deleteAll();
        projectTypologyRepositoryJPA.deleteAll();
    }

    @Test
    void createProject_ProjectDoesntExist_CompleteProjectData_ProjectAndCreated() throws Exception {
        String projectCode = UUID.randomUUID().toString();
        String projectName = "Project name";
        String description = "Description";
        String customerID = "211267490";
        String businessSectorName = "Technology";
        String projectTypologyName = "Time and Materials";
        Integer sprintDuration = 1;
        Integer numberPlannedSprints = 10;
        Double budget = 1000.5;
        String startDate = "2023-05-24";
        String endDate = "2023-12-24";

        String urlToProjectResource = String.format("/projects/%s", projectCode);

        String requestBody = new JSONObject()
                .put("projectCode", projectCode)
                .put("projectName", projectName)
                .put("description", description)
                .put("customerID", customerID)
                .put("businessSectorName", businessSectorName)
                .put("projectTypologyName", projectTypologyName)
                .put("sprintDuration", sprintDuration)
                .put("numberPlannedSprints", numberPlannedSprints)
                .put("budget", budget)
                .put("startDate", startDate)
                .put("endDate", endDate)
                .toString();

        // First call: GET /projects/{projectCode} - Verify that Project doesn't exist
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Project does not exist.")));


        // Second call: POST /projects - Create a Project
        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.projectCode", is(projectCode)))
                .andExpect(jsonPath("$.projectName", is(projectName)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.customerID", is(customerID)))
                .andExpect(jsonPath("$.businessSectorName", is(businessSectorName)))
                .andExpect(jsonPath("$.projectTypologyName", is(projectTypologyName)))
                .andExpect(jsonPath("$.productBacklog", is(empty())))
                .andExpect(jsonPath("$.status", is("PLANNED")))
                .andExpect(jsonPath("$.statusHistory.PLANNED", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.sprintDuration", is(sprintDuration)))
                .andExpect(jsonPath("$.numberPlannedSprints", is(numberPlannedSprints)))
                .andExpect(jsonPath("$.budget", is(budget)))
                .andExpect(jsonPath("$.startDate", is(startDate)))
                .andExpect(jsonPath("$.endDate", is(endDate)))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/projects/" + projectCode)))
                .andExpect(jsonPath("$._links.projects.href", is("http://localhost/projects")));

        // Third call: GET /projects/{projectCode} - Verify that project was created
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.projectCode", is(projectCode)))
                .andExpect(jsonPath("$.projectName", is(projectName)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.customerID", is(customerID)))
                .andExpect(jsonPath("$.businessSectorName", is(businessSectorName)))
                .andExpect(jsonPath("$.projectTypologyName", is(projectTypologyName)))
                .andExpect(jsonPath("$.productBacklog", is(empty())))
                .andExpect(jsonPath("$.status", is("PLANNED")))
                .andExpect(jsonPath("$.statusHistory.PLANNED", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.sprintDuration", is(sprintDuration)))
                .andExpect(jsonPath("$.numberPlannedSprints", is(numberPlannedSprints)))
                .andExpect(jsonPath("$.budget", is(budget)))
                .andExpect(jsonPath("$.startDate", is(startDate)))
                .andExpect(jsonPath("$.endDate", is(endDate)))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/projects/" + projectCode)))
                .andExpect(jsonPath("$._links.projects.href", is("http://localhost/projects")));
    }

    @Test
    void createProject_ProjectDoesntExist_OnlyRequiredProjectData_ProjectAndCreated() throws Exception {
        String projectCode = UUID.randomUUID().toString();
        String projectName = "Project name";
        String description = "Description";
        String customerID = "211267490";
        String businessSectorName = "Technology";
        String projectTypologyName = "Time and Materials";

        String urlToProjectResource = String.format("/projects/%s", projectCode);

        String requestBody = new JSONObject()
                .put("projectCode", projectCode)
                .put("projectName", projectName)
                .put("description", description)
                .put("customerID", customerID)
                .put("businessSectorName", businessSectorName)
                .put("projectTypologyName", projectTypologyName)
                .toString();

        // First call: GET /projects/{projectCode} - Verify that Project doesn't exist
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Project does not exist.")));


        // Second call: POST /projects - Create a Project
        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.projectCode", is(projectCode)))
                .andExpect(jsonPath("$.projectName", is(projectName)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.customerID", is(customerID)))
                .andExpect(jsonPath("$.businessSectorName", is(businessSectorName)))
                .andExpect(jsonPath("$.projectTypologyName", is(projectTypologyName)))
                .andExpect(jsonPath("$.productBacklog", is(empty())))
                .andExpect(jsonPath("$.status", is("PLANNED")))
                .andExpect(jsonPath("$.statusHistory.PLANNED", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.sprintDuration", nullValue()))
                .andExpect(jsonPath("$.numberPlannedSprints",nullValue()))
                .andExpect(jsonPath("$.budget", nullValue()))
                .andExpect(jsonPath("$.startDate", nullValue()))
                .andExpect(jsonPath("$.endDate", nullValue()))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/projects/" + projectCode)))
                .andExpect(jsonPath("$._links.projects.href", is("http://localhost/projects")));

        // Third call: GET /projects/{projectCode} - Verify that project was created
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.projectCode", is(projectCode)))
                .andExpect(jsonPath("$.projectName", is(projectName)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.customerID", is(customerID)))
                .andExpect(jsonPath("$.businessSectorName", is(businessSectorName)))
                .andExpect(jsonPath("$.projectTypologyName", is(projectTypologyName)))
                .andExpect(jsonPath("$.productBacklog", is(empty())))
                .andExpect(jsonPath("$.status", is("PLANNED")))
                .andExpect(jsonPath("$.statusHistory.PLANNED", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.sprintDuration", nullValue()))
                .andExpect(jsonPath("$.numberPlannedSprints",nullValue()))
                .andExpect(jsonPath("$.budget", nullValue()))
                .andExpect(jsonPath("$.startDate", nullValue()))
                .andExpect(jsonPath("$.endDate", nullValue()))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/projects/" + projectCode)))
                .andExpect(jsonPath("$._links.projects.href", is("http://localhost/projects")));
    }

    @Test
    void createProject_ProjectDoesntExist_InsufficientProjectData_ProjectAndCreated() throws Exception {
        String projectCode = UUID.randomUUID().toString();
        String description = "Description";
        String customerID = "211267490";
        String businessSectorName = "Technology";
        String projectTypologyName = "Time and Materials";

        String urlToProjectResource = String.format("/projects/%s", projectCode);

        String requestBody = new JSONObject()
                .put("projectCode", projectCode)
                .put("description", description)
                .put("customerID", customerID)
                .put("businessSectorName", businessSectorName)
                .put("projectTypologyName", projectTypologyName)
                .toString();

        // First call: GET /projects/{projectCode} - Verify that Project doesn't exist
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Project does not exist.")));


        // Second call: POST /projects - Create a Project
        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Project Name cannot be null")));
    }

    @Test
    void createProject_ProjectAlreadyExist_ErrorMessageAndConflict() throws Exception {
        // Setup: Create a Project
        String projectCode = UUID.randomUUID().toString();
        String projectName = "Project name";
        String description = "Description";
        String customerID = "211267490";
        String businessSectorName = "Technology";
        String projectTypologyName = "Time and Materials";
        Integer sprintDuration = 1;
        Integer numberPlannedSprints = 10;
        Double budget = 1000.5;
        String startDate = "2023-05-24";
        String endDate = "2023-12-24";

        String urlToProjectResource = String.format("/projects/%s", projectCode);

        String requestBody = new JSONObject()
                .put("projectCode", projectCode)
                .put("projectName", projectName)
                .put("description", description)
                .put("customerID", customerID)
                .put("businessSectorName", businessSectorName)
                .put("projectTypologyName", projectTypologyName)
                .put("sprintDuration", sprintDuration)
                .put("numberPlannedSprints", numberPlannedSprints)
                .put("budget", budget)
                .put("startDate", startDate)
                .put("endDate", endDate)
                .toString();

        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // First call: GET /projects/{projectCode}- Verify that Project exist
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isOk());


        // Second call: POST /projects - Create a Project
        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Project already exists")));
    }

    @Test
    void createProject_CustomerDoesntExist_ErrorMessageAndConflict() throws Exception {
        String projectCode = UUID.randomUUID().toString();
        String projectName = "Project name";
        String description = "Description";
        String customerID = "242200680";
        String businessSectorName = "Technology";
        String projectTypologyName = "Time and Materials";
        Integer sprintDuration = 1;
        Integer numberPlannedSprints = 10;
        Double budget = 1000.5;
        String startDate = "2023-05-24";
        String endDate = "2023-12-24";

        String urlToProjectResource = String.format("/projects/%s", projectCode);

        String requestBody = new JSONObject()
                .put("projectCode", projectCode)
                .put("projectName", projectName)
                .put("description", description)
                .put("customerID", customerID)
                .put("businessSectorName", businessSectorName)
                .put("projectTypologyName", projectTypologyName)
                .put("sprintDuration", sprintDuration)
                .put("numberPlannedSprints", numberPlannedSprints)
                .put("budget", budget)
                .put("startDate", startDate)
                .put("endDate", endDate)
                .toString();

        // First call: GET /projects/{projectCode} - Verify that Project doesn't exist
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isNotFound());


        // Second call: POST /projects - Create a Project
        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Customer doesn't exist")));
    }

    @Test
    void createProject_BusinessSectorDoesntExist_ErrorMessageAndConflict() throws Exception {
        String projectCode = UUID.randomUUID().toString();
        String projectName = "Project name";
        String description = "Description";
        String customerID = "211267490";
        String businessSectorName = "Construction";
        String projectTypologyName = "Time and Materials";
        Integer sprintDuration = 1;
        Integer numberPlannedSprints = 10;
        Double budget = 1000.5;
        String startDate = "2023-05-24";
        String endDate = "2023-12-24";

        String urlToProjectResource = String.format("/projects/%s", projectCode);

        String requestBody = new JSONObject()
                .put("projectCode", projectCode)
                .put("projectName", projectName)
                .put("description", description)
                .put("customerID", customerID)
                .put("businessSectorName", businessSectorName)
                .put("projectTypologyName", projectTypologyName)
                .put("sprintDuration", sprintDuration)
                .put("numberPlannedSprints", numberPlannedSprints)
                .put("budget", budget)
                .put("startDate", startDate)
                .put("endDate", endDate)
                .toString();

        // First call: GET /projects/{projectsCode} - Verify that Project doesn't exist
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isNotFound());


        // Second call: POST /projects - Create a Project
        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Business Sector doesn't exist")));
    }

    @Test
    void createProject_ProjectTypologyDoesntExist_ErrorMessageAndConflict() throws Exception {
        String projectCode = UUID.randomUUID().toString();
        String projectName = "Project name";
        String description = "Description";
        String customerID = "211267490";
        String businessSectorName = "Technology";
        String projectTypologyName = "Nonexistent Project Typology";
        Integer sprintDuration = 1;
        Integer numberPlannedSprints = 10;
        Double budget = 1000.5;
        String startDate = "2023-05-24";
        String endDate = "2023-12-24";

        String urlToProjectResource = String.format("/projects/%s", projectCode);

        String requestBody = new JSONObject()
                .put("projectCode", projectCode)
                .put("projectName", projectName)
                .put("description", description)
                .put("customerID", customerID)
                .put("businessSectorName", businessSectorName)
                .put("projectTypologyName", projectTypologyName)
                .put("sprintDuration", sprintDuration)
                .put("numberPlannedSprints", numberPlannedSprints)
                .put("budget", budget)
                .put("startDate", startDate)
                .put("endDate", endDate)
                .toString();

        // First call: GET /projects/{projectsCode} - Verify that Project doesn't exist
        this.mockMvc.perform(get(urlToProjectResource))
                .andExpect(status().isNotFound());

        // Second call: POST /projects - Create a Project
        this.mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Project Typology doesn't exist")));
    }
}