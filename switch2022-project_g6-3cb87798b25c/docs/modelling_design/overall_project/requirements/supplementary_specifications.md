# Supplementary Specification (FURPS+)

## FUNCTIONALITY
Features typically not captured in the user scenarios.

### Auditing
* N/A.

###  Licensing
* N/A.

###  Installation
* An Administrator profile should be automatically created at the time of installation.

###  Registration
* On registration, an account is automatically associated with the User profile.

###  Localization
* N/A.

###  Email
* N/A.

###  Help
* N/A.

###  Printing
* N/A.

###  Reporting
* System should determine Burndown Chart
* The KPI reports may be generated on screen or exported to an XML file.

###  Security
* Only Team Members, Product Owners and Sprint Masters allocated to sprint can work on sprint's activities
* The KPI report, is made available to the director, the project manager and PO
* The system should allow the configuration of profiles associated with users, to restrict access to features by assigning permissions to the profiles
* Passwords should not be openly saved to the database

###  System management
* N/A.

###  Workflow
* System should return unfinished stories to product backlog

---

## USABILITY
Regards/Evaluates the user interface.

### Prevention of errors entered by the user
* N/A.

### Accessibility
* All text as well as date and decimal formats must be configured so that you can have more than one language in the application (changed in runtime)

### Aesthetics and design
* N/A.

### Interface consistency
* N/A.

---

## RELIABILITY

Refers to the integrity and conformity.

### Frequency and severity of system failures
* N/A.

### Disaster recovery possibility
* N/A.

### Accuracy of some calculus
* N/A.

---

## PERFORMANCE
Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, set-up time, memory consumption, CPU usage, load capacity.

### Response time
* System should close session automatically after more than 30 minutes of inactivity

### Start-up time
* N/A.

### Recovery time
* N/A.

### Setup time
* N/A.

### Shutdown time
* N/A.

### Throughput
* N/A.

---

## SUPPORTABILITY
Evaluates characteristics concerned with: testability, adaptability, maintainability, compatibility, installability, scalability, localizability.

### Testability
* N/A.

### Adaptability
* The layout should adapt and display the information in a different layout to maximize the usability and user experience of the application
* The user interface should adapt the design of the forms to the type of device and screen size

### Maintainability
* N/A.

### Compatibility
* * The application should run on all platforms for which there exists a **Java Virtual Machine**.

### Configurability
* N/A.

### Instability
* N/A.

### Scalability
* N/A.

### Localizability
* N/A.

### Maintainability
* N/A.

---

## + (Plus)
Additional categories typically related with constraints,

### Design Requirements
* All user operations, as well as system messages, either warning or error, must be saved in log files on the server, identified with at least the following levels: debug, info, warn, error.

### Implementation Requirements
* Product Owner and Sprint Master can't change during sprint
* It is a scrum best practice that no resource should be allocated to more than a project at a given time. But this may not be feasible for specific technical and/or short duration tasks

### Interface Requirements
* The project activity list should have two distinct viewing modes, which can be used interchangeably or simultaneously: Table-shaped and Gantt format.

### Physical Requirements
* N/A.