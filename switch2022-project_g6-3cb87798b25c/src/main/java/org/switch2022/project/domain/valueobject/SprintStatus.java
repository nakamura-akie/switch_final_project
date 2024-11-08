package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

/**
 * The SprintStatus enum represents the status of a sprint in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public enum SprintStatus implements ValueObject {
    PLANNED,
    OPENED,
    CLOSED
}
