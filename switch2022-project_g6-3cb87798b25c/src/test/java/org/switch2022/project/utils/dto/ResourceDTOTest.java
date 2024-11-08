package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.ResourceDTO;

import static org.junit.jupiter.api.Assertions.*;

class ResourceDTOTest {

    @Test
    void equals_CompareWithSameObject_ReturnTrue() {

        ResourceDTO resourceDTOOne = new ResourceDTO();
        ResourceDTO resourceDTOTwo = resourceDTOOne;

        boolean result = resourceDTOTwo.equals(resourceDTOOne);

        assertTrue(result);
    }

    @Test
    void equals_CompareWithDifferentObjects_ReturnFalse() {
        ResourceDTO resourceDTO = new ResourceDTO();
        String aString = "";

        boolean result = resourceDTO.equals(aString);

        assertFalse(result);
    }

    @Test
    void equals_CompareWithNullObject_ReturnFalse() {
        ResourceDTO resourceDTO = new ResourceDTO();
        ResourceDTO nullResourceDTO = null;

        boolean result = resourceDTO.equals(nullResourceDTO);

        assertFalse(result);
    }

    @Test
    void equals_CompareDTOsWithSameAttributes_ReturnTrue() {
        ResourceDTO resourceDTOOne = new ResourceDTO();
        resourceDTOOne.email = "mail@mail.com";
        resourceDTOOne.projectRole = "sm";
        resourceDTOOne.projectCode = "P001";
        resourceDTOOne.startDate = "2022-01-01";
        resourceDTOOne.endDate = "2023-01-01";

        ResourceDTO resourceDTOTwo = new ResourceDTO();
        resourceDTOTwo.email = "mail@mail.com";
        resourceDTOTwo.projectRole = "sm";
        resourceDTOTwo.projectCode = "P001";
        resourceDTOTwo.startDate = "2022-01-01";
        resourceDTOTwo.endDate = "2023-01-01";

        boolean result = resourceDTOOne.equals(resourceDTOTwo);

        assertTrue(result);
    }

    @Test
    void equals_CompareDTOsWithDifferentEmail_ReturnFalse() {
        ResourceDTO resourceDTOOne = new ResourceDTO();
        resourceDTOOne.email = "mail@mail.com";
        resourceDTOOne.projectRole = "sm";
        resourceDTOOne.projectCode = "P001";

        ResourceDTO resourceDTOTwo = new ResourceDTO();
        resourceDTOTwo.email = "mail2@mail.com";
        resourceDTOTwo.projectRole = "sm";
        resourceDTOTwo.projectCode = "P001";

        boolean result = resourceDTOOne.equals(resourceDTOTwo);

        assertFalse(result);

    }

    @Test
    void equals_CompareDTOsWithDifferentProjectCode_ReturnFalse() {
        ResourceDTO resourceDTOOne = new ResourceDTO();
        resourceDTOOne.email = "mail@mail.com";
        resourceDTOOne.projectRole = "sm";
        resourceDTOOne.projectCode = "P001";

        ResourceDTO resourceDTOTwo = new ResourceDTO();
        resourceDTOTwo.email = "mail@mail.com";
        resourceDTOTwo.projectRole = "sm";
        resourceDTOTwo.projectCode = "P002";

        boolean result = resourceDTOOne.equals(resourceDTOTwo);

        assertFalse(result);
    }

    @Test
    void equals_CompareDTOsWithDifferentProjectRole_ReturnFalse() {
        ResourceDTO resourceDTOOne = new ResourceDTO();
        resourceDTOOne.email = "mail@mail.com";
        resourceDTOOne.projectRole = "tm";
        resourceDTOOne.projectCode = "P001";

        ResourceDTO resourceDTOTwo = new ResourceDTO();
        resourceDTOTwo.email = "mail2@mail.com";
        resourceDTOTwo.projectRole = "sm";
        resourceDTOTwo.projectCode = "P001";

        boolean result = resourceDTOOne.equals(resourceDTOTwo);

        assertFalse(result);
    }

    @Test
    void HashCode_CheckIfSameHashcode_ReturnTrue() {
        ResourceDTO resourceDTOOne = new ResourceDTO();
        resourceDTOOne.email = "mail@mail.com";
        resourceDTOOne.projectRole = "sm";
        resourceDTOOne.projectCode = "P001";
        int hashCodeOne = resourceDTOOne.hashCode();

        ResourceDTO resourceDTOTwo = new ResourceDTO();
        resourceDTOTwo.email = "mail@mail.com";
        resourceDTOTwo.projectRole = "sm";
        resourceDTOTwo.projectCode = "P001";
        int hashCodeTwo = resourceDTOTwo.hashCode();

        boolean result = hashCodeOne == hashCodeTwo;

        assertTrue(result);
    }

    @Test
    void HashCode_CheckIfDifferentHashcode_ReturnFalse() {
        ResourceDTO resourceDTOOne = new ResourceDTO();
        resourceDTOOne.email = "mail2@mail.com";
        resourceDTOOne.projectRole = "sm";
        resourceDTOOne.projectCode = "P001";
        int hashCodeOne = resourceDTOOne.hashCode();

        ResourceDTO resourceDTOTwo = new ResourceDTO();
        resourceDTOTwo.email = "mail@mail.com";
        resourceDTOTwo.projectRole = "sm";
        resourceDTOTwo.projectCode = "P001";
        int hashCodeTwo = resourceDTOTwo.hashCode();

        boolean result = hashCodeOne == hashCodeTwo;

        assertFalse(result);
    }
}