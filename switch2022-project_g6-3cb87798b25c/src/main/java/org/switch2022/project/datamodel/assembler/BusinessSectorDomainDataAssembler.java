package org.switch2022.project.datamodel.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.BusinessSectorJPA;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.businesssector.BusinessSectorFactory;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

@Component
public class BusinessSectorDomainDataAssembler {

    private BusinessSectorFactory businessSectorFactory;

    /**
     * Constructs a BusinessSectorDomainDataAssembler with the specified BusinessSectorFactory.
     *
     * @param businessSectorFactory the {@link BusinessSectorFactory} to be used for creating BusinessSector objects.
     * @throws IllegalArgumentException if the BusinessSectorFactory is null.
     */
    @Autowired
    public BusinessSectorDomainDataAssembler(BusinessSectorFactory businessSectorFactory) {
        if (businessSectorFactory == null) {
            throw new IllegalArgumentException("Business Sector Factory cannot be null.");
        }
        this.businessSectorFactory = businessSectorFactory;
    }

    /**
     * Converts a BusinessSector domain object to its corresponding {@link BusinessSectorJPA} entity.
     *
     * @param businessSector the BusinessSector domain object to be converted.
     * @return the corresponding JPA entity.
     */
    public BusinessSectorJPA toData(BusinessSector businessSector) {
        BusinessSectorName businessSectorName = businessSector.identity();
        String businessSectorNameValue = businessSectorName.getBusinessSectorNameValue();
        return new BusinessSectorJPA(businessSectorNameValue);
    }

    /**
     * Converts a BusinessSectorJPA entity to its corresponding {@link BusinessSector} domain object.
     *
     * @param businessSectorJPA the JPA entity to be converted.
     * @return the corresponding BusinessSector domain object.
     */
    public BusinessSector toDomain(BusinessSectorJPA businessSectorJPA) {
        BusinessSectorName businessSectorName = new BusinessSectorName(businessSectorJPA.getBusinessSectorName());
        return businessSectorFactory.createBusinessSector(businessSectorName);
    }
}
