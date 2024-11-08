package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;
import org.switch2022.project.utils.exception.InvalidOperationException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TimePeriodTest {

    @Test
    void TimePeriod_NullStartDate_Exception() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new TimePeriod(null, LocalDate.of(2022, 1, 3)));
        assertEquals("Start Date cannot be null", exception.getMessage());
    }

    @Test
    void TimePeriod_NullEndDate_Exception() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new TimePeriod(LocalDate.of(2022, 1, 3), null));
        assertEquals("End Date cannot be null", exception.getMessage());
    }

    @Test
    void ConstructorWithStartAndEndDate_EndDateBeforeStartDate_Exception() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 1)));
        assertEquals("Start Date cannot be after End Date", exception.getMessage());
    }

    @Test
    void ConstructorWithOnlyStartDate_StartDateNull_Exception() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new TimePeriod(null));
        assertEquals("Start Date cannot be null", exception.getMessage());
    }

    @Test
    void getStartDate() {
        String stringStartDate = "2022-01-10";
        String stringEndDate = "2022-01-15";

        LocalDate expectedStartDate = LocalDate.parse(stringStartDate);
        LocalDate endDate = LocalDate.parse(stringEndDate);
        TimePeriod period = new TimePeriod(expectedStartDate, endDate);

        LocalDate result = period.getStartDate();
        assertEquals(expectedStartDate, result);
    }

    @Test
    void getEndDate() {

        String stringStartDate = "2022-01-10";
        String stringEndDate = "2022-01-15";

        LocalDate startDate = LocalDate.parse(stringStartDate);
        LocalDate expectedEndDate = LocalDate.parse(stringEndDate);
        TimePeriod period = new TimePeriod(startDate, expectedEndDate);

        LocalDate result = period.getEndDate();
        assertEquals(expectedEndDate, result);
    }

    @Test
    void getEndDate_EndDateSetToMaximum_EndDate() {
        String stringStartDate = "2022-01-10";
        LocalDate startDate = LocalDate.parse(stringStartDate);
        TimePeriod period = new TimePeriod(startDate);

        LocalDate expected = LocalDate.MAX;

        LocalDate result = period.getEndDate();

        assertEquals(expected, result);
    }

    @Test
    void isOverlap_StartDateBeforeExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-08";
        String stringNewEndDate = "2022-01-12";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void isOverlap_StartDateAndEndDateBetweenExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-12";
        String stringNewEndDate = "2022-01-13";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void isOverlap_StartDateBetweenExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-12";
        String stringNewEndDate = "2022-01-16";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }


    @Test
    void isOverlap_StartDateAndEndDateIsAfterExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-16";
        String stringNewEndDate = "2022-01-20";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertFalse(result);
    }

    @Test
    void isOverlap_StartDateAndEndDateBeforeExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-01";
        String stringNewEndDate = "2022-01-05";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertFalse(result);
    }

    @Test
    void isOverlap_StartDateAndEndDateEqualToExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-10";
        String stringNewEndDate = "2022-01-15";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void isOverlap_StartDateIsEqualToEndAndEndDateAfterExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-15";
        String stringNewEndDate = "2022-01-17";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void isOverlap_StartDateIsEqualAndEndDateAfterExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-10";
        String stringNewEndDate = "2022-01-17";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void isOverlap_StartDateIsEqualAndEndDateInBetweenExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-10";
        String stringNewEndDate = "2022-01-13";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void isOverlap_StartDateIsBeforeAndEndDateIsEqualToStartDate() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-02";
        String stringNewEndDate = "2022-01-10";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void isOverlap_StartDateIsBeforeAndEndDateAfterExistingPeriod() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2022-01-05";
        String stringNewEndDate = "2022-01-17";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertTrue(result);
    }

    @Test
    void equals_sameObject_Equal() {
        TimePeriod period = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        assertEquals(period, period);
    }

    @Test
    void equals_differentObjectsButSameContent_Equal() {
        TimePeriod cost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        TimePeriod sameCost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        assertEquals(cost, sameCost);
    }

    @Test
    void equals_differentObjectSameClass_NotEqual() {
        TimePeriod cost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        TimePeriod differentCost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 7));
        assertNotEquals(cost, differentCost);
    }

    @Test
    void equals_differentObjectDifferentClass_NotEqual() {
        TimePeriod cost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        String differentObject = "2.5";

        assertNotEquals(cost, differentObject);
    }

    @Test
    void equals_compareWithNull_NotEqual() {
        TimePeriod cost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        TimePeriod nullObject = null;
        assertNotEquals(cost, nullObject);
    }

    @Test
    void hashCode_hashCodeCreation_Equals() {
        TimePeriod cost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        int result = cost.hashCode();
        TimePeriod sameCost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        int secondResult = sameCost.hashCode();
        assertEquals(result, secondResult);
    }

    @Test
    void hashCode_hashCodeCreation_NotEquals() {
        TimePeriod cost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 5));
        int result = cost.hashCode();
        TimePeriod differentCost = new TimePeriod(LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 7));
        int secondResult = differentCost.hashCode();
        assertNotEquals(result, secondResult);
    }

    @Test
    void contains_LocalDateWithinTimePeriod_True() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 1);
        TimePeriod timePeriod = new TimePeriod(startDate, endDate);

        LocalDate within = LocalDate.of(2022, 12, 12);

        assertTrue(timePeriod.contains(within));
    }

    @Test
    void contains_LocalDateWithinTimePeriod_False() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 1);
        TimePeriod timePeriod = new TimePeriod(startDate, endDate);

        LocalDate within = LocalDate.of(2020, 12, 12);

        assertFalse(timePeriod.contains(within));
    }

    @Test
    void contains_EndDateBeforeStartDate_Exception() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 1, 1);

        assertThrows(InvalidConstructorArgumentException.class, () -> {
            new TimePeriod(startDate, endDate);
        }, "Start Date cannot be after End Date");
    }

    @Test
    void contains_StartDateNull_Exception() {
        LocalDate endDate = LocalDate.of(2021, 1, 1);

        assertThrows(NullConstructorArgumentException.class, () -> {
            new TimePeriod(null, endDate);
        }, "Start Date cannot be null");
    }

    @Test
    void contains_EndDateNull_Exception() {
        LocalDate startDate = LocalDate.of(2021, 1, 1);

        assertThrows(NullConstructorArgumentException.class, () -> {
            new TimePeriod(startDate, null);
        }, "End Date cannot be null");
    }

    @Test
    void isOverlap_PeriodsNotOverlapping_False() {
        String stringExistingStartDate = "2022-01-10";
        String stringExistingEndDate = "2022-01-15";

        LocalDate existingStartDate = LocalDate.parse(stringExistingStartDate);
        LocalDate existingEndDate = LocalDate.parse(stringExistingEndDate);

        TimePeriod period = new TimePeriod(existingStartDate, existingEndDate);

        String stringNewStartDate = "2023-01-15";
        String stringNewEndDate = "2023-01-17";

        LocalDate newStartDate = LocalDate.parse(stringNewStartDate);
        LocalDate newEndDate = LocalDate.parse(stringNewEndDate);

        boolean result = period.isOverlap(newStartDate, newEndDate);
        assertFalse(result);
    }
}