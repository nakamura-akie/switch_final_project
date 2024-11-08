package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.ResourceRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A fake implementation of the {@link ResourceRepository} interface for testing purposes.
 * This repository stores resources in an in-memory list.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class ResourceRepositoryFake implements ResourceRepository {

    private static final List<Resource> resourceList = new ArrayList<>();

    public ResourceRepositoryFake() {
        //needed for JPA
    }

    /**
     * Saves a resource to the repository.
     *
     * @param entity The resource to save.
     * @return The saved resource.
     */
    @Override
    public Resource save(Resource entity) {
        if (!resourceList.contains(entity)) {
            resourceList.add(entity);
        }
        return entity;
    }

    /**
     * Retrieves all resources from the repository.
     *
     * @return An iterable containing all resources in the repository.
     */
    @Override
    public Iterable<Resource> findAll() {
        return new ArrayList<>(resourceList);
    }

    /**
     * Retrieves a resource with a given id, in this case a ResourceID.
     *
     * @param id The ID of the resource to retrieve.
     * @return An optional containing the resource if found, or an empty optional if not found.
     */
    @Override
    public Optional<Resource> findById(ResourceID id) {
        for (Resource resource : resourceList) {
            if (resource.identity().equals(id)) {
                return Optional.of(resource);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a resource with the given ID exists in the repository.
     *
     * @param id The ID of the resource to check.
     * @return true if a resource with the given ID exists, false otherwise.
     */
    @Override
    public boolean existsById(ResourceID id) {
        for (Resource resource : resourceList) {
            if (resource.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves resources by account email and current date.
     *
     * @param accountEmail The email of the account.
     * @param currentDate  The current date.
     * @return An iterable containing the resources that match the account email and current date.
     */
    @Override
    public Iterable<Resource> findByAccountEmailAndCurrentDate(Email accountEmail, LocalDate currentDate) {
        return resourceList.stream()
                .filter(rc -> rc.hasAccount(accountEmail)
                        && rc.resourceVerificationOfDate(currentDate))
                .collect(Collectors.toList());
    }

    /**
     * Finds all resources with the given project code and  allocated at the search date provided.
     *
     * @param projectCode the project code to search for
     * @param searchDate  the date to search for
     * @return An iterable collection of all matching resources
     */
    @Override
    public Iterable<Resource> findByProjectCodeAndIsAllocatedNow(ProjectCode projectCode, LocalDate searchDate) {
        return resourceList.stream().filter(rc -> rc.identity().
                getProjectCode().equals(projectCode)
                && rc.isAllocated(searchDate))
                .collect(Collectors.toList());
    }

    /**
     * Deletes all resources from the repository.
     */
    @Override
    public void deleteAll() {
        resourceList.clear();
    }

    /**
     * Deletes a resource with a given resourceID.
     *
     * @param id The ID of the resource to delete.
     */
    @Override
    public void deleteById(ResourceID id) {
        resourceList.removeIf(resource -> resource.identity().equals(id));
    }

    /**
     * Retrieves all resources with a given email.
     *
     * @param email the email to search for
     * @return An iterable containing the resources that match the email.
     */
    @Override
    public Iterable<Resource> findByEmail(Email email) {
        return resourceList.stream()
                .filter(rc -> rc.identity().hasEmail(email))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves resources by project code and project role.
     *
     * @param projectCode The project code to match.
     * @param projectRole The project role to match.
     * @return An iterable containing the resources that match the project code and project role.
     */
    @Override
    public Iterable<Resource> findByProjectCodeAndProjectRole(ProjectCode projectCode, ProjectRole projectRole) {
        return resourceList.stream()
                .filter(rc -> rc.identity().getProjectCode().equals(projectCode)
                        && rc.getRoleInProject().equals(projectRole))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all resources with a given project code.
     *
     * @param projectCode The project code to match.
     * @return An iterable containing the resources that match the project code.
     */
    @Override
    public Iterable<Resource> findByProjectCode(ProjectCode projectCode) {
        return resourceList.stream()
                .filter(rc -> rc.identity().getProjectCode().equals(projectCode))
                .collect(Collectors.toList());
    }

}
