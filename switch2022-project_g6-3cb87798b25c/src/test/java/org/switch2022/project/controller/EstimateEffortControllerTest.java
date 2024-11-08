package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactory;
import org.switch2022.project.domain.userstory.UserStoryFactoryImplementation;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.UserStoryRepositoryFake;
import org.switch2022.project.service.UserStoryService;
import org.switch2022.project.utils.dto.NewUserStoryDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EstimateEffortControllerTest {
        @Autowired
        private UserStoryRepositoryFake repository;
        @Autowired
        private EstimateEffortController controller;
        private UserStoryFactory userStoryFactory;

        @BeforeEach
        void init() {
            repository = new UserStoryRepositoryFake();

            userStoryFactory = new UserStoryFactoryImplementation();

            NewUserStoryDTO newUserStoryDTO = new NewUserStoryDTO();
            ProjectCode projectCode = new ProjectCode("P1");
            UserStoryCode usCode = new UserStoryCode("US01");
            Description description = new Description("description");

            UserStory userStoryToSave = userStoryFactory.createUserStory(new UserStoryID(projectCode, usCode),
                    description);
            repository.save(userStoryToSave);
        }

        @AfterEach
        void tearDown() {
            repository.deleteAll();
        }


        @Test
        void constructor_nullUserStoryService_throwException() {
            Exception e = assertThrows(IllegalArgumentException.class, () ->
                    new EstimateEffortController(null));
            assertEquals("User Story Service cannot be null.", e.getMessage());
        }

        @Test
        public void testConstructorWithValidService() {
            UserStoryService userStoryService = mock(UserStoryService.class);
            EstimateEffortController controller = new EstimateEffortController(userStoryService);

            assertNotNull(controller);
        }

        @Test
        void estimateEffort_True() {
            boolean expected = true;
            boolean result = controller.estimateEffort("P1", "US01", org.switch2022.project.domain.valueobject.Effort.EIGHT);

            assertEquals(expected, result);

        }

        @Test
        void estimateEffort_userStoryNotFound_False() {
            boolean expected = false;
            boolean result = controller.estimateEffort("P1", "US03", org.switch2022.project.domain.valueobject.Effort.EIGHT);

            assertEquals(expected, result);
        }

        @Test
        public void testEstimateEffort_Success() {
            String projectCode = "P1";
            String userStoryCode = "US01";
            Effort effortValue = Effort.THREE;

            UserStoryID userStoryID = new UserStoryID(new ProjectCode(projectCode), new UserStoryCode(userStoryCode));

            boolean result = controller.estimateEffort(projectCode, userStoryCode, effortValue);

            assertTrue(result);
        }
}