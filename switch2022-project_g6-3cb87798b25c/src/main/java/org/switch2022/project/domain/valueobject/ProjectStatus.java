package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

/**
 * The ProjectStatus enum represents the status of a project in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public enum ProjectStatus implements ValueObject {
    PLANNED,
    INCEPTION,
    ELABORATION,
    CONSTRUCTION,
    TRANSITION,
    WARRANTY,
    CLOSED
}
