package org.switch2022.project.utils.exception;


/**
 * The DataValidationException class represents an exception that is thrown when data validation fails.
 * It is a subclass of AbstractApiException and inherits its properties and methods.
 */
public class DataValidationException extends AbstractApiException {

    /**
     * Constructs a DataValidationException object with the specified error message.
     *
     * @param message the error message
     */
    public DataValidationException(String message) {
        super(message);
    }
}
