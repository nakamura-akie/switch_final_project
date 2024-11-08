package org.switch2022.project.utils.exception;

/**
 * The ApiExceptionMessage class represents an error message for API exceptions.
 * It encapsulates the error message string.
 */
public class ApiExceptionMessage {
    private final String message;

    /**
     * Constructs an ApiExceptionMessage object with the specified message.
     *
     * @param message the error message
     */
    public ApiExceptionMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }
}
