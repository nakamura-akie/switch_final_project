package org.switch2022.project.utils.exception;

/**
 * The InvalidOperationException class represents an exception that is thrown when an invalid operation is performed.
 * It is a subclass of AbstractApiException and inherits its properties and methods.
 */
public class InvalidOperationException extends AbstractApiException {

    /**
     * Constructs an InvalidConstructorArgumentException object with the specified error message.
     *
     * @param message the error message
     */
    public InvalidOperationException(String message) {
        super(message);
    }
}
