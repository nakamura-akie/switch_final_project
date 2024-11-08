package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffortTest {
    @Test
    void getValue_ReturnsCorrectValue() {
        assertEquals(1, Effort.ONE.getValue());
        assertEquals(2, Effort.TWO.getValue());
        assertEquals(3, Effort.THREE.getValue());
        assertEquals(5, Effort.FIVE.getValue());
        assertEquals(8, Effort.EIGHT.getValue());
        assertEquals(13, Effort.THIRTEEN.getValue());
        assertEquals(20, Effort.TWENTY.getValue());
        assertEquals(40, Effort.FORTY.getValue());
    }

    @Test
    void equals_WithSameObject_ReturnsTrue() {
        assertEquals(Effort.ONE, Effort.ONE);
    }

    @Test
    void equals_WithEqualObjects_ReturnsTrue() {
        assertEquals(Effort.FIVE, Effort.FIVE);
    }

    @Test
    void equals_WithDifferentObjects_ReturnsFalse() {
        assertNotEquals(Effort.TWO, Effort.ONE);
    }

    @Test
    void equals_WithNull_ReturnsFalse() {
        assertNotEquals(null, Effort.ONE);
    }

    @Test
    void equals_WithDifferentObjectType_ReturnsFalse() {
        assertNotEquals("Effort", Effort.ONE);
    }

    @Test
    void hashCode_ReturnsHashCode() {
        assertEquals(Effort.FIVE.hashCode(), Effort.FIVE.hashCode());
    }

}