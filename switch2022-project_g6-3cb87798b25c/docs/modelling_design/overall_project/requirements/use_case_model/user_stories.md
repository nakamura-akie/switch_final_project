# User Stories

## Table of Contents
* Introduction
* User Stories
* Index

## Introduction
This document details brief User Stories for the Use Cases identified in the overall vision document.

## User Stories
### US999:
As administrator, I want to change the profile of a user.

### US998:
As a manager, I want to create a new project.

* Acceptance criteria:
    1. At the time of project  creation, a user is assigned to Project Manager
    2. That user maintains the roles he already has in other projects.

### US997:
As a user, I want to log in.

* Acceptance criteria:
    1. E-mail already associated to an existing account
    2. Correct password
    3. Log in successful
  
### US996:
As a user, I want to log out of the system at any time.

* Acceptance criteria:
    1. User logs out

### US995:
As administrator, I want to register users.

* Acceptance criteria:
  1. Registration should be done via a form accessible via a link in the login zone.
  2. The registration must have at least the following information:
     1. Name.
     2. Email.
     3. Phone number.
     4. Photo (optional).
  3. Creating accounts in the system must ensure that email address used is unique.
  4. If account is not associated with another profile or the user added to a project's team, the User will only be able
     to edit some of its profile data.

### US994:
As a User, I want to reset password.

* Acceptance criteria:
  1. Login form should have a link that allows users to reset their password. 
  2. In this form the account email and a button must be entered.
  3. The account email must be valid.
  4. Email with reset password link is sent.

### US993:
As administrator, I want to list all system users.

### US992:
As administrator, I want to search for users.

### US991:
As administrator, I want to associate user accounts with existing profiles.

### US990:
As administrator, I want to activate user accounts.

### US989:
As administrator, I want to disable user accounts.

### US988:
As administrator, I want to edit other user account information.

### US987:
As a Manager[^1], I want to register a project.

* Acceptance Criteria:
    1. Each project must have at least the following information:
        * Code (unique alphanumerical identifier for each project);
        * Project manager (user who will have the project manager profile for that record; may
        * change over time);
        * Name;
        * Description;
        * Start Date;
        * Sprint duration (initial estimate);
        * Number of planned sprints;
        * End Date (when available, date it was closed);
        * Customer;
        * Business Sector;
        * Typology (Fixed Cost / Time and materials);
        * Product Owner (may change over time);
        * Scrum Master (may change over time);
        * Project team (may change over time);
        * Project Status (Planned / Inception / Elaboration / Construction / Transition / Warranty / Closed);
        * Budget (monetary amount available for resource spending).
    2. Must be a Manager Profile.

### US986:
As a Project Manager, I want to create an activity in a project.

### US985:
As a Project Manager, I want to edit a project information.

* Acceptance Criteria:
    1. Must be a Project Manager.

### US984:
As a User, I want to search for projects.

* Acceptance Criteria:
    1. Searchable fields:
        * Code
        * Name
        * Start month
        * Customer
        * Business Sector
        * Project status
    2. All users can search for projects but should only have access to projects they participate in
    3. Manager profile has access to all project in the project search

### US983:
As a Product Owner, I want to create a User Story.

### US982:
As a Team Member, I want to define Sprint Backlog.

### US981:
As a Team Member, I want to define the effort for each activity.

### US980:
As a Team Member, I want to create a task.

### US979:
As a Team Member, I want to register work on a task.

### US978:
As a User, I want to update a task.

### US977:
As a User, I want to log a time record on a task.

### US976:
As a Manager, I want to associate resources with projects.

* Acceptance Criteria:
    1. A task must have at least the following information:
    2. End date should be later than start date.
    3. Cost per hour must be a positive integer.
    4. The percentage of allocation must be > 0% and <= 100%.
    5. User total percentage allocation must be <= 100% across all projects.

### US975:
As a Manager, I want to assign a task to a User.

* Acceptance Criteria:
    1. Total tasks time assigned to User within project should not be greater than User's time allocated to that project.

### US974:
As a User, I want to view the projects' ongoing activities.

* Acceptance Criteria:
    1. The viewing modes can be used interchangeably or simultaneously.

### US973:
As a User, I want to change between two different viewing modes.

### US972:
As a User, I want to generate an allocations report.

### US971:
As a User, I want to generate a KPI report.

### US970:
As a User, I want to create a scrum ceremony minute in the system.

### US969:
As a Manager, I want to import projects created in a legacy platform.

## Index
| User Story | Chapter | Implemented in |
|------------|---------|----------------|
| US999      | 2.1     |                |
| US998      | 2.1     |                |
| US997      | 2.2.1   |                |
| US996      | 2.2.2   |                |
| US995      | 2.3.1   | US002          |
| US994      | 2.3.2   |                |
| US993      | 2.3.3   | US004          |
| US992      | 2.3.3   | US003          |
| US991      | 2.3.3   | US003          |
| US990      | 2.3.3   | US006          |
| US989      | 2.3.3   | US005          |
| US988      | 2.3.3   |                |
| US987      | 2.4.1   |                |
| US986      | 2.4.1   |                |
| US985      | 2.4.1   |                |
| US984      | 2.4.2   | US016          |
| US983      | 2.4.3   |                |
| US982      | 2.4.3   |                |
| US981      | 2.4.3   |                |
| US980      | 2.4.3   |                |
| US979      | 2.4.3   |                |
| US978      | 2.4.4   |                |
| US977      | 2.4.4   |                |
| US976      | 2.4.5   |                |
| US975      | 2.4.5   |                |
| US974      | 2.4.6   |                |
| US973      | 2.4.6   |                |
| US972      | 2.4.7   |                |
| US971      | 2.4.8   |                |
| US970      | 2.5     |                |
| US969      | 2.6     |                |
