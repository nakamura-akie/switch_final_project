package org.switch2022.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.BusinessSectorJPA;
import org.switch2022.project.datamodel.assembler.BusinessSectorDomainDataAssembler;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.repository.interfaces.BusinessSectorRepository;
import org.switch2022.project.repository.jpa.BusinessSectorRepositoryJPA;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence implementation of the {@link BusinessSectorRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link BusinessSectorRepositoryJPA} interface to store business sectors.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class BusinessSectorRepositoryPersistence implements BusinessSectorRepository {

    private final BusinessSectorRepositoryJPA businessSectorRepositoryJPA;
    private final BusinessSectorDomainDataAssembler businessSectorDomainDataAssembler;

    /**
     * Instantiates a BusinessSectorRepositoryPersistence.
     *
     * @param businessSectorRepositoryJPA       the business sector JPA repository
     * @param businessSectorDomainDataAssembler the business sector domain data assembler
     * @throws IllegalArgumentException if either the business sector JPA repository or the
     *                                  business sector domain data assembler are null
     */
    @Autowired
    public BusinessSectorRepositoryPersistence(BusinessSectorRepositoryJPA businessSectorRepositoryJPA,
                                               BusinessSectorDomainDataAssembler businessSectorDomainDataAssembler) {
        if (businessSectorRepositoryJPA == null || businessSectorDomainDataAssembler == null) {
            throw new IllegalArgumentException("Business Sector Repository JPA and Business Sector Assembler cannot " +
                    "be null.");
        }
        this.businessSectorRepositoryJPA = businessSectorRepositoryJPA;
        this.businessSectorDomainDataAssembler = businessSectorDomainDataAssembler;
    }

    /**
     * Saves a business sector entity.
     *
     * @param businessSector the business sector to be saved
     * @return the saved business sector
     */
    @Override
    public BusinessSector save(BusinessSector businessSector) {
        BusinessSectorJPA businessSectorJPA =
                businessSectorDomainDataAssembler.toData(businessSector);
        BusinessSectorJPA savedBusinessSectorJPA =
                businessSectorRepositoryJPA.save(businessSectorJPA);
        return businessSectorDomainDataAssembler.toDomain(savedBusinessSectorJPA);
    }

    /**
     * Retrieves all business sectors in the JPA repository.
     *
     * @return An iterable collection of all business sectors in the repository
     */
    @Override
    public List<BusinessSector> findAll() {
        List<BusinessSectorJPA> businessSectorListJPA = (List<BusinessSectorJPA>) businessSectorRepositoryJPA.findAll();
        return businessSectorListJPA.stream()
                .map(businessSectorDomainDataAssembler::toDomain)
                .collect(Collectors.toList());

    }

    /**
     * Retrieves a business sector with the given business sector name.
     *
     * @param businessSectorName the business sector name to search for
     * @return an Optional containing the business sector entity if found,
     *          an empty optional otherwise
     */
    @Override
    public Optional<BusinessSector> findById(BusinessSectorName businessSectorName) {
        return this.businessSectorRepositoryJPA.findById(businessSectorName.getBusinessSectorNameValue())
                .map(businessSectorDomainDataAssembler::toDomain);
    }

    /**
     * Confirms if a business sector exists with the given business sector name.
     *
     * @param businessSectorName the business sector name to search for
     * @return true if the business sector exists, false otherwise
     */
    @Override
    public boolean existsById(BusinessSectorName businessSectorName) {
        return this.businessSectorRepositoryJPA.existsById(businessSectorName.getBusinessSectorNameValue());
    }

    /**
     * Deletes all business sectors from the JPA repository
     */
    @Override
    public void deleteAll() {
        this.businessSectorRepositoryJPA.deleteAll();
    }

    /**
     * Deletes a business sector with the given id (business sector name).
     *
     * @param id the business sector name to search for
     */
    @Override
    public void deleteById(BusinessSectorName id) {
        this.businessSectorRepositoryJPA.deleteById(id.getBusinessSectorNameValue());
    }
}
