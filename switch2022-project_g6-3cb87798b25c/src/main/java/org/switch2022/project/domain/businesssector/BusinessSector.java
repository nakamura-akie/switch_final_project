package org.switch2022.project.domain.businesssector;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

import java.util.Objects;

/**
 * The BusinessSector class represents a business sector in the domain model.
 * It implements the AggregateRoot interface with the identity of the business sector being the business sector name.
 */
public class BusinessSector implements AggregateRoot<BusinessSectorName> {

    private final BusinessSectorName businessSectorName;

    /**
     * Constructs a BusinessSector object with the specified name.
     *
     * @param businessSectorName the name of the business sector
     * @throws IllegalArgumentException if the businessSectorName parameter is null
     */
    protected BusinessSector(BusinessSectorName businessSectorName) {
        if (businessSectorName == null) {
            throw new IllegalArgumentException("Invalid Business Sector name.");
        }
        this.businessSectorName = businessSectorName;
    }

    /**
     * Returns the identity of the business sector, which is the business sector name.
     *
     * @return the business sector name
     */
    @Override
    public BusinessSectorName identity() {
        return this.businessSectorName;
    }

    /**
     * Returns the name of the business sector.
     *
     * @return the business sector name
     */
    public String getBusinessSectorName() {
        return this.businessSectorName.getBusinessSectorNameValue();
    }

    /**
     * Checks if this business sector is the same as the given object.
     *
     * @param object the object to compare
     * @return true if the object is the same as this business sector, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof BusinessSector) {
            BusinessSector that = (BusinessSector) object;

            return this.businessSectorName.equals(that.businessSectorName);
        }
        return false;
    }

    /**
     * Checks if this business sector is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this business sector, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessSector businessSector = (BusinessSector) o;
        return Objects.equals(businessSectorName, businessSector.businessSectorName);
    }

    /**
     * Generates the hash code value for the business sector.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(getBusinessSectorName());
    }
}
