package org.switch2022.project.datamodel.jpa.resource;

import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.account.AccountJPA;
import org.switch2022.project.domain.valueobject.ProjectRole;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceJPATest {

    @Test
    void emptyConstructor(){
        ResourceJPA resourceJPA = new ResourceJPA();
        assertNotNull(resourceJPA);
    }

    @Test
    void getResourceIDJpa() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        ResourceIdJPA result = resourceJPA.getResourceIDJpa();
        assertEquals(resourceIdJPA, result);
    }

    @Test
    void getProjectRole() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        String result = resourceJPA.getProjectRole();
        assertEquals("P1", result);
    }

    @Test
    void getPercentageOfAllocation() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        int result = resourceJPA.getPercentageOfAllocation();
        assertEquals(50, result);
    }

    @Test
    void getCostPerHour() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        double result = resourceJPA.getCostPerHour();
        assertEquals(5.5, result);
    }

    @Test
    void equals_SameObject_Equal() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        assertEquals(resourceJPA, resourceJPA);
    }

    @Test
    void equals_EqualObject_SameID_Equal() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        ResourceJPA anotherResourceJPA = new ResourceJPA(resourceIdJPA, "P2",40, 10.0);
        assertEquals(resourceJPA, anotherResourceJPA);
    }

    @Test
    void equals_DifferentType_NotEqual() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        String anotherResource = "not a resource";
        assertNotEquals(resourceJPA, anotherResource);
    }

    @Test
    void equals_DifferentObject_DifferentID_NotEqual() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);

        ResourceIdJPA anotherResourceIDJPA = mock(ResourceIdJPA.class);
        ResourceJPA anotherResourceJPA = new ResourceJPA(anotherResourceIDJPA, "P1", 50, 5.5);
        assertNotEquals(resourceJPA, anotherResourceJPA);
    }

    @Test
    void hashCode_SameObject_True() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        ResourceJPA anotherResourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);

        int firstHash = resourceJPA.hashCode();
        int secondHash = anotherResourceJPA.hashCode();
        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        ResourceIdJPA resourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA resourceJPA = new ResourceJPA(resourceIdJPA, "P1", 50, 5.5);
        ResourceIdJPA anotherResourceIdJPA = mock(ResourceIdJPA.class);
        ResourceJPA anotherResourceJPA = new ResourceJPA(anotherResourceIdJPA, "P1", 50, 5.5);
        int firstHash = resourceJPA.hashCode();
        int secondHash = anotherResourceJPA.hashCode();

        assertNotEquals(firstHash, secondHash);
    }
}