package org.switch2022.project.domain.project;

import org.switch2022.project.ddd.DomainEntity;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryID;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The ProductBacklog class represents a product backlog in the domain model.
 * It implements the DomainEntity interface with the identity of the product backlog being the project code.
 */
public class ProductBacklog implements DomainEntity<ProjectCode> {
    private final ProjectCode projectCode;
    private final LinkedList<UserStoryID> userStoryIDList;

    /**
     * Instantiates a new Product backlog.
     *
     * @param projectCode the project code
     * @throws IllegalArgumentException if the projectCode parameter is null
     */
    protected ProductBacklog(ProjectCode projectCode) {
        if (projectCode == null) {
            throw new IllegalArgumentException("Project Code can not be null");
        }

        this.projectCode = projectCode;
        this.userStoryIDList = new LinkedList<>();
    }

    /**
     * Instantiates a new Product backlog.
     *
     * @param projectCode the project code
     * @param userStoryIDList the list of user story id
     * @throws IllegalArgumentException if the projectCode parameter is null
     * @throws IllegalArgumentException if the userStoryIDList parameter is null
     */
    protected ProductBacklog(ProjectCode projectCode, List<UserStoryID> userStoryIDList) {
        if (projectCode == null) {
            throw new IllegalArgumentException("Project Code can not be null");
        }

        if (userStoryIDList == null) {
            throw new IllegalArgumentException("Product Backlog can not be null");
        }

        this.projectCode = projectCode;
        this.userStoryIDList = new LinkedList<>(userStoryIDList);
    }

    /**
     * Add user story.
     *
     * @param userStoryID the user story identification.
     * @return true if user story is successfully added or false otherwise.
     */
    public boolean addUserStory(UserStoryID userStoryID) {
        if (userStoryIDList.contains(userStoryID)) {
            return false;
        }
        return userStoryIDList.add(userStoryID);
    }

    /**
     * Remove user story.
     *
     * @param userStoryID the user story identification.
     */
    public void removeUserStory(UserStoryID userStoryID) {
        this.userStoryIDList.remove(userStoryID);
    }

    /**
     * Gets User Story List.
     *
     * @return the User Story list
     */
    public List<UserStoryID> getUserStorylist() {
        return Collections.unmodifiableList(userStoryIDList);
    }

    /**
     * Returns the identity of the product backlog, which is the project code.
     *
     * @return the project code
     */
    @Override
    public ProjectCode identity() {
        return this.projectCode;
    }

    /**
     * Checks if this Product Backlog is the same as the specified object.
     *
     * @param object the object to compare
     * @return true if the ProductBacklog is the same as the specified object, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (!(object instanceof ProductBacklog)) {
            return false;
        }

        ProductBacklog that = (ProductBacklog) object;

        return this.userStoryIDList.equals(that.userStoryIDList);
    }

    /**
     * Checks if this product backlog is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this product backlog, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductBacklog that = (ProductBacklog) o;
        return this.projectCode.equals(that.projectCode);
    }

    /**
     * Generates the hash code value for the product backlog.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode);
    }

}
