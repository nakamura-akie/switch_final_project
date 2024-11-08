package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CustomerNameTest {

    @Test
    void constructor_ValueIsNull_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CustomerName(null));
        assertEquals("Invalid Customer Name.", exception.getMessage());
    }

    @Test
    void constructor_ValueIsBlank_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CustomerName(""));
        assertEquals("Invalid Customer Name.", exception.getMessage());
    }

    @Test
    void getCustomerNameValue_ReturnsCorrectString_True() {
        String name = "Rute";

        CustomerName newCustomerName = new CustomerName(name);

        String resultString = newCustomerName.getCustomerNameValue();

        boolean result = resultString.equals(name);

        assertTrue(result);
    }

    @Test
    void equals_SameObject_True() {
        CustomerName name = new CustomerName("João");
        CustomerName sameName = name;

        boolean result = name.equals(sameName);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        CustomerName name = new CustomerName("Joana");
        CustomerName nullObject = null;

        boolean result = name.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        CustomerName name = new CustomerName("Salmão");
        String differentObject = "Lídia";

        boolean result = name.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        CustomerName name = new CustomerName("Joaquim");
        CustomerName differentName = new CustomerName("Amélia");

        boolean result = name.equals(differentName);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        CustomerName name = new CustomerName("António");
        CustomerName nameWithSameValue = new CustomerName("António");

        boolean result = name.equals(nameWithSameValue);

        assertTrue(result);
    }

    @Test
    void hashCode_True() {
        CustomerName name = new CustomerName("Paula");

        int expected = Objects.hashCode(name);
        int result = name.hashCode();

        assertEquals(expected, result);
    }

}