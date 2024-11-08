package org.switch2022.project.datamodel.jpa.resource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ResourceIdJPATest {

    @Test
    void setEmailAndGetEmail_Equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        String expected="other_email@mail.com";
        idJPA.setEmail(expected);
        String result= idJPA.getEmail();

        assertEquals(expected,result);
    }

    @Test
    void EmptyConstructorMethod_InstanceIsCreated() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA();

        assertEquals(idJPA.getClass(),secondIdJPA.getClass());
    }

    @Test
    void constructorMethod_NullEmail_ThrowsException() {
        String expected = "Information cannot be null.";

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new ResourceIdJPA(
                        null,
                        "P1",
                        "2022-02-12",
                        "2022-10-10"));

        assertEquals(expected, e.getMessage());
    }

    @Test
    void constructorMethod_NullProjectCode_ThrowsException() {
        String expected = "Information cannot be null.";

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new ResourceIdJPA(
                        "pedro@mail.com",
                        null,
                        "2022-02-12",
                        "2022-10-10"));

        assertEquals(expected, e.getMessage());
    }

    @Test
    void constructorMethod_NullStartDate_ThrowsException() {
        String expected = "Information cannot be null.";

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new ResourceIdJPA(
                        "pedro@mail.com",
                        "P1",
                        null,
                        "2022-10-10"));

        assertEquals(expected, e.getMessage());
    }

    @Test
    void constructorMethod_NullEndDate_ThrowsException() {
        String expected = "Information cannot be null.";

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new ResourceIdJPA(
                        "pedro@mail.com",
                        "P1",
                        "2022-02-12",
                        null));

        assertEquals(expected, e.getMessage());
    }


    @Test
    void setProjectCodeAndGetProjectCode_Equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        String expected = "P2";

        idJPA.setProjectCode(expected);
        String result = idJPA.getProjectCode();

        assertEquals(expected, result);
    }

    @Test
    void setStartDateAndGetStartDate_Equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        String expected = "2022-04-01";

        idJPA.setStartDate(expected);
        String result = idJPA.getStartDate();

        assertEquals(expected, result);
    }

    @Test
    void setEndDateAndGetEndDate_Equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        String expected = "2022-11-01";

        idJPA.setEndDate(expected);
        String result = idJPA.getEndDate();

        assertEquals(expected, result);
    }

    @Test
    void testEquals_SameObject_Equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = idJPA;

        assertEquals(idJPA, secondIdJPA);
    }

    @Test
    void testEquals_DifferentObjects_NotEquals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        Object otherObject = new Object();

        assertNotEquals(idJPA, otherObject);
    }

    @Test
    void testEquals_DifferentInstancesWithSameValues_Equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        assertEquals(idJPA, secondIdJPA);
    }

    @Test
    void testEquals_DifferentEmailValues_NotEquals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA(
                "maria@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        assertNotEquals(idJPA, secondIdJPA);
    }

    @Test
    void testEquals_DifferentProjectCodeValues_NotEquals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P2",
                "2022-02-12",
                "2022-10-10");

        assertNotEquals(idJPA, secondIdJPA);
    }

    @Test
    void testEquals_DifferentStartDateValues_NotEquals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-10",
                "2022-10-10");

        assertNotEquals(idJPA, secondIdJPA);
    }

    @Test
    void testEquals_DifferentEndDateValues_NotEquals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2024-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        assertNotEquals(idJPA, secondIdJPA);
    }

    @Test
    void testHashCode_equalInstances_equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        assertEquals(idJPA.hashCode(), secondIdJPA.hashCode());
    }

    @Test
    void testHashCode_differentInstances_NotEquals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        ResourceIdJPA secondIdJPA = new ResourceIdJPA(
                "maria@mail.com",
                "P2",
                "2021-02-12",
                "2025-10-10");

        assertNotEquals(idJPA.hashCode(), secondIdJPA.hashCode());
    }

    @Test
    void testToString_Equals() {
        ResourceIdJPA idJPA = new ResourceIdJPA(
                "pedro@mail.com",
                "P1",
                "2022-02-12",
                "2022-10-10");

        String expected = "ResourceIdJPA{" +
                "email='pedro@mail.com', " +
                "projectCode='P1', " +
                "startDate='2022-02-12', " +
                "endDate='2022-10-10'}";

        String result = idJPA.toString();

        assertEquals(expected, result);
    }
}