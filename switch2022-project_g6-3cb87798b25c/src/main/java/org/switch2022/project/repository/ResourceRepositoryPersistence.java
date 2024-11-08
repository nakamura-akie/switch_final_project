package org.switch2022.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.assembler.ResourceDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.resource.ResourceIdJPA;
import org.switch2022.project.datamodel.jpa.resource.ResourceJPA;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.ResourceRepository;
import org.switch2022.project.repository.jpa.ResourceRepositoryJPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence implementation of the {@link ResourceRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link ResourceRepositoryJPA} interface to store resources.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class ResourceRepositoryPersistence implements ResourceRepository {

    private final ResourceRepositoryJPA resourceJpaRepository;
    private final ResourceDomainDataAssembler resourceAssembler;

    /**
     * Instantiates a new ResourceRepositoryPersistence
     *
     * @param resourceJpaRepository the resource JPA repository
     * @param resourceAssembler     the resource domain data assembler
     * @throws IllegalArgumentException if either the resource JPA repository or
     *                                  the domain data assembler are null
     */
    @Autowired
    public ResourceRepositoryPersistence(ResourceRepositoryJPA resourceJpaRepository,
                                         ResourceDomainDataAssembler resourceAssembler) {
        if (resourceAssembler == null) {
            throw new IllegalArgumentException("ResourceDomainDataAssembler cannot be null.");
        }
        if (resourceJpaRepository == null) {
            throw new IllegalArgumentException("ResourceRepositoryJPA cannot be null.");
        }
        this.resourceJpaRepository = resourceJpaRepository;
        this.resourceAssembler = resourceAssembler;
    }

    /**
     * Persists a new resource entity.
     *
     * @param entity the resource to be saved
     * @return the saved resource
     */
    @Override
    public Resource save(Resource entity) {
        ResourceJPA data = resourceAssembler.toData(entity);
        ResourceJPA savedAccountJpa = resourceJpaRepository.save(data);
        return resourceAssembler.toDomain(savedAccountJpa);
    }

    /**
     * Returns all resources in the JPA repository.
     *
     * @return an iterable collection of the resources in the repository
     */
    @Override
    public Iterable<Resource> findAll() {
        List<ResourceJPA> data = (List<ResourceJPA>) resourceJpaRepository.findAll();

        return (data.stream()
                .map(resourceAssembler::toDomain)
                .collect(Collectors.toList()));
    }

    /**
     * Returns the resource with the given id, in this case a resourceID
     *
     * @param id the resourceID to search for
     * @return An optional containing the resource entity if found, an empty optional otherwise
     */
    @Override
    public Optional<Resource> findById(ResourceID id) {
        ResourceIdJPA idJpa = new ResourceIdJPA(
                id.getResourceEmail().getEmailValue(),
                id.getProjectCode().getProjectCodeValue(),
                id.getPeriodOfAllocation().getStartDate().toString(),
                id.getPeriodOfAllocation().getEndDate().toString());

        return resourceJpaRepository.findByResourceIDJpa(idJpa)
                .map(resourceAssembler::toDomain);
    }

    /**
     * Confirms if a resource exists with the given id, in this case a resourceID.
     *
     * @param id the resourceID to search for
     * @return true if the resource exists, false otherwise
     */
    @Override
    public boolean existsById(ResourceID id) {
        ResourceIdJPA idJpa = new ResourceIdJPA(
                id.getResourceEmail().getEmailValue(),
                id.getProjectCode().getProjectCodeValue(),
                id.getPeriodOfAllocation().getStartDate().toString(),
                id.getPeriodOfAllocation().getEndDate().toString());

        return resourceJpaRepository.existsByResourceIDJpa(idJpa);
    }

    /**
     * Deletes all resources from the JPA repository.
     */
    @Override
    public void deleteAll() {
        resourceJpaRepository.deleteAll();
    }

    /**
     * Deletes a resource from the repository with the given id, in this case a resourceID
     *
     * @param id the resourceID to search for
     */
    @Override
    public void deleteById(ResourceID id) {
        ProjectCode resourceProjectCode = id.getProjectCode();
        Email resourceEmail = id.getResourceEmail();
        LocalDate resourceAllocationStartDate = id.getPeriodOfAllocation().getStartDate();
        LocalDate resourceAllocationEndDate = id.getPeriodOfAllocation().getEndDate();

        ResourceIdJPA resourceIdJPA = new ResourceIdJPA();

        resourceIdJPA.setProjectCode(resourceProjectCode.getProjectCodeValue());
        resourceIdJPA.setEmail(resourceEmail.getEmailValue());
        resourceIdJPA.setStartDate(resourceAllocationStartDate.toString());
        resourceIdJPA.setEndDate(resourceAllocationEndDate.toString());

        resourceJpaRepository.deleteById(resourceIdJPA);
    }

    /**
     * Finds all resources with the given email
     *
     * @param email the email to search for
     * @return an iterable collection of matching resources
     */
    @Override
    public Iterable<Resource> findByEmail(Email email) {
        List<ResourceJPA> data = (List<ResourceJPA>) resourceJpaRepository
                .findByResourceIDJpaEmail(email.getEmailValue());

        return (data.stream()
                .map(resourceAssembler::toDomain)
                .collect(Collectors.toList()));
    }

    /**
     * Finds all resources that have the given project code and project role.
     *
     * @param projectCode the project code to search for
     * @param projectRole the project role to search for
     * @return an iterable collection of matching resources
     */
    @Override
    public Iterable<Resource> findByProjectCodeAndProjectRole(ProjectCode projectCode, ProjectRole projectRole) {
        List<ResourceJPA> data =
                (List<ResourceJPA>) resourceJpaRepository
                        .findByResourceIDJpaProjectCodeAndProjectRole(
                                projectCode.getProjectCodeValue(),
                                projectRole.toString());

        return (data.stream()
                .map(resourceAssembler::toDomain)
                .collect(Collectors.toList()));
    }

    /**
     * Finds all resouces with the given project code
     *
     * @param projectCode the project code to search for
     * @return an iterable collection of matching resources
     */
    @Override
    public Iterable<Resource> findByProjectCode(ProjectCode projectCode) {
        List<ResourceJPA> data = (List<ResourceJPA>) resourceJpaRepository
                .findByResourceIDJpaProjectCode(projectCode.getProjectCodeValue());

        return (data.stream()
                .map(resourceAssembler::toDomain)
                .collect(Collectors.toList()));
    }

    /**
     * Finds all resources with the provided email, allocated at the time of the search
     *
     * @param accountEmail the email to search for
     * @param currentDate  the date to search for
     * @return an iterable collection of matching resources
     */
    @Override
    public Iterable<Resource> findByAccountEmailAndCurrentDate(Email accountEmail, LocalDate currentDate) {
        List<ResourceJPA> data = (List<ResourceJPA>) resourceJpaRepository
                .findByResourceIDJpaEmailAndResourceIDJpaStartDateAfterAndResourceIDJpaEndDateIsBefore(
                        accountEmail.getEmailValue(), currentDate.toString(), currentDate.toString());

        return data.stream()
                .map(resourceAssembler::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds all resources with the given project code and  allocated at the search date provided.
     *
     * @param projectCode the project code to search for
     * @param searchDate  the current date to compare
     * @return An iterable collection of matching resources
     */
    @Override
    public Iterable<Resource> findByProjectCodeAndIsAllocatedNow(ProjectCode projectCode, LocalDate searchDate) {
        List<ResourceJPA> data = resourceJpaRepository
                .findAllocatedResourcesByProjectIdAndSearchTime(projectCode.getProjectCodeValue(),
                        searchDate.toString());

        return data.stream()
                .map(resourceAssembler::toDomain)
                .collect(Collectors.toList());
    }
}
