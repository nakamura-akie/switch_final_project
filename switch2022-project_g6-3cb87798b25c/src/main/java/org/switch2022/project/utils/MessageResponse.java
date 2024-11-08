package org.switch2022.project.utils;

import java.util.Objects;

/**
 * The MessageResponse class represents a response containing a message.
 * It is used for returning simple messages from API endpoints or services.
 */
public class MessageResponse {
    private final String message;

    /**
     * Constructs a new MessageResponse object with the specified message.
     *
     * @param message the message to be encapsulated in the response
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message from the response.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Checks whether the current object is equal to another object.
     *
     * @param o The object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageResponse that = (MessageResponse) o;
        return Objects.equals(message, that.message);
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
