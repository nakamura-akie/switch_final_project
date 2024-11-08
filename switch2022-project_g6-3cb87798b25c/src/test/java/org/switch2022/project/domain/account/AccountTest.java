package org.switch2022.project.domain.account;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AccountTest {

    private static AccountName accountNameOneDouble;
    private static AccountName accountNameTwoDouble;

    private static Email emailOneDouble;
    private static Email emailTwoDouble;

    private static PhoneNumber phoneNumberOneDouble;
    private static PhoneNumber phoneNumberTwoDouble;

    private static ProfileName profileNameOneDouble;
    private static ProfileName profileNameTwoDouble;

    private static Account accountOne;
    private static Account accountTwo;

    private static AccountStatus inactive;
    private static AccountStatus accountStatus;

    @BeforeAll
    static void setup() {
        accountNameOneDouble = mock(AccountName.class);
        accountNameTwoDouble = mock(AccountName.class);

        emailOneDouble = mock(Email.class);
        emailTwoDouble = mock(Email.class);

        phoneNumberOneDouble = mock(PhoneNumber.class);
        phoneNumberTwoDouble = mock(PhoneNumber.class);

        profileNameOneDouble = mock(ProfileName.class);
        profileNameTwoDouble = mock(ProfileName.class);
        accountStatus = new AccountStatus(true);
        inactive = new AccountStatus(false);

        accountOne = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameOneDouble, accountStatus);
        accountTwo = new Account(accountNameOneDouble,emailOneDouble, null, phoneNumberOneDouble,profileNameOneDouble, inactive);

    }

    @AfterAll
    static void tearDown() {
        accountNameOneDouble = null;
        accountNameTwoDouble = null;
        emailOneDouble = null;
        emailTwoDouble = null;
        phoneNumberOneDouble = null;
        phoneNumberTwoDouble = null;
        profileNameOneDouble = null;
        profileNameTwoDouble = null;
        accountOne = null;

    }

    @Test
    void identity_ReturnsCorrectIdentity_True() {
        Email accountOneIdentity = accountOne.identity();

        boolean result = accountOneIdentity.equals(emailOneDouble);

        assertTrue(result);
    }

    @Test
    void sameAs_EverythingIsTheSame_True() {
        Account accountTwo = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameOneDouble,accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertTrue(result);
    }

    @Test
    void sameAs_EverythingDifferent_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble,inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameAccountName_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameEmail_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailOneDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SamePhoneNumber_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberOneDouble,
                profileNameTwoDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameProfileName_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameOneDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameStatus_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameAccountNameAndEmail_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameAccountNameAndPhoneNumber_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailTwoDouble, null, phoneNumberOneDouble,
                profileNameTwoDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameAccountNameAndProfileName_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameOneDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameAccountNameAndStatus_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameEmailAndPhoneNumber_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameTwoDouble,inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameEmailAndProfileName_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailOneDouble, null, phoneNumberTwoDouble,
                profileNameOneDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameEmailAndStatus_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailOneDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SamePhoneNumberAndProfileName_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberOneDouble,
                profileNameOneDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SamePhoneNumberAndStatus_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberOneDouble,
                profileNameTwoDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_SameProfileNameAndStatus_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameOneDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentAccountName_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameOneDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentPhoneNumber_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberTwoDouble,
                profileNameOneDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentProfileName_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameTwoDouble, accountStatus);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentStatus_False() {
        Account accountTwo = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameOneDouble, inactive);

        boolean result = accountOne.sameAs(accountTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentClass_False() {
        String notAnAccount = "not an account";

        boolean result = accountOne.sameAs(notAnAccount);

        assertFalse(result);
    }

    @Test
    void equals_SameObject_True() {
        Account accountTwo = accountOne;

        boolean result = accountOne.equals(accountTwo);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        Account nullObject = null;

        boolean result = accountOne.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String notAnAccout = "not an account";

        boolean result = accountOne.equals(notAnAccout);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        Account accountTwo = new Account(accountNameTwoDouble, emailTwoDouble, null, phoneNumberTwoDouble,
                profileNameTwoDouble, accountStatus);

        boolean result = accountOne.equals(accountTwo);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        Account accountTwo = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameOneDouble, accountStatus);

        boolean result = accountOne.equals(accountTwo);

        assertTrue(result);
    }

    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(accountOne);
        int result = accountOne.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void setAccountStatus_FromActiveToInactive_True() {
        AccountStatus inactive = new AccountStatus(false);
        accountOne.setAccountStatus(inactive);
        assertTrue(accountOne.hasAccountStatus(inactive));
    }

    @Test
    void setAccountStatus_FromInactiveToActive_True() {
        AccountStatus inactive = new AccountStatus(false);
        Account accountTwo = new Account(accountNameOneDouble, emailOneDouble, null, phoneNumberOneDouble,
                profileNameOneDouble, inactive);
        accountTwo.setAccountStatus(accountStatus);
        assertTrue(accountTwo.hasAccountStatus(accountStatus));
    }

    @Test
    void setAccountStatus_TryToActivateAnActiveAccount_Exception() {
        String result = assertThrows(IllegalStateException.class, () -> accountOne.setAccountStatus(accountStatus)).getMessage();
        String expected = "The account already has the chosen status";
        assertEquals(expected,result);
    }
    @Test
    void setAccountStatus_TryToInactivateAnInactiveAccount_Exception() {
        String result = assertThrows(IllegalStateException.class, () -> accountTwo.setAccountStatus(inactive)).getMessage();
        String expected = "The account already has the chosen status";
        assertEquals(expected,result);
    }

    @Test
    void hasAccountStatus_True() {
       assertTrue(accountOne.hasAccountStatus(accountStatus));
    }

    @Test
    void hasAccountStatus_False() {
        AccountStatus inactive = new AccountStatus(false);
        assertFalse(accountOne.hasAccountStatus(inactive));
    }

    @Test
    void getProfile() {
        ProfileName result = accountOne.getProfile();
        assertEquals(profileNameOneDouble, result);
    }

    @Test
    void getEmail() {
        Email result = accountOne.getEmail();
        assertEquals(emailOneDouble, result);

    }

    @Test
    void getPhoneNumber() {
        PhoneNumber result = accountOne.getPhoneNumber();
        assertEquals(phoneNumberOneDouble, result);
    }

    @Test
    void changeProfileOfUserAccount() {
        accountTwo.changeProfileOfUserAccount(profileNameOneDouble);
        ProfileName result = accountTwo.getProfile();
        assertEquals(profileNameOneDouble, result);
    }

    @Test
    void getAccountName() {
        AccountName result = accountOne.getAccountName();
        assertEquals(accountNameOneDouble, result);
    }

    @Test
    void getAccountStatus() {
        AccountStatus result = accountOne.getAccountStatus();
        assertEquals(accountStatus, result);
    }

    @Test
    void hasProfile_hasProfile_True() {
        boolean result = accountOne.hasProfile(profileNameOneDouble);
        assertTrue(result);
    }
    @Test
    void hasProfile_hasDifferentProfile_False() {
        boolean result = accountOne.hasProfile(profileNameTwoDouble);
        assertFalse(result);
    }
}