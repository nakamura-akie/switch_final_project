package org.switch2022.project.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.switch2022.project.datamodel.jpa.resource.ResourceIdJPA;
import org.switch2022.project.repository.jpa.ResourceRepositoryJPA;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ResourceControllerMvcTest {

    @Autowired
    ResourceRepositoryJPA resourceRepositoryJPA;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addResourceToProject_ResourceDoesntExist_Success() throws Exception {
        //First call: GET /{resourceEmail}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/666/2023-03-11/2023-03-12"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("ResourceID does not exist.")));

        //Second call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "js@mymail.com")
                .put("projectCode", "666")
                .put("startDate", "2023-03-11")
                .put("endDate", "2023-03-12")
                .put("projectRole", "TEAM_MEMBER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 50.5)
                .toString();


        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-11")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("TEAM_MEMBER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-11/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Third call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/666/2023-03-11/2023-03-12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-11")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("TEAM_MEMBER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-11/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));


        ResourceIdJPA resourceIdJPA = new ResourceIdJPA(
                "js@mymail.com",
                "666",
                "2023-03-11",
                "2023-03-12"
        );
        resourceRepositoryJPA.deleteById(resourceIdJPA);
    }

    @Test
    void addResourceToProject_ResourceAlreadyExistsInTimePeriod_ThrowsException() throws Exception {
        //First call: GET /{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/A1/2022-01-03/2022-07-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("A1")))
                .andExpect(jsonPath("$.startDate", is("2022-01-03")))
                .andExpect(jsonPath("$.endDate", is("2022-07-31")))
                .andExpect(jsonPath("$.projectRole", is("PRODUCT_OWNER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(20)))
                .andExpect(jsonPath("$.costPerHour", is(25.0)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/A1/2022-01-03/2022-07-31")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Second call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "js@mymail.com")
                .put("projectCode", "A1")
                .put("startDate", "2022-03-10")
                .put("endDate", "2022-03-12")
                .put("projectRole", "PRODUCT_OWNER")
                .put("percentageOfAllocation", 50)
                .put("costPerHour", 10.0)
                .toString();

        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("This resource is already exists in the given time period.")));

        //Third call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/A1/2022-03-10/2023-03-12"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ResourceID does not exist.")));
    }

    @Test
    void addResourceToProject_AnotherProductOwnerAlreadyExistsInTimePeriod_ThrowsException() throws Exception {
        //First call: GET /{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/A1/2022-01-03/2022-07-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("A1")))
                .andExpect(jsonPath("$.startDate", is("2022-01-03")))
                .andExpect(jsonPath("$.endDate", is("2022-07-31")))
                .andExpect(jsonPath("$.projectRole", is("PRODUCT_OWNER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(20)))
                .andExpect(jsonPath("$.costPerHour", is(25.0)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/A1/2022-01-03/2022-07-31")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Second call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "tg@mymail.com")
                .put("projectCode", "A1")
                .put("startDate", "2022-02-02")
                .put("endDate", "2022-03-03")
                .put("projectRole", "PRODUCT_OWNER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 10.0)
                .toString();

        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("PRODUCT_OWNER already exists in time period.")));

        //Third call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/tg@mymail.com/A1/2022-02-02/2022-03-03"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ResourceID does not exist.")));
    }

    @Test
    void addResourceToProject_AnotherScrumMasterAlreadyExistsInTimePeriod_ThrowsException() throws Exception {
        //First call: GET /{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/ms@mymail.com/A1/2022-01-04/2022-07-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("ms@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("A1")))
                .andExpect(jsonPath("$.startDate", is("2022-01-04")))
                .andExpect(jsonPath("$.endDate", is("2022-07-31")))
                .andExpect(jsonPath("$.projectRole", is("SCRUM_MASTER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(30)))
                .andExpect(jsonPath("$.costPerHour", is(25.0)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/ms%40mymail" +
                        ".com/A1/2022-01-04/2022-07-31")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Second call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "tg@mymail.com")
                .put("projectCode", "A1")
                .put("startDate", "2022-02-02")
                .put("endDate", "2022-03-03")
                .put("projectRole", "SCRUM_MASTER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 10.0)
                .toString();

        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("SCRUM_MASTER already exists in time period.")));

        //Third call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/tg@mymail.com/A1/2022-02-02/2022-03-03"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ResourceID does not exist.")));
    }

    @Test
    void addResourceToProject_AnotherProjectManagerAlreadyExistsInTimePeriod_ThrowsException() throws Exception {
        //First call: GET /{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/tc@mymail.com/A1/2022-02-01/2022-07-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("tc@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("A1")))
                .andExpect(jsonPath("$.startDate", is("2022-02-01")))
                .andExpect(jsonPath("$.endDate", is("2022-07-31")))
                .andExpect(jsonPath("$.projectRole", is("PROJECT_MANAGER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(20)))
                .andExpect(jsonPath("$.costPerHour", is(35.0)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/tc%40mymail" +
                        ".com/A1/2022-02-01/2022-07-31")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Second call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "tg@mymail.com")
                .put("projectCode", "A1")
                .put("startDate", "2022-02-02")
                .put("endDate", "2022-03-03")
                .put("projectRole", "PROJECT_MANAGER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 10.0)
                .toString();

        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("PROJECT_MANAGER already exists in time period.")));

        //Third call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/tg@mymail.com/A1/2022-02-02/2022-03-03"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ResourceID does not exist.")));
    }

    @Test
    void addResourceToProject_NotUserProfile_ThrowsException() throws Exception {
        String postRequestBody = new JSONObject()
                .put("email", "ze@mymail.com")
                .put("projectCode", "A1")
                .put("startDate", "2022-02-02")
                .put("endDate", "2022-03-03")
                .put("projectRole", "TEAM_MEMBER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 10.0)
                .toString();

        mockMvc.perform(post("/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestBody))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("This account does not have a User Profile.")));

        //Third call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/ze@mymail.com/A1/2022-02-02/2022-03-03"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ResourceID does not exist.")));
    }

    @Test
    void addResourceToProject_ScrumMasterDoesNotExistInTimePeriod_Success() throws Exception {
        //First call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "js@mymail.com")
                .put("projectCode", "666")
                .put("startDate", "2023-03-10")
                .put("endDate", "2023-03-12")
                .put("projectRole", "SCRUM_MASTER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 50.5)
                .toString();


        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-10")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("SCRUM_MASTER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-10/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Second call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/666/2023-03-10/2023-03-12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-10")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("SCRUM_MASTER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-10/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));


        ResourceIdJPA resourceIdJPA = new ResourceIdJPA(
                "js@mymail.com",
                "666",
                "2023-03-10",
                "2023-03-12"
        );
        resourceRepositoryJPA.deleteById(resourceIdJPA);
    }

    @Test
    void addResourceToProject_ProductOwnerDoesNotExistInTimePeriod_Success() throws Exception {
        //First call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "js@mymail.com")
                .put("projectCode", "666")
                .put("startDate", "2023-03-10")
                .put("endDate", "2023-03-12")
                .put("projectRole", "PRODUCT_OWNER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 50.5)
                .toString();


        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-10")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("PRODUCT_OWNER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-10/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Second call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/666/2023-03-10/2023-03-12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-10")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("PRODUCT_OWNER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-10/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));


        ResourceIdJPA resourceIdJPA = new ResourceIdJPA(
                "js@mymail.com",
                "666",
                "2023-03-10",
                "2023-03-12"
        );
        resourceRepositoryJPA.deleteById(resourceIdJPA);
    }

    @Test
    void addResourceToProject_ProjectManagerDoesNotExistInTimePeriod_Success() throws Exception {
        //First call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "js@mymail.com")
                .put("projectCode", "666")
                .put("startDate", "2023-03-10")
                .put("endDate", "2023-03-12")
                .put("projectRole", "PROJECT_MANAGER")
                .put("percentageOfAllocation", 10)
                .put("costPerHour", 50.5)
                .toString();


        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-10")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("PROJECT_MANAGER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-10/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));

        //Second call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/666/2023-03-10/2023-03-12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.email", is("js@mymail.com")))
                .andExpect(jsonPath("$.projectCode", is("666")))
                .andExpect(jsonPath("$.startDate", is("2023-03-10")))
                .andExpect(jsonPath("$.endDate", is("2023-03-12")))
                .andExpect(jsonPath("$.projectRole", is("PROJECT_MANAGER")))
                .andExpect(jsonPath("$.percentageOfAllocation", is(10)))
                .andExpect(jsonPath("$.costPerHour", is(50.5)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/resources/js%40mymail" +
                        ".com/666/2023-03-10/2023-03-12")))
                .andExpect(jsonPath("$._links.resources.href", is("http://localhost/resources")));


        ResourceIdJPA resourceIdJPA = new ResourceIdJPA(
                "js@mymail.com",
                "666",
                "2023-03-10",
                "2023-03-12"
        );
        resourceRepositoryJPA.deleteById(resourceIdJPA);
    }

    @Test
    void addResourceToProject_TeamMemberOverOneHundredPercentAllocation_ThrowsException() throws Exception {
        //First call: POST /resources
        String postRequestBody = new JSONObject()
                .put("email", "js@mymail.com")
                .put("projectCode", "666")
                .put("startDate", "2023-03-10")
                .put("endDate", "2023-03-12")
                .put("projectRole", "TEAM_MEMBER")
                .put("percentageOfAllocation", 90)
                .put("costPerHour", 50.5)
                .toString();


        mockMvc.perform(post("/resources")
                        .contentType("application/json")
                        .content(postRequestBody))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Allocation exceed 100%")));


        //Second call: GET resource resources/{email}/{projectCode}/{startDate}/{endDate}
        mockMvc.perform(get("/resources/js@mymail.com/666/2023-03-10/2023-03-12"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("ResourceID does not exist.")));
    }

}