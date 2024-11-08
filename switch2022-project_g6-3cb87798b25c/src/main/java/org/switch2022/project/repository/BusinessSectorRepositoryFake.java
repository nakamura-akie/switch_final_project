package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.repository.interfaces.BusinessSectorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A fake implementation of the {@link BusinessSectorRepository} interface that uses an in-memory list to store
 * {@link BusinessSector} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class BusinessSectorRepositoryFake implements BusinessSectorRepository {

    private static final List<BusinessSector> businessSectorList = new ArrayList<>();

    /**
     * Saves a business sector to the repository, if it is not already in the repository.
     *
     * @param businessSector the business sector to be saved
     * @return the saved business sector
     * @throws IllegalArgumentException if the given business sector already exists in the repository
     */
    @Override
    public BusinessSector save(BusinessSector businessSector) {
        if (businessSectorList.contains(businessSector)) {
            throw new IllegalArgumentException("Business Sector already exists.");
        }
        businessSectorList.add(businessSector);
        return businessSector;
    }

    /**
     * Retrieves all business sectors from the repository.
     *
     * @return An iterable collection of all business sectors
     */
    @Override
    public Iterable<BusinessSector> findAll() {
        return new ArrayList<>(businessSectorList);
    }

    /**
     * Retrieves a business sector with the given business sector name.
     *
     * @param businessSectorName the business sector name to search for
     * @return An optional containing the business sector if found, an empty optional otherwise
     */
    @Override
    public Optional<BusinessSector> findById(BusinessSectorName businessSectorName) {
        for (BusinessSector businessSector : businessSectorList) {
            if (businessSector.identity().equals(businessSectorName)) {
                return Optional.of(businessSector);
            }
        }
        return Optional.empty();
    }

    /**
     * Confirms if a business sector exists with the given business sector name.
     *
     * @param businessSectorName the business sector name to search for
     * @return true if the business sector exists, false otherwise
     */
    @Override
    public boolean existsById(BusinessSectorName businessSectorName) {
        for (BusinessSector businessSector : businessSectorList) {
            if (businessSector.identity().equals(businessSectorName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete all business sectors from the repository.
     */
    @Override
    public void deleteAll() {
        businessSectorList.clear();
    }

    /**
     * Deletes a business sector with the given id (business sector name).
     *
     * @param id the business sector name to search for
     * @throws UnsupportedOperationException when method is called because it has not
     *                                       been implemented yet
     */
    @Override
    public void deleteById(BusinessSectorName id) {
        throw new UnsupportedOperationException("Business Sector Repository doesn't support the deleteByID method.");
    }
}
