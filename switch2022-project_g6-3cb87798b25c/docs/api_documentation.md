# Scrumy REST API
This documentation details how to navigate and use the resources provided by the Scrumy API.

When making an HTTP request to the REST API, you will need to specify an HTTP method and the desired path.
In addition to the HTTP method, it may also be necessary to include body parameters.
The response provided by the API will include the HTTP response status code and a response body.

***Topics***:

1. [Access](#access-section)
2. [Representations](#representations-section)
3. [Root endpoint](#root-section)
4. [Client Errors](#client-errors-section)
5. [Hypermedia](#hypermedia-section)
6. [Resources](#api-resources-section)
---

## <a id="access-section"></a> Access
The API is accessed over HTTP from `http:localhost:8080`. 
All data is sent and received as JSON missing fields are included as `null` instead of being omitted.

```bash
$ curl -I https://localhost:8080

> HTTP/1.1 200 
> Vary: Origin
> Vary: Access-Control-Request-Method
> Vary: Access-Control-Request-Headers
> Content-Type: application/hal+json
> Content-Length: 766
> Date: Mon, 19 Jun 2023 22:35:05 GMT
```

---

## <a id="representation-section"></a> Representations
### Summary
When retrieving a collection of resources, the response will contain a portion of the attributes associated with each resource.

Example: Here, we are fetching the list of existing projects. You will get the summary representation of each project. 

```bash
GET /projects
```

### Detailed
When retrieving a single resource, the response includes all the attributes pertaining to that specific resource.

Example: Here, we are fetching the 'A1' project. You will get the detailed representation of the 'A1' project.

```bash
GET /projects/A1
```

---

## <a id="root-section"></a> Root endpoint
A `GET` request to the root endpoint returns all available endpoints for the REST API.

```bash
$ curl https://localhost:8080
```

#### <u> Returns: </u>

```JSON
{
   "_links": {
      "projects": {
         "href": "http://localhost:8080/projects"
      },
      "resources": {
         "href": "http://localhost:8080/resources"
      },
      "user-stories": {
         "href": "http://localhost:8080/user-stories"
      },
      "customers": {
         "href": "http://localhost:8080/customers"
      },
      "sprints": {
         "href": "http://localhost:8080/sprints"
      },
      "profiles": {
         "href": "http://localhost:8080/profiles"
      }
   }
}
```

---

## <a id="client-errors-section"></a> Client Errors
The main possible types of errors on API calls that receive request bodies are:
1. Sending invalid fields will result in `422 Unprocessable Entity` response.
2. Valid fields that don't fulfill business rules will result in `409 Conflict` response.
3. If the resource doesn't exist it will result in `404 Not Found` response.

All error responses have a message field so that the client can identify what the problem was.

```JSON 
{
    "message": "Description of what went wrong."
}
```

> See more details on errors for a specific resource in the resources section.
> 
---

## <a id="hypermedia-section"></a> Hypermedia
Every resource in our application is enriched with hypermedia links,
providing clients with the means to explore related resources and perform actions dynamically.

These links are included within the response body of each resource representation, allowing clients to
seamlessly traverse though the API's resources.

We ensure that two essential hypermedia links are provided: one to the resource itself and another to the collection.
These links serve as entry points for clients to access specific resources or navigate to the entire collection of related resources.

***Example:*** This is a typical response body of a GET request, for a resource collection, containing hypermedia links.

```JSON
{
   "_embedded": {
      "projectDTOList": [
         {
            "projectCode": "A1",
            "projectName": "Dummy 01",
            "projectCustomer": "211267490",
            "projectStatus": "CLOSED",
            "_links": {
               "self": {
                  "href": "http://localhost:8080/projects/A1"
               },
               "projects": {
                  "href": "http://localhost:8080/projects"
               }
            }
         }
      ]
   },
   "_links": {
      "self": {
         "href": "http://localhost:8080/projects"
      }
   }
}

```
Each resource in the collection has an attribute `self` with a link to the resource itself and a an attribute with the
name of the collection (in this case `projects`) with a link to the collection to which the resource belongs to.
The collection itself has an attribute `self` with a link to itself.

---

## <a id="api-resources-section"></a> Resources
Our API offers a comprehensive set of resources that can be accessed and interacted with via HTTP requests.
These resources include:

1. [Projects](#projects-section)
   1. [Product Backlog](#productbacklog-section)
   2. [Sprint Backlog](#sprintbacklog-section)
2. [Resources](#resources-section)  
3. [UserStories](#userstories-section)  
4. [Sprints](#sprints-section)  
5. [Customers](#customers-section)  
6. [Profiles](#profiles-section)

These resources are designed to provide a comprehensive set of functionalities to meet our application's needs. 
By leveraging HTTP requests, you can perform various operations on these resources, allowing for seamless integration 
and manipulation of data within our application.

### <a id="projects-section"></a> Projects

This namespace contains endpoints for projects management.

***/projects***

* Method: `POST`
* Description: Creates a new project with the provided content. There are some attributes that are required for project
creation (specified in the example "only_required_fields"). The remaining fields are option and can be included in the
request body as needed.
* URL Structure: `http://localhost:8080/projects` 

***Example***: project_creation
```bash
curl -X POST 'http://localhost:8080/projects' \
--header 'Content-Type: application/json' \
--data '{
    "projectCode": "P001",
    "projectName": "Project name",
    "description": "Project description",
    "customerID": "211267490",
    "businessSectorName": "Technology",
    "projectTypologyName": "Time and Materials",
    "sprintDuration": 1,
    "numberPlannedSprints": 10,
    "budget": 1000.0,
    "startDate": "2023-05-24",
    "endDate": "2023-12-24"
}'
```
`projectCode`: <i>String</i>. **Required**. The code that identifies the project.

`projectName`: <i>String</i>. **Required**. The name of the project.

`description`: <i>String</i>. **Required**. A description of the project.

`customerID`: <i>String</i>. **Required**. The customers tax identification number. At the moment the API only accepts Portuguese 
tax identification numbers.

`businessSectorName`: <i>String</i>. **Required**. The business sector of the project.

`projectTypologyName`:<i>String</i>. **Required**. The type of project. Projects can be of type "Time and Materials" or "Fixed-Cost".

`sprintDuration`:<i>Integer</i>. The duration of each of the project's sprints, in weeks.

`numberPlannedSprints`:<i>Integer</i>. The number of planned sprints for the project.

`budget`: <i>Double</i>. The project's budget.

`startDate`: <i>String</i>. The project's start date.

`endDate`: <i>String</i>. The project's end date.

#### <u> Returns: </u>
```JSON
{
   "projectCode": "P001",
   "projectName": "Project name",
   "description": "Project description",
   "customerID": "211267490",
   "businessSectorName": "Technology",
   "projectTypologyName": "Time and Materials",
   "productBacklog": [],
   "status": "PLANNED",
   "statusHistory": {
      "PLANNED": "2023-06-20"
   },
   "sprintDuration": 1,
   "numberPlannedSprints": 10,
   "budget": 1000.0,
   "startDate": "2023-05-24",
   "endDate": "2023-12-24",
   "_links": {
      "self": {
         "href": "http://localhost:8080/projects/P001"
      },
      "projects": {
         "href": "http://localhost:8080/projects"
      }
   }
}
```
In addition to the attributes specified in the request body, some additional attributes are returned:

`productBacklog`: <i>List.Of(String)</i>. The list of user stories codes that are a part of the product backlog. When the project
is created, the product backlog starts off empty.

`status`: <i>String</i>. The current phase of a project. When the project is created, the status is set to "PLANNED".

`StatusHistory`: <i>Object</i>. The project's list of status changes over time.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* An HTTP request sent with invalid fields will result in `422 Unprocessable Entity` response.

>Fields are considered invalid if flagged as ***required*** and sent as `null` or `blank`.

Example: blank_project_code
```JSON
{"message": "Project Code cannot be empty"}
```

Example: null_project_code
```JSON
{"message":"Project Code cannot be null"}
```

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `409 Conflict` response.

Example: invalid_portuguese_tax_identification_number (123456788)
```JSON
{"message":"Invalid Portuguese Tax Identification Number"}
```

Example: invalid_sprint_duration (-1)
```JSON
{"message":"Invalid duration."}  
```

Example: invalid_number_of_planned_sprints (-10)
```JSON
{"message":"Number of planned Sprints must be greater than 0"} 
```

Example: invalid_budget (-1000.0)
```JSON
{"message":"Budget must be greater than 0"} 
```

Example: nonexistent_customer_id
```JSON
{"message":"Customer doesn't exist."} 
```

Example: nonexistent_business_sector
```JSON
{"message":"Business Sector doesn't exist."}
```

Example: nonexistent_project_typology
```JSON
{"message":"Project Typology doesn't exist."}
```

---

***/projects***

* Method: `GET`
* Description: Retrieves all existing projects.
* URL Structure: `http://localhost:8080/projects`

***Example***:
```bash
curl -X GET http://localhost:8080/projects
```

#### <u> Returns: </u>

```JSON
{
    "_embedded": {
        "projectDTOList": [
            {
                "projectCode": "A1",
                "projectName": "Dummy 01",
                "projectCustomer": "211267490",
                "projectStatus": "PLANNED",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/projects/A1"
                    },
                    "projects": {
                        "href": "http://localhost:8080/projects"
                    }
                }
            },
            {
                "projectCode": "A2",
                "projectName": "Dummy 02",
                "projectCustomer": "272983330",
                "projectStatus": "PLANNED",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/projects/A2"
                    },
                    "projects": {
                        "href": "http://localhost:8080/projects"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/projects"
        }
    }
}
```

`projectCode`: <i>String</i>. The code that identifies the project.

`projectName`: <i>String</i>. The name of the project.

`customerID`: <i>String</i>. The customers tax identification number. 

`status`: <i>String</i>. The current phase of a project.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

---

***/projects/{projectCodeValue}***

* Method: `GET`
* Description: Returns the specified project.
* URL Structure: `http://localhost:8080/projects/{projectCodeValue}`

***Example***:
```bash
curl -X GET http://localhost:8080/projects/A2
```

`projectCodeValue`: <i>String</i>. The code that identifies the project.


#### <u> Returns: </u>
```JSON
{
   "projectCode": "A2",
   "projectName": "Dummy 02",
   "description": "Just another dummy project",
   "customerID": "272983330",
   "businessSectorName": "Marketing",
   "projectTypologyName": "Fixed-Cost",
   "productBacklog": [
      "US011",
      "US012",
      "US013"
   ],
   "status": "ELABORATION",
   "statusHistory": {
      "INCEPTION": "2023-03-15",
      "ELABORATION": "2023-06-20",
      "PLANNED": "2023-02-20"
   },
   "sprintDuration": 3,
   "numberPlannedSprints": 12,
   "budget": 350000.0,
   "startDate": "2023-02-20",
   "endDate": "2024-04-29",
   "_links": {
      "self": {
         "href": "http://localhost:8080/projects/A2"
      },
      "projects": {
         "href": "http://localhost:8080/projects"
      }
   }
}
```
`projectCode`: <i>String</i>. The code that identifies the project.

`projectName`: <i>String</i>. The name of the project.

`description`: <i>String</i>. A description of the project.

`customerID`: <i>String</i>. The customers tax identification number.

`businessSectorName`: <i>String</i>. The business sector on which the customer operates.

`projectTypologyName`:<i>String</i>. The type of project. Projects can be of type "Time and Materials" or "Fixed Cost".

`productBacklog`: <i>List of (String)</i>. The list of user stories codes that are a part of the product backlog. When the project
is created, the product backlog starts off empty.

`status`: <i>String</i>. The current phase of a project. 

`StatusHistory`: <i>Object</i>. The project's list of status changes over time.

`sprintDuration`:<i>Integer</i>. The duration of each of the project's sprints.

`numberOfPlannedSprints`:<i>Integer</i>. The number of planned sprints for the project.

`budget`: <i>Double</i>. The project's budget.

`startDate`: <i>String</i>. The project's start date.

`endDate`: <i>String</i>. The project's end date.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* If the resource doesn't exist in the database it will result in `404 Not Found` response.

Example: nonexistent_project_code
```JSON
{"message":"Project doesn't exist"}
```

---

## <a id="productbacklog-section"></a>Product Backlog

This namespace contains endpoints for managing a project's Product Backlog.

### /projects/{projectCode}/user-stories

* Method: `GET`
* Description: Retrieves all existing user stories in the Product Backlog in order of priority (first position in the list
has the highest priority).
* URL Structure: `http://localhost:8080/projects/{projectCode}/user-stories`

***Example***:
```bash
curl -X GET http://localhost:8080/projects/A2/user-stories
```

`projectCode`: <i>String</i>. The code that identifies the project.


#### <u> Returns: </u>
```JSON
{
    "_embedded": {
        "userStoryDTOList": [
            {
                "projectCode": {
                    "projectCodeValue": "A2"
                },
                "userStoryCode": {
                    "userStoryCodeValue": "US011"
                },
                "description": {
                    "descriptionValue": " A2 dummy 011"
                },
                "status": "RUNNING"
            },
            {
                "projectCode": {
                    "projectCodeValue": "A2"
                },
                "userStoryCode": {
                    "userStoryCodeValue": "US012"
                },
                "description": {
                    "descriptionValue": " A2 dummy 012"
                },
                "status": "OPEN"
            }
        ]
    }
}
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`userStoryCode`: <i>String</i>. The code used to identify the user story.

`description`: <i>String</i>. The user story's description.

`status`: <i>String</i>. The current phase of the user story.

#### <u> Errors: </u>

* If the resource doesn't exist in the database it will result in `404 Not Found` response.

Example: nonexistent_project_code
```JSON
{"message":"Project doesn't exist"}
```
---

### /projects/{projectCode}/user-stories/{userStoryCode}

* Method: `GET`
* Description: Returns the specified user story from the product backlog.
* URL Structure: `http://localhost:8080/projects/{projectCode}/user-stories/{userStoryCode}`

***Example***:
```bash
curl -X GET 'http://localhost:8080/projects/A2/user-stories/US011'
```

`projectCode`: <i>String</i>. The code that identifies the project.

`userStoryCode`: <i>String</i>. The code used to identify the user story.


#### <u> Returns: </u>

```JSON
{
    "projectCode": "A2",
    "userStoryCode": "US011",
    "description": " A2 dummy 011",
    "status": "RUNNING",
    "userStoryEffort": 1,
    "_links": {
        "self": {
            "href": "http://localhost:8080/projects/A2/user-stories/US011"
        },
        "/projects/A2/user-stories": {
            "href": "http://localhost:8080/projects/A2/user-stories"
        }
    }
}
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`userStoryCode`: <i>String</i>. The code used to identify the user story.

`description`: <i>String</i>. The textual explanation of the user story.

`status`: <i>String</i>. The current phase of the user story.

`userStoryEffort`: <i>Integer</i>. An integer representing the estimated number of hours needed to complete the user 
story, following the Fibonacci series.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

If the resource doesn't exist it will result in `404 Not Found` response.

Example: nonexistent_user_story_code
```JSON
{"message":"User Story doesn't exist"}
```
Example: nonexistent_project_code
```JSON
{"message":"Project doesn't exist"}
```
---

### <a id="sprintbacklog-section"></a> Sprint Backlog

This namespace contains endpoints for managing a sprint's backlog, in the context of a project.

### /projects/{projectCode}/sprints/{sprintCode}/backlog

* Method: `PATCH`
* Description: Update the sprint backlog of a sprint by adding a user story.
* URL Structure: `http://localhost:8080/projects/{projectCode}/sprints/{sprintCode}/backlog`

***Example***:
```bash
curl -X PATCH 'http://localhost:8080/projects/666/sprints/S1/backlog' \
--header 'Content-Type: application/json' \
--data '{
    "userStoryCode": "US001"
}'
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`sprintCode`:<i>String</i>. The code that identifies the sprint.

#### <u> Returns: </u>
```JSON
{
    "projectCode": "666",
    "sprintCode": "S1",
    "startDate": "2023-04-02",
    "endDate": "2023-04-30",
    "sprintDuration": 4,
    "sprintBacklog": [
        "my test code"
    ],
    "sprintStatus": "OPENED"
}
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`sprintCode`:<i>String</i>. The code that identifies the sprint.

`startDate`:<i>String</i>. The sprint's start date.

`endDate`:<i>String</i>. The sprint's end date.

`sprintDuration`:<i>Integer</i>. The duration of the sprint.

`sprintBacklog`:<i>List of (String)</i>. The list of user story codes of the sprint.

`sprintStatus`: <i>String</i>. The current phase of the sprint.

#### <u> Errors: </u>

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `409 Conflict` response.

Example: null_user_story_code
```JSON
{"message": "The inserted User Story code is invalid"}
```
Example: non_existent_project_code
```JSON
{"message": "The selected Project does not exist"}
```
Example: closed_sprint
```JSON
{"message": "The selected Sprint is currently closed"}
```
Example: non_existent_sprint
```JSON
{"message": "The selected User Story does not exist in the Product Backlog"}
```

* An HTTP request sent with invalid fields will result in `422 Unprocessable Entity` response.

Example: blank_user_story_code
```JSON
{"message": "The inserted User Story code is invalid"}
```
---

### /projects/{projectCode}/sprints/{sprintCode}/backlog

* Method: `GET`

* Description: Retrieves the sprint backlog of a sprint.

* URL Structure: `http://localhost:8080/projects/{projectCode}/sprints/{sprintCode}/backlog`

***Example***:
```bash
curl -X GET 'http://localhost:8080/projects/666/sprints/S1/backlog'
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`sprintCode`:<i>String</i>. The code that identifies the sprint.

#### <u> Returns: </u>

```JSON
{
    "_embedded": {
        "userStoryDTOList": [
            {
                "projectCode": {
                    "projectCodeValue": "666"
                },
                "userStoryCode": {
                    "userStoryCodeValue": "my test code"
                },
                "description": {
                    "descriptionValue": "presentation user story"
                },
                "status": "OPEN"
            }
        ]
    }
}
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`sprintCode`:<i>String</i>. The code that identifies the sprint.

`userStoryCode`: <i>String</i>. The code used that identifies the user story.

`description`: <i>String</i>. The textual explanation of the user story.

`status`: <i>String</i>. The current phase of the user story.

#### <u> Errors: </u>

* If the resource doesn't exist it will result in `404 Not Found` response.

Example: non_existent_project
```JSON
{"message": "Project does not exist."}
```
Example: non_existent_sprint
```JSON
{"message": "Sprint does not exist."}
```
---

### <a id="resources-section"></a> Resources

This namespace provides endpoints for managing project Resources.
In the context of a project, a Resource refers to a user account that has been assigned to the 
project with a specific role.

### /resources

* Method: `POST`
* Description: Assigns a new resource to a project.
* URL Structure: `http://localhost:8080/resources`

***Example***:
```bash
curl -X POST 'http://localhost:8080/resources' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "js@mymail.com",
    "projectCode": "666",
    "startDate": "2023-03-10",
    "endDate": "2023-03-12",
    "projectRole": "TEAM_MEMBER",
    "percentageOfAllocation": 50,
    "costPerHour": 10.0
}'
```
`email`: <i>String</i>. The account identifier.

`projectCode`: <i>String</i>. The project identifier to which the resource will be added.

`startDate`: <i>String</i>. The start date of the resource's period of allocation.

`endDate`: <i>String</i>. The end date of the resource's period of allocation.

`projectRole`: <i>String</i>. Specifies the role of the resource within the project.

`percentageOfAllocation`: <i>Integer</i>. Specifies the daily time percentage that the resource will
dedicate to the project.

`costPerHour`: <i>Double</i>. Indicates the hourly cost associated with the resource.


#### <u> Returns: </u>

```JSON
{
    "email": "js@mymail.com",
    "projectCode": "666",
    "startDate": "2023-03-10",
    "endDate": "2023-03-12",
    "projectRole": "TEAM_MEMBER",
    "percentageOfAllocation": 50,
    "costPerHour": 10.0,
    "_links": {
        "self": {
            "href": "http://localhost:8080/resources/js%40mymail.com/666/2023-03-10/2023-03-12"
        },
        "resources": {
            "href": "http://localhost:8080/resources"
        }
    }
}
```
In addition to the attributes specified in the request body, an addition attribute is returned:

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* An HTTP request sent with invalid fields will result in `422 Unprocessable Entity` response.

>Fields are considered invalid if flagged as ***required*** and sent as `null`, `blank`, `negative value` or
> invalid `role`.  

***In Resources, all fields are required***.

Example: null_email
```JSON
{"message": "Invalid Email."}
```
Example: blank_email
```JSON
{"message": "Invalid Email."}
```
Example: null_project_code
```JSON
{"message": "Project Code cannot be null"}
```
Example: blank_project_code
```JSON
{"message": "Project Code cannot be empty"}
```
Example: invalid_percentage_of_allocation (-10)
```JSON
{"message": "Invalid percentage of allocation."}
```
Example: invalid_cost_per_hour (-10.0)
```JSON
{"message": "Invalid cost per hour."}
```
Example: unknown_project_role
```JSON
{"message": "Invalid role."}
```

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `409 Conflict` response.

Example: resource_already_exists
```JSON
{"message": "This resource is already exists in the given time period."}
```
Example: allocation_exceeds_maximum
```JSON
{"message": "Allocation exceed 100%"}
```
Example: end_date_after_project_end_date
```JSON
{"message": "Resource end date cannot be after project end date."}
```
Example: start_date_before_project_start_date
```JSON
{"message": "Resource start date cannot be before the project start date."}
```
Example: not_user_profile
```JSON
{"message": "This account does not have a User Profile."}
```
Example: product_owner_already_exists
```JSON
{"message": "PRODUCT_OWNER already exists in time period."}
```
Example: scrum_master_already_exists
```JSON
{"message": "SCRUM_MASTER already exists in time period."}
```
Example: project_manager_already_exists
```JSON
{"message": "PROJECT_MANAGER already exists in time period."}
```

* If a resource doesn't exist in the database it will result in `404 Not Found` response.

Example: non_existent_email
```JSON
{"message": "Account does not exist."}
```
Example: non_existent_project_code
```JSON
{"message": "Project does not exist."}
```
---

### /resources/{projectCode}/{dateOfToday}

* Method: `GET`

* Description: Retrieves a list of all resources currently assigned to a project.

* URL Structure: `http://localhost:8080/resources/{projectCode}/{dateOfToday}`

***Example***:
```bash
curl -X GET 'http://localhost:8080/resources/A2/2023-06-21'
```

`projectCode`: <i>String</i>. The project identifier.

`dateOfToday`: <i>String</i>. The search date.


#### <u> Returns: </u>
```JSON
{
   "_embedded": {
      "resourceDTOList": [
         {
            "email": "qb@mymail.com",
            "projectCode": "A2",
            "startDate": "2023-05-15",
            "endDate": "2024-04-29",
            "projectRole": "PROJECT_MANAGER",
            "_links": {
               "self": {
                  "href": "http://localhost:8080/resources/qb%40mymail.com/A2/2023-05-15/2024-04-29"
               }
            }
         },
         {
            "email": "tg@mymail.com",
            "projectCode": "A2",
            "startDate": "2023-05-15",
            "endDate": "2024-04-29",
            "projectRole": "PRODUCT_OWNER",
            "_links": {
               "self": {
                  "href": "http://localhost:8080/resources/tg%40mymail.com/A2/2023-05-15/2024-04-29"
               }
            }
         },
         {
            "email": "zm@mymail.com",
            "projectCode": "A2",
            "startDate": "2023-05-31",
            "endDate": "2024-04-29",
            "projectRole": "TEAM_MEMBER",
            "_links": {
               "self": {
                  "href": "http://localhost:8080/resources/zm%40mymail.com/A2/2023-05-31/2024-04-29"
               }
            }
         }
      ]
   },
   "_links": {
      "self": {
         "href": "http://localhost:8080/resources/A2/2023-06-21"
      }
   }
}
```

`email`: <i>String</i>. The account identifier.

`projectCode`: <i>String</i>. The project identifier to which the resource is allocated to.

`startDate`: <i>String</i>. The start date of the resource's period of allocation.

`endDate`: <i>String</i>. The end date of the resource's period of allocation.

`projectRole`: <i>String</i>. Specifies the role of the resource within the project.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>
> There are no error response messages. An empty list is returned if the resource does not exist. 

### /resources/{resourceEmail}/{projectCode}/{startDate}/{endDate}

* Method: `GET`

* Description: Retrieves a resource based on its ID.

* URL Structure: `http://localhost:8080/resources/{email}/{projectCode}/{startDate}/{endDate}`

***Example***:
```bash
curl -X GET 'http://localhost:8080/resources/tc@mymail.com/A1/2022-02-01/2022-07-31'
```

`email`: <i>String</i>. The account identifier.

`projectCode`: <i>String</i>. The project identifier.

`startDate`: <i>String</i>. The start date of the resource's period of allocation.

`endDate`: <i>String</i>. The end date of the resource's period of allocation.

#### <u> Returns: </u>
```JSON
{
   "email": "tc@mymail.com",
   "projectCode": "A1",
   "startDate": "2022-02-01",
   "endDate": "2022-07-31",
   "projectRole": "PROJECT_MANAGER",
   "percentageOfAllocation": 20,
   "costPerHour": 35.0,
   "_links": {
      "self": {
         "href": "http://localhost:8080/resources/tc%40mymail.com/A1/2022-02-01/2022-07-31"
      },
      "resources": {
         "href": "http://localhost:8080/resources"
      }
   }
}
```
`email`: <i>String</i>. The account identifier.

`projectCode`: <i>String</i>. The project identifier to which the resource is allocated to.

`startDate`: <i>String</i>. The start date of the resource's period of allocation.

`endDate`: <i>String</i>. The end date of the resource's period of allocation.

`projectRole`: <i>String</i>. Specifies the role of the resource within the project.

`percentageOfAllocation`: <i>Integer</i>. Specifies the daily time percentage that the resource will
dedicate to the project.

`costPerHour`: <i>Double</i>. Indicates the hourly cost associated with the resource.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* If the resource doesn't exist in the database it will result in `404 Not Found` response.

Example: non_existent_resource_id
```JSON
{"message": "ResourceID does not exist."}
```
---

### <a id="userstories-section"></a> User Stories

This namespace contains endpoints for managing user stories.

***/user-stories***

* Method: `POST`

* Description: Creates a new user story with the provided content, in the context of a project.

* URL Structure: `http://localhost:8080/user-stories`

***Example***:
```bash
curl -X POST 'http://localhost:8080/user-stories' \
--header 'Content-Type: application/json' \
--data '{
          "projectCode": "666",
          "userStoryCode": "my test code",
          "description": "presentation user story"
        }'
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`userStoryCode`: <i>String</i>. The code used to identify the user story.

`description`: <i>String</i>. The textual explanation of the user story.


#### <u> Returns: </u>

```JSON
{
    "projectCode": {
        "projectCodeValue": "666"
    },
    "userStoryCode": {
        "userStoryCodeValue": "US001"
    },
    "description": {
        "descriptionValue": "User Story description"
    },
    "status": "OPEN"
}
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`userStoryCode`: <i>String</i>. The code used to identify the user story.

`description`: <i>String</i>. The user story description.

`status`: <i>String</i>. The current phase of the user story.

#### <u> Errors: </u>

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `409 Conflict` response.

Example: non_existent_project
```JSON
{"message": "Project does not exist."}
```
Example: null_project_code
```JSON
{"message": "Invalid Project Code."}
```
Example: null_user_story_code
```JSON
{"message": "The inserted User Story code is invalid"}
```
Example: null_description
```JSON
{"message": "User Story Description cannot be null."}
```

* An HTTP request sent with invalid fields will result in `422 Unprocessable Entity` response.

Example: blank_user_story_code
```JSON
{"message": "The inserted User Story code is invalid"}
```
Example: blank_project_code
```JSON
{"message": "Project Code cannot be empty"}
```
Example: blank_description
```JSON
{"message": "Description cannot be empty"}
```
---

***/projects/{projectCode}/user-stories/{userStoryCode}***

* Method: `PATCH`

* Description: Allows for the modification of the user story status.

* URL Structure: `http://localhost:8080/projects/{projectCode}/user-stories/{userStoryCode}`

***Example***:
```bash
curl -X PATCH 'http://localhost:8080/projects/A1/user-stories/US02' \
--header 'Content-Type: application/json' \
--data '{
    "userStoryStatus":"RUNNING"
}'
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`userStoryCode`: <i>String</i>. The code used to identify the user story.

`userStoryStatus`: <i>String</i>. The current phase of the user story.

#### <u> Returns: </u>

```JSON
{
    "projectCode": "A1",
    "userStoryCode": "US02",
    "description": "Dummy 02",
    "status": "RUNNING",
    "userStoryEffort": 1,
    "_links": {
        "self": {
            "href": "http://localhost:8080/projects/A1/user-stories/US02"
        }
    }
}
```

`projectCode`:<i>String</i>. The code representing the project that the user story belongs to.

`userStoryCode`: <i>String</i>. The code used to identify the user story.

`description`: <i>String</i>. The textual explanation of the user story.

`userStoryEffort`: <i>Integer</i>. An integer representing the estimated number of hours needed to complete the user
story, following the Fibonacci series.

`status`: <i>String</i>. The current phase of the user story.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* An HTTP request sent with invalid fields will result in `422 Unprocessable Entity` response.

Example: unknown_user_story_status
```JSON
{"message": "Invalid User Story Status"}
```

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `400 Bad Request` response.

Example: non_existent_project_code
```JSON
{"message": "Project does not exist."}
```
Example: non_existent_user_Story_code
```JSON
{"message": "User Story does not exist."}
```
---

### <a id="sprints-section"></a> Sprints

This namespace contains endpoints for managing sprints.

***/sprints***

* Method: `POST`

* Description: Creates a new sprint with the provided content, in the context of a project.

* URL Structure: `http://localhost:8080/sprints`

***Example***:
```bash
curl -X POST 'http://localhost:8080/sprints' \
--header 'Content-Type: application/json' \
--data '{
        "projectCode": "A2",
        "startDate": "2024-03-02"
    }'
```

`projectCode`:<i>String</i>. The code representing the project that the sprint belongs to.

`startDate`: <i>String</i>. The sprint's start date.

#### <u> Returns: </u>

```JSON
{
    "sprintID": {
        "projectCode": {
            "projectCodeValue": "A2"
        },
        "sprintCode": {
            "sprintCodeValue": "S7"
        }
    },
    "sprintDuration": {
        "sprintDurationValue": 3
    },
    "startDate": "2024-03-02",
    "endDate": "2024-03-23",
    "sprintStatus": "PLANNED"
}
```

`projectCode`:<i>String</i>. The code representing the project that the sprint belongs to.

`sprintCode`: <i>String</i>. The code used to identify the sprint.

`sprintDurantion`: <i>Integer</i>. The duration of the sprint. Set at the time of the project's creation.

`startDate`: <i>String</i>. The sprint's start date.

`endDate`: <i>String</i>. The sprint's end date. Calculated based on the sprint duration.

`sprintStatus`: <i>String</i>. The current phase of the sprint.

#### <u> Errors: </u>

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `409 Conflict` response.

Example: non_existent_project_code
```JSON
{"message": "Project does not exist."}
```
Example: start_date_before_end_date_of_previous_sprint
```JSON
{"message": "Start date must be equal to or after the end date of the previous sprint"}
```
Example: start_date_outside_project_period
```JSON
{"message": "Sprint start or/and end dates are outside the project range"}
```
Example: blank_start_date
```JSON
{"message": "can not create a sprint with start date not defined"}
```

* An HTTP request sent with invalid fields will result in `422 Unprocessable Entity` response.

Example: blank_project_code
```JSON
{"message": "Project Code cannot be empty"}
```
---

***/sprints***

* Method: `PATCH`

* Description: Allows for the modification of the sprint status.

* URL Structure: `http://localhost:8080/sprints`

***Example***:
```bash
curl -X PATCH 'http://localhost:8080/sprints' \
--header 'Content-Type: application/json' \
--data '{
        "projectCode": "A2",
        "sprintCode": "S2",
        "sprintStatus": "CLOSED"
}'
```

`projectCode`:<i>String</i>. The code representing the project that the sprint belongs to.

`sprintCode`: <i>String</i>. The code used to identify the sprint.

`sprintStatus`: <i>String</i>. The current phase of the sprint.

#### <u> Returns: </u>

```JSON
{
   "sprintID": {
      "projectCode": {
         "projectCodeValue": "666"
      },
      "sprintCode": {
         "sprintCodeValue": "S1"
      }
   },
   "sprintDuration": {
      "sprintDurationValue": 4
   },
   "startDate": "2023-04-02",
   "endDate": "2023-04-30",
   "sprintStatus": "CLOSED"
}
```

`projectCode`:<i>String</i>. The code representing the project that the sprint belongs to.

`sprintCode`: <i>String</i>. The code used to identify the sprint.

`sprintDurantion`: <i>Integer</i>. The duration of the sprint. Set at the time of the project's creation.

`startDate`: <i>String</i>. The sprint's start date.

`endDate`: <i>String</i>. The sprint's end date. Calculated based on the sprint duration.

`sprintStatus`: <i>String</i>. The current phase of the sprint.


#### <u> Errors: </u>

* An HTTP request sent with invalid fields will result in `422 Unprocessable Entity` response.

Example: blank_project_code
```JSON
{"message": "Project Code cannot be empty"}
```

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `409 Conflict` response.

Example: invalid_sprint_code
```JSON
{"message": "Sprint does not exist"}
```
---

***projects/{projectCodeValue}/sprints/open***

* Method: `GET`

* Description: Retrieves the current open sprint of a project.

* URL Structure: `http://localhost:8080/projects/{projectCodeValue}/sprints/open`

***Example***:
```bash
curl -X GET 'http://localhost:8080/projects/A2/sprints/open'
```

`projectCode`:<i>String</i>. The code representing the project that the sprint belongs to.

#### <u> Returns: </u>

```JSON
{
    "sprintCode": "S3",
    "startDate": "2023-06-12",
    "endDate": "2023-07-03",
    "sprintStatus": "OPENED"
}
```
`projectCode`:<i>String</i>. The code representing the project that the sprint belongs to.

`sprintCode`: <i>String</i>. The code used to identify the sprint.

`endDate`: <i>String</i>. The sprint's end date. Calculated based on the sprint duration.

`sprintStatus`: <i>String</i>. The current phase of the sprint.

#### <u> Errors: </u>

* If the resource doesn't exist in the database it will result in `404 Not Found` response.

Example: non_existent_project_code
```JSON
{"message": "Project does not exist."}
```
---

***projects/{projectCodeValue}/sprints/***

* Method: `GET`

* Description: Retrieves all sprints created for a project.

* URL Structure: `http://localhost:8080/projects/{projectCodeValue}/sprints`

***Example***:
```bash
curl -X GET 'http://localhost:8080/projects/A2/sprints'
```

`projectCode`:<i>String</i>. The code representing the project that the sprint belongs to.

#### <u> Returns: </u>

```JSON
{
    "_embedded": {
        "openSprintOutputDTOList": [
            {
                "sprintCode": "S1",
                "startDate": "2023-05-01",
                "endDate": "2023-05-22",
                "sprintStatus": "CLOSED"
            },
            {
                "sprintCode": "S2",
                "startDate": "2023-05-22",
                "endDate": "2023-06-12",
                "sprintStatus": "CLOSED"
            },
            {
                "sprintCode": "S3",
                "startDate": "2023-06-12",
                "endDate": "2023-07-03",
                "sprintStatus": "OPENED"
            }
        ]
    }
}
```

`projectCode`: <i>String</i>. The code representing the project that the sprint belongs to.

`sprintCode`: <i>String</i>. The code used to identify the sprint.

`endDate`: <i>String</i>. The sprint's end date. Calculated based on the sprint duration.

`sprintStatus`: <i>String</i>. The current phase of the sprint.

#### <u> Errors: </u>

* If the resource doesn't exist in the database it will result in `404 Not Found` response.

Example: non_existent_project_code
```JSON
{"message": "Project does not exist."}
```
---

### <a id="customers-section"></a> /customers

This namespace contains endpoints for managing customers.

***/customers***

* Method: `POST`

* Description: Creates a new customer with the provided content.

* URL Structure: `http://localhost:8080/customers`

***Example***:
```bash
curl -X POST 'http://localhost:8080/customers' \
--header 'Content-Type: application/json' \
--data '{
    "taxIdentificationNumber": "123456789",
    "customerName": "ISEP",
    "country": "portugal"
}'
```

`taxIdentificationNumber`: <i>String</i>. The tax identification number of the customer.

`customerName`: <i>String</i>. The name of the customer.

`country`: <i>String</i>. The customer's country.


#### <u> Returns: </u>

```JSON
{
    "taxIdentificationNumber": "123456789",
    "customerName": "ISEP",
    "country": "portugal",
    "_links": {
        "collection": {
            "href": "http://localhost:8080/customers/"
        },
        "self": {
            "href": "http://localhost:8080/customers?taxIdentificationNumber=123456789&country=portugal"
        }
    }
}
```

`taxIdentificationNumber`: <i>String</i>. The tax identification number of the customer.

`customerName`: <i>String</i>. The name of the customer.

`country`: <i>String</i>. The customer's country.

`sprintStatus`: <i>String</i>. The current phase of the sprint.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* An HTTP request sent with valid fields that ***don't*** fulfill business rules will result in `409 Conflict` response.

Example: invalid_tax_identification_number
```JSON
{"message": "Invalid tax identification number or country"}
```
Example: invalid_country
```JSON
{"message": "Invalid tax identification number or country"}
```
---

***/customers***

* Method: `GET`

* Description: Retrieves a customer.

* URL Structure: `http://localhost:8080/customers`

***Example***:
```bash
curl -X GET 'http://localhost:8080/customers?taxIdentificationNumber=123456789&country=portugal'
```

`taxIdentificationNumber`: <i>String</i>. The tax identification number of the customer.

`country`: <i>String</i>. The customer's country.

#### <u> Returns: </u>

```JSON
{
    "taxIdentificationNumber": "123456789",
    "customerName": "ISEP",
    "country": "portugal",
    "_links": {
        "self": {
            "href": "http://localhost:8080/customers?taxIdentificationNumber=123456789&country=portugal"
        }
    }
}
```

`taxIdentificationNumber`: <i>String</i>. The tax identification number of the customer.

`customerName`: <i>String</i>. The name of the customer.

`country`: <i>String</i>. The customer's country.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* If the resource doesn't exist in the database it will result in `404 Not Found` response.
```JSON
{"message": "Customer not found"}
```

---

### <a id="profiles-section"></a> Profiles

This namespace contains endpoints for managing system profiles.

***/profiles***

* Method: `POST`

* Description: Creates a new profile with the provided content.

* URL Structure: `http://localhost:8080/profiles`

***Example***:
```bash
curl -X POST 'http://localhost:8080/profiles' \
--header 'Content-Type: application/json' \
--data '{
    "profileName": "joker"
}'
```

`profileName`: <i>String</i>. The name attributed to the profile.

#### <u> Returns: </u>

```JSON
{
    "profileName": "joker",
    "_links": {
        "collection": {
            "href": "http://localhost:8080/profiles/"
        },
        "self": {
            "href": "http://localhost:8080/profiles?profileName=joker"
        }
    }
}
```

`profileName`: <i>String</i>. The name attributed to the profile.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

---

***/profiles***

* Method: `GET`

* Description: Retrieves a profile.

* URL Structure: `http://localhost:8080/profiles`

***Example***:
```bash
curl -X GET 'http://localhost:8080/profiles?profileName=joker'
```

`profileName`: <i>String</i>. The name attributed to the profile.

##### <u> Returns: </u>

```JSON
{
   "profileName": "joker",
   "_links": {
      "self": {
         "href": "http://localhost:8080/profiles?profileName=joker"
      }
   }
}
```

`profileName`: <i>String</i>. The name attributed to the profile.

`_links`: <i>String</i>. Hypermedia links to the resource and the collection of resources.

#### <u> Errors: </u>

* If the resource doesn't exist in the database it will result in `404 Not Found` response.

Example: non_existent_profile
```JSON
{"message": "Profile not found"}
```
---