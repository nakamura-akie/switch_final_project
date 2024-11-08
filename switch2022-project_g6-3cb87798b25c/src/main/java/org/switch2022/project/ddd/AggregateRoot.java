package org.switch2022.project.ddd;


/**
 * The AggregateRoot interface represents the root entity of an aggregate in the domain-driven design (DDD) approach.
 * It extends the DomainEntity interface, specifying the type of the aggregate's identifier.
 *
 * @param <Id> the type of the aggregate's identifier, extending DomainID
 */
public interface AggregateRoot<Id extends DomainID> extends DomainEntity<Id> {
}
