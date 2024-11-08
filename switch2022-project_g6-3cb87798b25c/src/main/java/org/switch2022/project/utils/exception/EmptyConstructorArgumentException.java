package org.switch2022.project.utils.exception;

/**
 * The EmptyConstructorArgumentException class represents an exception that is thrown when an empty constructor
 * argument is encountered.
 * It is a subclass of AbstractApiException and inherits its properties and methods.
 */
public class EmptyConstructorArgumentException extends AbstractApiException {

    /**
     * Constructs an EmptyConstructorArgumentException object with the specified error message.
     *
     * @param message the error message
     */
    public EmptyConstructorArgumentException(String message) {
        super(message);
    }
}
