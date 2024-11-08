package org.switch2022.project.ddd;

/**
 * The DomainEntity interface represents a domain entity in the domain-driven design (DDD) approach.
 * It defines methods for retrieving the entity's identity and checking equality with another object.
 *
 * @param <Id> the type of the entity's identifier, extending DomainID
 */
public interface DomainEntity<Id extends DomainID> {

    /**
     * Retrieves the identity of the domain entity.
     *
     * @return the entity's identity
     */
    Id identity();

    /**
     * Checks if the domain entity is the same as the specified object.
     *
     * @param object the object to compare
     * @return true if the entity is the same as the object, false otherwise
     */
    boolean sameAs(Object object);
}
