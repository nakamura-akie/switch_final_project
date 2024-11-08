package org.switch2022.project.utils.exception;

/**
 * The NullConstructorArgumentException class represents an exception that is thrown when a null argument is
 * passed to a constructor.
 * It is a subclass of AbstractApiException and inherits its properties and methods.
 */
public class NullConstructorArgumentException extends AbstractApiException {

    /**
     * Constructs an InvalidConstructorArgumentException object with the specified error message.
     *
     * @param message the error message
     */
    public NullConstructorArgumentException(String message) {
        super(message);
    }
}
