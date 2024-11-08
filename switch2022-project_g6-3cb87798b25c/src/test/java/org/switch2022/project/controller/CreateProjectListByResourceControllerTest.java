
package org.switch2022.project.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.ProjectFactoryImplementation;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.resource.ResourceFactory;
import org.switch2022.project.domain.resource.ResourceFactoryImplementation;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberInternationalImplementation;
import org.switch2022.project.repository.ProjectRepositoryFake;
import org.switch2022.project.repository.ResourceRepositoryFake;
import org.switch2022.project.service.ResourceService;
import org.switch2022.project.utils.dto.ProjectByResourceDTO;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateProjectListByResourceControllerTest {

    @Nested
    class IntegrationTest {

        @Autowired
        private CreateProjectListByResourceController controller;
        private Repository<Project, ProjectCode> projectRepository;
        private ProjectFactory projectFactory;
        private ResourceFactory resourceFactory;
        private Repository <Resource, ResourceID> resourceRepository;


        @BeforeEach
        void init() {
            projectRepository = new ProjectRepositoryFake();
            resourceFactory = new ResourceFactoryImplementation();
            projectFactory = new ProjectFactoryImplementation();
            resourceRepository = new ResourceRepositoryFake();

            String email = "exemple@example.com";
            ProjectCode projectCode = new ProjectCode("P1");
            ProjectName projectName = new ProjectName("Project Name");
            Description description = new Description("Description 1");
            TaxIdentificationNumberInternationalImplementation taxID = new
                    TaxIdentificationNumberInternationalImplementation("123456789");
            BusinessSectorName businessSectorName = new BusinessSectorName("TI");
            ProjectTypologyName projectTypologyName = new ProjectTypologyName("time and materials");
            TimePeriod timePeriod = new TimePeriod(LocalDate.of(2022, 1, 3),
                    LocalDate.of(2025, 1, 2));

            Email email1 = new Email(email);

            ResourceID resourceID = new ResourceID(email1, projectCode, timePeriod);

            ProjectRole projectRole = ProjectRole.TEAM_MEMBER;
            PercentageOfAllocation percentageOfAllocation = new PercentageOfAllocation((75));
            CostPerHour costPerHour = new CostPerHour(20.63);

            Resource resource1 = resourceFactory.createResource(resourceID, projectRole, percentageOfAllocation,
                    costPerHour);

            resourceRepository.save(resource1);

            Project project1 = projectFactory.createProject(projectCode, projectName, description, taxID,
                    businessSectorName, projectTypologyName);
            projectRepository.save(project1);
        }

        @AfterEach
        void tearDown() {
            projectRepository.deleteAll();
            resourceRepository.deleteAll();
        }

        @Test
        public void createProjectListByResource_withValidInformation() {
            String email = "exemple@example.com";
            LocalDate currentDate = LocalDate.now();

            ProjectByResourceDTO testDTO = new ProjectByResourceDTO();
            testDTO.projectDescription = "Description 1";
            testDTO.projectName = "Project Name";
            testDTO.projectCode = "P1";

            List<ProjectByResourceDTO> expected = new ArrayList<>();
            expected.add(testDTO);

            List<ProjectByResourceDTO> result = controller.createProjectListByResource(email, currentDate);

            assertEquals(expected, result);
        }
    }

    @Nested
    class UnitTest {

        @MockBean
        private ResourceService resourceService;

        @Test
        public void testCreateProjectListByResource_withValidEmailAndCurrentDate_returnListOfProjectByResourceDTO() {
            String email = "test@test.com";
            LocalDate currentDate = LocalDate.now();

            ResourceService resourceService = mock(ResourceService.class);
            when(resourceService.createProjectListByResource(email, currentDate))
                    .thenReturn(List.of(new ProjectByResourceDTO()));

            CreateProjectListByResourceController controller = new CreateProjectListByResourceController(resourceService);

            List<ProjectByResourceDTO> projectByResourceDTOs = controller.createProjectListByResource(email, currentDate);

            Assertions.assertEquals(1, projectByResourceDTOs.size());
        }

        @Test
        public void testCreateProjectListByResource_withInvalidEmailAndValidCurrentDate_returnEmptyList() {
            String email = "invalid_email";
            LocalDate currentDate = LocalDate.now();

            ResourceService resourceService = mock(ResourceService.class);
            when(resourceService.createProjectListByResource(email, currentDate))
                    .thenReturn(List.of());

            CreateProjectListByResourceController controller = new CreateProjectListByResourceController(resourceService);

            List<ProjectByResourceDTO> projectByResourceDTOs = controller.createProjectListByResource(email, currentDate);

            Assertions.assertEquals(0, projectByResourceDTOs.size());
        }

        @Test
        public void testCreateProjectListByResource_withValidEmailAndInvalidCurrentDate_returnEmptyList() {
            String email = "test@test.com";
            LocalDate currentDate = null;

            ResourceService resourceService = mock(ResourceService.class);
            when(resourceService.createProjectListByResource(email, currentDate))
                    .thenReturn(List.of());

            CreateProjectListByResourceController controller = new CreateProjectListByResourceController(resourceService);

            List<ProjectByResourceDTO> projectByResourceDTOs = controller.createProjectListByResource(email, currentDate);

            Assertions.assertEquals(0, projectByResourceDTOs.size());
        }

        @Test
        public void testCreateProjectListByResource_withNullResourceService_throwIllegalArgumentException() {

            String expected = "Resource Service cannot be null.";

            String result = assertThrows(IllegalArgumentException.class, () -> {
                new CreateProjectListByResourceController(null);
            }).getMessage();

            assertEquals(expected, result);
        }

        @Test
        public void testCreateProjectListByResource_withValidEmailAndDate_returnListOfProjectByResourceDTO() {
            // Arrange
            String email = "test@test.com";
            LocalDate currentDate = LocalDate.now();

            List<ProjectByResourceDTO> expectedProjectByResourceDTOs = List.of(new ProjectByResourceDTO());
            when(resourceService.createProjectListByResource(email, currentDate))
                    .thenReturn(expectedProjectByResourceDTOs);

            CreateProjectListByResourceController controller = new CreateProjectListByResourceController(resourceService);

            // Act
            List<ProjectByResourceDTO> actualProjectByResourceDTOs = controller.createProjectListByResource(email, currentDate);

            // Assert
            Assertions.assertEquals(expectedProjectByResourceDTOs, actualProjectByResourceDTOs);
        }
    }


}

