package org.switch2022.project.utils.exception;

/**
 * The AbstractApiException class is an abstract base class for custom exceptions in the application.
 * It extends the RuntimeException class and provides a common structure for handling exceptions.
 */
public abstract class AbstractApiException extends RuntimeException {

    /**
     * Constructs an AbstractApiException object with the specified error message.
     *
     * @param message the error message
     */
    public AbstractApiException(String message) {
        super(message);
    }
}
