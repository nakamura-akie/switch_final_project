package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void constructor_NullCountry_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Country(null));
        assertEquals("Invalid country.", exception.getMessage());
    }

    @Test
    void constructor_EmptyCountry_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Country(""));
        assertEquals("Invalid country.", exception.getMessage());
    }

    @Test
    void constructor_BlankCountry_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Country("\t"));
        assertEquals("Invalid country.", exception.getMessage());
    }


    @Test
    void getCountryName_ReturnsCountryName_True() {
        Country country = new Country("portugal");

        String result = country.getCountryName();

        assertEquals("portugal", result);
    }

    @Test
    void equals_SameObject_True() {
        Country country = new Country("portugal");
        Country sameCountry = country;

        boolean result = country.equals(sameCountry);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        Country country = new Country("portugal");
        Country nullObject = null;

        boolean result = country.equals(nullObject);

        assertFalse(result);

    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        Country country = new Country("portugal");
        String differentClass = "not a country";

        boolean result = country.equals(differentClass);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        Country country = new Country("portugal");
        Country differentCountry = new Country("spain");

        boolean result = country.equals(differentCountry);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        Country country = new Country("portugal");
        Country anotherCountry = new Country("portugal");

        boolean result = country.equals(anotherCountry);

        assertTrue(result);

    }

    @Test
    void hashCode_True() {
        Country country = new Country("portugal");

        int expected = Objects.hashCode(country);
        int result = country.hashCode();

        assertEquals(expected, result);
    }
}