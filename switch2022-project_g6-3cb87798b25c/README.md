# Documentation

## Overall project

### Requirements
* Use Case Model
	* [Use Case Diagram](./docs/modelling_design/overall_project/requirements/use_case_model/use_case_diagram/use_case_diagram.md)
	* [User Stories](./docs/modelling_design/overall_project/requirements/use_case_model/user_stories.md)
	* [System Sequence Diagram](./docs/modelling_design/overall_project/requirements/use_case_model/system_sequence_diagram/system_sequence_diagram.md)
* [Supplementary Specifications](./docs/modelling_design/overall_project/requirements/supplementary_specifications.md)
* [Glossary](./docs/modelling_design/overall_project/requirements/glossary.md)
* [Customer Specification Document](./docs/modelling_design/overall_project/requirements/customer_specification_document.md)

### Analysis
* [Domain Model](./docs/modelling_design/overall_project/analysis/domain_model.md)
* [Domain Model - Domain Driven Design](./docs/modelling_design/overall_project/domain_driven_design/domain_model_ddd.md)

### Architecture
* [Views](docs/modelling_design/overall_project/architecture/architecture_documentation.md)

## User Stories
* [Sprint 1](docs/modelling_design/user_stories/sprint1/sprint1_artifacts.md)
* [Sprint 2](docs/modelling_design/user_stories/sprint2/sprint2_artifacts.md)
* [Sprint 3](docs/modelling_design/user_stories/sprint3/sprint3_artifacts.md)
* [Sprint 4](docs/modelling_design/user_stories/sprint4/sprint4_artifacts.md)
* [Sprint 5](docs/modelling_design/user_stories/sprint5/sprint5_artifacts.md)
* [Sprint 6](docs/modelling_design/user_stories/sprint6/sprint6_artifacts.md)
* [Sprint 7](docs/modelling_design/user_stories/sprint7/sprint7_artifacts.md)

## REST API Documentation
* [REST API Docs](docs/api_documentation.md)

---

# README

This is the repository template used for students in SWitCH Projects.

## Java source files

Java source and test files are located in folder src.

## Maven file

Pom.xml file controls the project build.

### Notes
In this file, DO NOT EDIT the following elements:

* groupID
* artifactID
* version
* properties

Students can only add dependencies to the specified section of this file.

## IntelliJ Idea IDE files

The following folder is solely used by Intellij Idea IDE :

* .idea

# How was the .gitignore file generated?
.gitignore file was generated based on https://www.gitignore.io/ with the following keywords:

  - Java
  - Maven
  - Eclipse
  - NetBeans
  - Intellij
  - VisualStudioCode
  - macOS
  - Windows
  - Linux

# Who do I talk to?
In case you have any problem, please email Nuno Bettencourt (nmb@isep.ipp.pt).

# How do I use Maven?

## How to run unit tests?

Execute the "test" goals.

```shell
$ mvn test
```
## How to generate the javadoc for source code?

Execute the "javadoc:javadoc" goal.

```shell
$ mvn javadoc:javadoc
```
This generates the source code javadoc in folder "target/site/apidocs/index.html".

## How to generate the javadoc for test cases code?

Execute the "javadoc:test-javadoc" goal.

```shell
$ mvn javadoc:test-javadoc
```
This generates the test cases javadoc in folder "target/site/testapidocs/index.html".

## How to generate Jacoco's Code Coverage Report?

Execute the "jacoco:report" goal.

```shell
$ mvn test jacoco:report
```

This generates a jacoco code coverage report in folder "target/site/jacoco/index.html".

## How to generate PIT Mutation Code Coverage?

Execute the "org.pitest:pitest-maven:mutationCoverage" goal.

```shell
$ mvn test org.pitest:pitest-maven:mutationCoverage
```
This generates a PIT Mutation coverage report in folder "target/pit-reports/YYYYMMDDHHMI".

## How to combine different maven goals in one step?

You can combine different maven goals in the same command. For example, to locally run your project just like on jenkins, use:

```shell
$ mvn clean test jacoco:report org.pitest:pitest-maven:mutationCoverage
```
## How to perform a faster pit mutation analysis?

Do not clean and build => remove "clean"

Reuse the previous mutation report => add "-Dsonar.pitest.mode=reuseReport"

Use more threads to perform the analysis. The number is dependent on each computer CPU => add "-Dthreads=4"

Temporarily remove timestamps from reports.

Example:
```shell
$ mvn test jacoco:report org.pitest:pitest-maven:mutationCoverage -DhistoryInputFile=target/fasterPitMutationTesting-history.txt -DhistoryOutputFile=target/fasterPitMutationTesting-history.txt -Dsonar.pitest.mode=reuseReport -Dthreads=4 -DtimestampedReports=false
```







    

