package org.switch2022.project.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a time period with a start date and an end date.
 * Implements the ValueObject interface and Serializable.
 */
public class TimePeriod implements ValueObject, Serializable {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructs a TimePeriod object with the given start date and end date.
     *
     * @param startDate the start date of the time period
     * @param endDate   the end date of the time period
     * @throws IllegalArgumentException if the start date or end date is null,
     *                                  or if the start date is after the end date
     */
    public TimePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new NullConstructorArgumentException("Start Date cannot be null");
        }
        if (endDate == null) {
            throw new NullConstructorArgumentException("End Date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidConstructorArgumentException("Start Date cannot be after End Date");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Creates a new TimePeriod with the specified start date and default end date.
     *
     * @param startDate the start date of the time period
     * @throws NullConstructorArgumentException if the start date is null
     */
    @JsonCreator
    public TimePeriod(@JsonProperty("startDate") LocalDate startDate) {
        if (startDate == null) {
            throw new NullConstructorArgumentException("Start Date cannot be null");
        }

        this.startDate = startDate;
        this.endDate = LocalDate.MAX;
    }

    /**
     * Checks if this time period overlaps with the specified time period defined by its start date and end date.
     *
     * @param newResourceStartDate the start date of the new time period
     * @param newResourceEndDate   the end date of the new time period
     * @return true if there is an overlap, false otherwise
     */
    public boolean isOverlap(LocalDate newResourceStartDate, LocalDate newResourceEndDate) {

        return !newResourceEndDate.isBefore(this.startDate) &&
                !newResourceStartDate.isAfter(this.endDate);
    }

    /**
     * Returns the start date of the time period.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Returns the end date of the time period.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Checks if this time period is equal to the specified object.
     * Two time periods are considered equal if their start dates and end dates are equal.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !o.getClass().equals(this.getClass())) {
            return false;
        }
        TimePeriod that = (TimePeriod) o;
        return this.startDate.equals(that.startDate) && this.endDate.equals(that.endDate);
    }

    /**
     * Returns the hash code value for this time period.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    /**
     * Checks if this time period contains the specified date.
     *
     * @param date the date to check
     * @return true if the time period contains the date, false otherwise
     */
    public boolean contains(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
