package org.switch2022.project.utils.exception;

/**
 * The EntityNotFoundException class represents an exception that is thrown when an entity is not found.
 * It is a subclass of AbstractApiException and inherits its properties and methods.
 */
public class EntityNotFoundException extends AbstractApiException {

    /**
     * Constructs an EntityNotFoundException object with the specified error message.
     *
     * @param message the error message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
