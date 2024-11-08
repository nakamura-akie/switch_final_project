package org.switch2022.project.datamodel.jpa.project;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "productBacklog")
public class ProductBacklogJPA {
    @Id
    private String projectCode;
    private String userStoryCodeList;

    protected ProductBacklogJPA() {}

    /**
     * Constructs a new ProductBacklogJPA object with the specified project code and user story code list.
     *
     * @param projectCode       the project code
     * @param userStoryCodeList the user story code list
     */
    public ProductBacklogJPA(String projectCode, String userStoryCodeList) {
        this.projectCode = projectCode;
        this.userStoryCodeList = userStoryCodeList;
    }

    /**
     * Returns the project code.
     *
     * @return the project code
     */
    public String getProjectCodeValue() {
        return projectCode;
    }

    /**
     * Returns the user story code list.
     *
     * @return the user story code list
     */
    public String getUserStoryCodeList() {
        return userStoryCodeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductBacklogJPA that = (ProductBacklogJPA) o;
        return this.projectCode.equals(that.projectCode) && this.userStoryCodeList.equals(that.userStoryCodeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, userStoryCodeList);
    }
}
