package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactory;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.profile.ProfileFactory;
import org.switch2022.project.domain.valueobject.AccountName;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.PhoneNumber;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateAccountControllerTest {

    @SpringBootTest
    @Nested
    @ActiveProfiles("test")
    class UnitTest {

        @Autowired
        CreateAccountController controller;

        @MockBean
        AccountService accountServiceDouble;
        String name;
        String email;
        int phoneNumber;

        Account accountDouble;

        @BeforeEach
        void init() {
            name = "Diana";
            email = "diana@diana.pt";
            phoneNumber = 123456789;
            accountDouble = mock(Account.class);
        }

        @Test
        void constructor_NullService_ThrowsException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new CreateAccountController(null));

            assertEquals("Account Service cannot be null", exception.getMessage());
        }

        @Test
        void createAccount_SuccessfullyCreatesAccount_Account() {
            when(accountServiceDouble.createAccount(name, email, null, phoneNumber)).thenReturn(accountDouble);

            Account result = controller.createAccount(name, email, null, phoneNumber);

            assertEquals(accountDouble, result);
        }
    }

    @SpringBootTest
    @Nested
    @ActiveProfiles("test")
    class IntegrationTest {

        @Autowired
        CreateAccountController controller;

        @Autowired
        Repository<Account, Email> accountRepository;

        @Autowired
        Repository<Profile, ProfileName> profileRepository;

        @Autowired
        ProfileFactory profileFactory;

        @Autowired
        AccountFactory accountFactory;

        @BeforeEach
        void init() {
            //to ensure profile user exists in the repository
            Profile userProfile = profileFactory.createProfile(new ProfileName("user"));
            profileRepository.save(userProfile);
        }

        @AfterEach
        void tearDown() {
            accountRepository.deleteAll();
            profileRepository.deleteAll();
        }

        @Test
        void createAccount_HappyPath_True() {
            Account expected = accountFactory.createAccount(new AccountName("Maria"),
                    new Email("maria@gmail.com"), null, new PhoneNumber(123456789), new ProfileName("user"));

            Account result = controller.createAccount("Maria",
                    "maria@gmail.com", null, 123456789);

            assertEquals(expected, result);
        }

        @Test
        void createAccount_ExistingAccount_False() {
            controller.createAccount("Maria",
                    "maria@gmail.com", null, 123456789);

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    controller.createAccount("Maria",
                            "maria@gmail.com", null, 123456789));
            assertEquals("Account already exists.", exception.getMessage());
        }

        @Test
        void createAccount_ProfileDoesNotExist_False() {
            //remove profile user
            ProfileName user = new ProfileName("user");
            profileRepository.deleteById(user);

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    controller.createAccount("Maria",
                            "maria@gmail.com", null, 123456789));

            assertEquals("Profile does not exist.", exception.getMessage());


            //add profile user back to the repository
            Profile userProfile = profileFactory.createProfile(new ProfileName("user"));
            profileRepository.save(userProfile);
        }
    }
}

