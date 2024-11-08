# Customer Specification Document

### US001 - As an Administrator I want to create a profile

- N/A

### US002 - As an Administrator, I want to register a user

- When an account is created, it is by default set as active
- When an account is created, it is automatically associated with a user profile
- Photo is optional

### US003 - As an Administrator, I want to change a user profile

- N/A

### US004 - As Administrator, I want to get a list of all user accounts and their status

- Account status means it's active or inactive

### US005 - As Administrator, I want to inactivate a user account

- N/A

### US006 - As Administrator, I want to activate a user account

- N/A

### US007 - As Administrator, I want to create a new project typology

- N/a

### US008 - As Administrator, I want to add a business sector

- N/A

### US009 - As Administrator, I want to add a customer

- For now, the only customer info we need is the name
> 25/01 - Teams
- The customer is identified with a NIF

### US010 - As Manager, I want to register/create a new project.

- When creating a new project, not all information will be filled out (clarification TBA)
> Answered on 17/01/2023 in Teams
- For a newly created project his state is "planned" and doesn't have Human Resources associated to it. It also doesn't 
- have duration, start date, number of sprints and budget.
> Answered on 23/01/2023 in Teams
- When creating a project on the system, a user is defined as the manager of that project, making that user Project Manager
> Answered on 03/05/2023 in Teams
- The "duration" mentioned in the reply of 17/01/2023 referred to the Sprint Duration.

### US011 - As Manager, I want to associate a user as Team Member of a project.

> 17/01 - Teams
- Maintaining the resource history is fundamental

### US012 - As Manager, I want to define the PO of a project

- PO is not a Team Member
> 17/01 - Teams
- Maintaining the resource history is fundamental

### US013 - As Manager, I want to define the SM of a project

- SM is not a Team Member
> 17/01 - Teams
- Maintaining the resource history is fundamental

### US014 - As Manager, I want to get a list of all human resources in a project

- N/A

### US015 - As Manager, I want to get a list of all projects.

- In a project list, the following should be displayed: Project code, project name, customer, project status, start date and end date

### US016 - As Authenticated User, I want to get a list of all projects I'm currently allocated to

> Answered on 23/01/2023 in a Teams call. 

- It is not required to check if the user account is active. Since the user is authenticated, it is assumed that account is active.
- There's no restriction to what profile can access this functionality, it is only required to check if account exists.
- If account doesn't exist it should return an empty list.

### US018 - As PO/SM/Team Member, I want to consult the product backlog, i.e. to get the list of user stories sorted by priority.

> Answered on 28/02/2023 in Teams
- The Product Backlog is the list of User Stories ordered by descending order of priority.
> Answered on 22/03/2023 in Teams
- Acceptance Criteria:
  - A Product Backlog has finished and/or cancelled USs and open USs. The ordered list of open USs is returned.
  - A Product Backlog has finished and/or cancelled USs, but none open. An empty list is returned.
  - A Product Backlog has no USs. An empty list is returned.