package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactory;
import org.switch2022.project.domain.account.AccountFactoryImplementation;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.AccountRepositoryFake;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class InactivateAccountControllerTest {
    @Autowired
    InactivateAccountController inactivateAccountController;

    static AccountRepositoryFake accountRepositoryFake;
    static AccountFactory accountFactory;

    @BeforeAll
    public static void init(){

        accountFactory = new AccountFactoryImplementation();
        AccountName accountName = new AccountName("Ana");
        Email accountEmail = new Email("ana@ana.com");
        Email accountTwoEmail = new Email("rita@rita.com");
        PhoneNumber phoneNumber = new PhoneNumber(917366527);
        ProfileName profileName = new ProfileName("user");
        Account activeAccount = accountFactory.createAccount(accountName,accountEmail,null,phoneNumber,profileName);

        Account inactiveAccount = accountFactory.createAccount(accountName, accountTwoEmail, null, phoneNumber, profileName);
        inactiveAccount.setAccountStatus(new AccountStatus(false));

        accountRepositoryFake = new AccountRepositoryFake();
        accountRepositoryFake.save(activeAccount);
        accountRepositoryFake.save(inactiveAccount);
    }

    @AfterAll
    public static void tearDown(){
        accountRepositoryFake.deleteAll();

    }
    @Test
    void constructor_nullAccountService() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new InactivateAccountController(null));
        assertEquals("Account Service cannot be null", exception.getMessage());
    }


    @Test
    void inactivateAccount_EverythingGoesWell_True() {
        assertTrue(inactivateAccountController.inactivateAccount("ana@ana.com"));
    }
    @Test
    void inactivateAccount_AccountDoesNotExist_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                inactivateAccountController.inactivateAccount("john@john.com"));
        assertEquals("Selected Account does not exist.", exception.getMessage());
    }

    @Test
    void inactivateAccount_AccountAlreadyInactive_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                inactivateAccountController.inactivateAccount("rita@rita.com"));
        assertEquals("Account is already deactivated", exception.getMessage());
    }
}