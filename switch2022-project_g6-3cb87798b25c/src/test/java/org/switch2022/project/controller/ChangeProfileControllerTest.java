package org.switch2022.project.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactory;
import org.switch2022.project.domain.account.AccountFactoryImplementation;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.profile.ProfileFactory;
import org.switch2022.project.domain.profile.ProfileFactoryImplementation;
import org.switch2022.project.domain.valueobject.AccountName;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.PhoneNumber;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.AccountRepositoryFake;
import org.switch2022.project.repository.ProfileRepositoryFake;
import org.switch2022.project.repository.interfaces.AccountRepository;
import org.switch2022.project.repository.interfaces.ProfileRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ChangeProfileControllerTest {

    @Autowired
    ChangeProfileController changeProfileController;
    static AccountRepository accountRepository;
    static ProfileRepository profileRepository;

    static Account accountOne;
    static Account accountTwo;
    static Account accountThree;
    static Email firstAccountID;
    static Email secondAccountID;
    static Email thirdAccountID;

    @BeforeAll
    static void init() {
        //---Account IDs
        accountRepository = new AccountRepositoryFake();
        profileRepository = new ProfileRepositoryFake();
        ProfileFactory profileFactory = new ProfileFactoryImplementation();

        //Creating Initial Profiles
        Profile admin = profileFactory.createProfile(new ProfileName(
                "administrator"));
        profileRepository.save(admin);

        Profile user = profileFactory.createProfile(new ProfileName(
                "user"));
        profileRepository.save(user);

        Profile manager = profileFactory.createProfile(new ProfileName(
                "manager"));
        profileRepository.save(manager);

        firstAccountID = new Email("email_one@manager.pt");
        secondAccountID = new Email("email_two@admin.pt");
        thirdAccountID = new Email("email_three@user.pt");

        AccountFactory accountFactory = new AccountFactoryImplementation();

        accountOne = accountFactory.createAccount(
                new AccountName("Account One"),
                firstAccountID,
                null,
                new PhoneNumber(123456789),
                new ProfileName("Manager"));
        accountRepository.save(accountOne);

        accountTwo = accountFactory.createAccount(
                new AccountName("Account Two"),
                secondAccountID,
                null,
                new PhoneNumber(987654321),
                new ProfileName("ADMINISTRATOR"));
        accountRepository.save(accountTwo);

        accountThree = accountFactory.createAccount(
                new AccountName("Account 3"),
                thirdAccountID,
                null,
                new PhoneNumber(192837465),
                new ProfileName("user"));
        accountRepository.save(accountThree);

    }

    @AfterAll
    static void tearDown() {
        accountRepository.deleteAll();
        profileRepository.deleteAll();

        accountRepository = null;
        profileRepository = null;
    }

    @Test
    void constructor_nullAccountService_ThrowsException() {
        //Assert
        String expected = "Account Service cannot be null.";
        //Act
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                changeProfileController = new ChangeProfileController(null));
        //Assert
        assertEquals(expected,e.getMessage());
    }

    @Test
    void changeProfileOfExistingAccount_AccountAndProfileExist_True() {
        //Act
        boolean result =
                changeProfileController.changeProfileOfAccount(
                        "email_three@user.pt", "user");

        //Assert
        assertTrue(result);
    }

    @Test
    void changeProfileOfExistingAccount_ProfileDoesNotExist_False() {
        //Act
        boolean result =
                changeProfileController.changeProfileOfAccount(
                        "email_three@user.pt", "xpto");

        //Assert
        assertFalse(result);
    }

    @Test
    void changeProfileOfExistingAccount_AccountDoesNotExist_ThrowsException() {
        //Arrange
        String expected = "Selected Account does not exist.";

        //Act
        String result = assertThrows(IllegalArgumentException.class, () ->
                changeProfileController.changeProfileOfAccount(
                        "email_four@user.pt", "administrator")).getMessage();

        //Assert
        assertEquals(expected, result);
    }
}