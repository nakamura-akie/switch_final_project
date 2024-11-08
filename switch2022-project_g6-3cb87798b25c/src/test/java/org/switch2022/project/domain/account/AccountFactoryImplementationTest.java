package org.switch2022.project.domain.account;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class AccountFactoryImplementationTest {

    @Test
    void createAccount_HappyPath_Equals() {
        AccountName accountNameDouble = mock(AccountName.class);
        ProfileName profileNameDouble = mock(ProfileName.class);
        Email emailDouble = mock(Email.class);
        PhoneNumber phoneNumberDouble = mock(PhoneNumber.class);

        AccountFactoryImplementation factoryImplementation = new AccountFactoryImplementation();
        Account result = factoryImplementation.createAccount(accountNameDouble, emailDouble, null, phoneNumberDouble, profileNameDouble);
        assertEquals(accountNameDouble, result.getAccountName());
        assertEquals(profileNameDouble, result.getProfile());
        assertEquals(emailDouble, result.getEmail());
        assertEquals(phoneNumberDouble, result.getPhoneNumber());
        assertTrue(result.getAccountStatus().isStatusActive());
    }

    @Test
    void createAccountJpa() {
        String accountName = "name";
        String profileName = "profile";
        String emailDouble = "email@mail.com";
        Integer phoneNumber = 123456789;
        boolean accountStatus = true;

        AccountFactoryImplementation factoryImplementation = new AccountFactoryImplementation();
        Account result = factoryImplementation.createAccountJpa(accountName,
                emailDouble, null, phoneNumber, profileName, accountStatus);
        assertEquals(accountName, result.getAccountName().toString());
        assertEquals(profileName, result.getProfile().getProfileNameValue());
        assertEquals(emailDouble, result.getEmail().getEmailValue());
        assertEquals(phoneNumber, result.getPhoneNumber().getPhoneNumberValue());
        assertEquals(accountStatus, result.getAccountStatus().isStatusActive());
    }
}