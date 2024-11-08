package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountAndStatusDTOTest {

    private AccountAndStatusDTO firstAccountAndStatusDTO;

    @BeforeEach
    void init() {
        firstAccountAndStatusDTO = new AccountAndStatusDTO();
        firstAccountAndStatusDTO.accountName = "Account Name";
        firstAccountAndStatusDTO.email = "email.email@email.com";
        firstAccountAndStatusDTO.accountStatus = "active";
    }

    @AfterEach
    void tearDown() {
        firstAccountAndStatusDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        AccountAndStatusDTO anotherAccountAndStatusDTO = firstAccountAndStatusDTO;

        boolean result = firstAccountAndStatusDTO.equals(anotherAccountAndStatusDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsOfDifferentClasses_False() {
        String account = "This is not a account";

        boolean result = firstAccountAndStatusDTO.equals(account);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithSameAttributes_True() {
        AccountAndStatusDTO thirdAccountAndStatusDTO = new AccountAndStatusDTO();
        thirdAccountAndStatusDTO.accountName = "Account Name";
        thirdAccountAndStatusDTO.email = "email.email@email.com";
        thirdAccountAndStatusDTO.accountStatus = "active";

        boolean result = firstAccountAndStatusDTO.equals(thirdAccountAndStatusDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentEmail_False() {
        AccountAndStatusDTO secondAccountAndStatusDTO = new AccountAndStatusDTO();
        secondAccountAndStatusDTO.accountName = "Account Name";
        secondAccountAndStatusDTO.email = "different.email@email.com";
        secondAccountAndStatusDTO.accountStatus = "active";


        boolean result = firstAccountAndStatusDTO.equals(secondAccountAndStatusDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithDifferentAccountName_False() {
        AccountAndStatusDTO fourthAccountAndStatusDTO = new AccountAndStatusDTO();
        fourthAccountAndStatusDTO.accountName = "Different Account Name";
        fourthAccountAndStatusDTO.email = "email.email@email.com";
        fourthAccountAndStatusDTO.accountStatus = "active";

        boolean result = firstAccountAndStatusDTO.equals(fourthAccountAndStatusDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithDifferentAccountStatus_False() {
        AccountAndStatusDTO fifthAccountAndStatusDTO = new AccountAndStatusDTO();
        fifthAccountAndStatusDTO.accountName = "Account Name";
        fifthAccountAndStatusDTO.email = "email.email@email.com";
        fifthAccountAndStatusDTO.accountStatus = "inactive";

        boolean result = firstAccountAndStatusDTO.equals(fifthAccountAndStatusDTO);

        assertFalse(result);
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        AccountAndStatusDTO anotherAccountAndStatusDTO = firstAccountAndStatusDTO;

        int firstHashCode = firstAccountAndStatusDTO.hashCode();
        int secondHashCode = anotherAccountAndStatusDTO.hashCode();

        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        AccountAndStatusDTO secondAccountAndStatusDTO = new AccountAndStatusDTO();
        secondAccountAndStatusDTO.accountName = "Second Account Name";
        secondAccountAndStatusDTO.email = "second.email@email.com";
        secondAccountAndStatusDTO.accountStatus = "active";

        int firstHashCode = firstAccountAndStatusDTO.hashCode();
        int secondHashCode = secondAccountAndStatusDTO.hashCode();

        assertNotEquals(firstHashCode, secondHashCode);
    }

}