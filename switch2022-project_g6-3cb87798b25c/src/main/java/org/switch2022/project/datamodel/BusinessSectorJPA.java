package org.switch2022.project.datamodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "business_sectors")
public class BusinessSectorJPA {
    @Id
    private String businessSectorName;

    protected BusinessSectorJPA() {
    }

    /**
     * Constructs a new BusinessSectorJPA object with the specified business sector name.
     *
     * @param businessSectorName the name of the business sector
     */
    public BusinessSectorJPA(String businessSectorName) {
        this.businessSectorName = businessSectorName;
    }

    /**
     * Returns the name of the business sector.
     *
     * @return the name of the business sector
     */
    public String getBusinessSectorName() {
        return this.businessSectorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessSectorJPA that = (BusinessSectorJPA) o;
        return Objects.equals(businessSectorName, that.businessSectorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessSectorName);
    }
}
