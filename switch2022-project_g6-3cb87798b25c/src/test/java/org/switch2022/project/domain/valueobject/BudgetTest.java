package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

    @Test
    void constructor_BudgetValue_ThrowsException() {
        assertDoesNotThrow(() -> new Budget(0.0));
    }

    @Test
    void constructor_InvalidBudgetValue_ThrowsException() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class,
                () -> new Budget(-1.0));

        assertEquals("Budget must be greater than 0", exception.getMessage());
    }

    @Test
    void stringConstructor_ParsesCorrectValue() {
        String budgetValue = "1000.0";
        Budget budget = new Budget(budgetValue);

        assertEquals(Double.parseDouble(budgetValue), budget.getBudgetValue());
    }

    @Test
    void getBudgetValue_ReturnsCorrectValue() {
        Double budgetValue = 1000.0;
        Budget budget = new Budget(budgetValue);

        assertEquals(budgetValue, budget.getBudgetValue());
    }

    @Test
    void equals_SameObject_ReturnsTrue() {
        Budget budget = new Budget(1000.0);

        assertTrue(budget.equals(budget));
    }

    @Test
    void equals_TwoBudgetsWithSameValue_AreEqual() {
        Double budgetValue = 1000.0;
        Budget budget1 = new Budget(budgetValue);
        Budget budget2 = new Budget(budgetValue);

        assertEquals(budget1, budget2);
    }

    @Test
    void equals_TwoBudgetsWithDifferentValues_AreNotEqual() {
        Double budgetValue1 = 1000.0;
        Double budgetValue2 = 2000.0;
        Budget budget1 = new Budget(budgetValue1);
        Budget budget2 = new Budget(budgetValue2);

        assertNotEquals(budget1, budget2);
    }

    @Test
    void equals_BudgetAndNonBudgetObject_AreNotEqual() {
        Double budgetValue = 1000.0;
        Budget budget = new Budget(budgetValue);
        Object nonBudgetObject = new Object();

        assertNotEquals(budget, nonBudgetObject);
    }

    @Test
    void hashCode_TwoBudgetsWithSameValue_HaveSameHashCode() {
        Double budgetValue = 1000.0;
        Budget budget1 = new Budget(budgetValue);
        Budget budget2 = new Budget(budgetValue);

        assertEquals(budget1.hashCode(), budget2.hashCode());
    }


        }