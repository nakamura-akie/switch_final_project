package org.switch2022.project.utils.exception;

/**
 * The InvalidConstructorArgumentException class represents an exception that is thrown when an invalid constructor
 * argument is provided.
 * It is a subclass of AbstractApiException and inherits its properties and methods.
 */
public class InvalidConstructorArgumentException extends AbstractApiException {

    /**
     * Constructs an InvalidConstructorArgumentException object with the specified error message.
     *
     * @param message the error message
     */
    public InvalidConstructorArgumentException(String message) {
        super(message);
    }
}
