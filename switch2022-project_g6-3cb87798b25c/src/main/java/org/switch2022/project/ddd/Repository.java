package org.switch2022.project.ddd;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The Repository interface represents a generic repository for managing domain entities in
 * the domain-driven design (DDD) approach.
 *
 * @param <T> the type of the aggregate root entity
 * @param <Id> the type of the entity's identifier
 */
@Component
public interface Repository<T extends AggregateRoot<Id>, Id extends DomainID> {

    /**
     * Saves a given entity.
     *
     * @param entity the entity to save; must not be null.
     * @return the saved entity; will never be null.
     * @throws IllegalArgumentException in case the given entity is null.
     */
    T save(T entity);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Retrieves an entity by its id.
     *
     * @param id the id of the entity; must not be null.
     * @return the entity with the given id or an empty Optional if none found.
     * @throws IllegalArgumentException in case the given id is null.
     */
    Optional<T> findById(Id id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id the id to check; must not be null.
     * @return true if an entity with the given id exists, false otherwise.
     * @throws IllegalArgumentException in case the given id is null.
     */
    boolean existsById(Id id);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();

    /**
     * Deletes the entity with the given id.
     *
     * @param id the id of the entity to delete; must not be null.
     * @throws IllegalArgumentException in case the given id is null.
     */
    void deleteById(Id id);
}