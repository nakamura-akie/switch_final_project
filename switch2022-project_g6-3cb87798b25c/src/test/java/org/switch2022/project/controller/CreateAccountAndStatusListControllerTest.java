package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactory;
import org.switch2022.project.domain.account.AccountFactoryImplementation;
import org.switch2022.project.domain.valueobject.AccountName;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.PhoneNumber;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.AccountRepositoryFake;
import org.switch2022.project.utils.dto.AccountAndStatusDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class CreateAccountAndStatusListControllerTest {


    @Autowired
    CreateAccountAndStatusListController controller;
    @Qualifier("AccountRepository")
    Repository<Account, Email> accountRepository;


    @BeforeEach
    void init() {
        AccountFactory accountFactory = new AccountFactoryImplementation();
        accountRepository = new AccountRepositoryFake();

        AccountName accountName = new AccountName("Account Name");
        Email email = new Email("email.email@email.com");
        PhoneNumber phoneNumber = new PhoneNumber(123456789);
        ProfileName profileName = new ProfileName("Profile Name");

        Account account = accountFactory.createAccount(accountName, email, null, phoneNumber, profileName);
        accountRepository.save(account);
    }

    @AfterEach
    void tearDown() {
        controller = null;
        accountRepository.deleteAll();
    }

    @Test
    void constructor_NullProjectService_ThrowsException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new CreateAccountAndStatusListController(null);
        });

        assertEquals("Account Service cannot be null", e.getMessage());
    }

    @Test
    void requestAccountAndStatusList_ReturnsListOfAccountAndStatus_Equals() {
        AccountAndStatusDTO accountAndStatusDTO = new AccountAndStatusDTO();
        accountAndStatusDTO.accountName = "Account Name";
        accountAndStatusDTO.email = "email.email@email.com";
        accountAndStatusDTO.accountStatus = "active";

        List<AccountAndStatusDTO> expected = new ArrayList<>();
        expected.add(accountAndStatusDTO);

        List<AccountAndStatusDTO> result = controller.createAccountAndStatusList();

        assertEquals(expected, result);
    }

    @Test
    void requestProjectList_ReturnsEmptyList_Equals() {
        accountRepository.deleteAll();

        List<AccountAndStatusDTO> expected = new ArrayList<>();

        List<AccountAndStatusDTO> result = controller.createAccountAndStatusList();

        assertEquals(expected, result);
    }

}