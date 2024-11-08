package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactory;
import org.switch2022.project.domain.valueobject.AccountStatus;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.AccountRepository;
import org.switch2022.project.repository.interfaces.ProfileRepository;
import org.switch2022.project.utils.dto.AccountAndStatusDTO;
import org.switch2022.project.utils.assembler.CreateAccountAndStatusListAssembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepositoryDouble;

    @MockBean
    private ProfileRepository profileRepositoryDouble;

    @MockBean
    private AccountFactory accountFactoryDouble;

    private List<Account> accountList;
    private List<AccountAndStatusDTO> accountAndStatusDTOList;
    private List<Account> emptyAccountAndStatusList;
    private List<AccountAndStatusDTO> emptyDTOList;
    private MockedStatic<CreateAccountAndStatusListAssembler> createAccountAndStatusListAssembler;

    private ProfileName userProfileName;
    private Account accountDouble;
    private String name;
    private String email;
    private int phoneNumber;

    @BeforeEach
    void init() {
        userProfileName = new ProfileName("user");
        accountDouble = mock(Account.class);

        name = "Sofia";
        email = "sofia@sofia.com";
        phoneNumber = 123456789;

        this.createAccountAndStatusListAssembler = mockStatic(CreateAccountAndStatusListAssembler.class);

        this.accountList = new ArrayList<>();
        accountList.add(accountDouble);

        this.emptyAccountAndStatusList = new ArrayList<>();

        AccountAndStatusDTO firstAccountAndStatusDTO = mock(AccountAndStatusDTO.class);
        this.accountAndStatusDTOList = new ArrayList<>();
        accountAndStatusDTOList.add(firstAccountAndStatusDTO);

        this.emptyDTOList = new ArrayList<>();


    }

    @AfterEach
    void tearDown() {
        accountList = null;
        accountAndStatusDTOList = null;
        emptyDTOList = null;
        accountDouble = null;
        userProfileName = null;
        createAccountAndStatusListAssembler.close();
    }

    @Test
    void constructor_NullAccountRepository_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AccountService(null, profileRepositoryDouble, accountFactoryDouble));

        assertEquals("Account Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_NullProfileRepository_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AccountService(accountRepositoryDouble, null, accountFactoryDouble));

        assertEquals("Profile Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_NullAccountFactory_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AccountService(accountRepositoryDouble, profileRepositoryDouble, null));

        assertEquals("Account Factory cannot be null", exception.getMessage());
    }

    @Test
    void createAccount_SuccessfulCreation_True() {
        when(accountRepositoryDouble.existsById(any())).thenReturn(false);
        when(profileRepositoryDouble.existsById(userProfileName)).thenReturn(true);
        when(accountFactoryDouble.createAccount(any(), any(), any(),
                any(), any())).thenReturn(accountDouble);
        when(accountRepositoryDouble.save(accountDouble)).thenReturn(accountDouble);


        Account result = accountService.createAccount(name, email, null, phoneNumber);

        assertEquals(accountDouble, result);
    }

    @Test
    void createAccount_AccountExists_False() {
        when(accountRepositoryDouble.existsById(any())).thenReturn(true);
        when(profileRepositoryDouble.existsById(userProfileName)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                accountService.createAccount(name, email, null, phoneNumber));
        assertEquals("Account already exists.", exception.getMessage());
    }

    @Test
    void createAccount_ProfileDoesNotExist_False() {
        when(accountRepositoryDouble.existsById(any())).thenReturn(false);
        when(profileRepositoryDouble.existsById(userProfileName)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                accountService.createAccount(name, email, null, phoneNumber));
        assertEquals("Profile does not exist.", exception.getMessage());
    }

    @Test
    void changeProfileOfUserAccount_ProfileDoesNotExist_False() {
        //Arrange
        when(profileRepositoryDouble.existsById(userProfileName)).thenReturn(false);

        //Act
        boolean result = accountService.changeProfileOfUserAccount("email@email" +
                ".pt", "user");

        //Assert
        assertFalse(result);
    }

    @Test
    void changeProfileOfUserAccount_AccountDoesNotExist_ThrowsException() {
        //Arrange
        String expected = "Selected Account does not exist.";
        when(profileRepositoryDouble.existsById(userProfileName)).thenReturn(true);

        //Act
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                accountService.changeProfileOfUserAccount("email@email.pt", "user"));

        //Assert
        assertEquals(expected, e.getMessage());
    }

    @Test
    void changeProfileOfUserAccount_HappyPath_True() {
        //Arrange
        Account accountDouble = mock(Account.class);
        when(profileRepositoryDouble.existsById(userProfileName)).thenReturn(true);
        when(accountRepositoryDouble.findById(new Email("email@email.pt"))).thenReturn(Optional.of(accountDouble));

        //Act
        boolean result = accountService.changeProfileOfUserAccount("email@email.pt", "user");

        //Assert
        assertTrue(result);
        verify(accountDouble, times(1)).changeProfileOfUserAccount(any());
    }

    @Test
    void requestAccountList_ReturnsListOfAccounts_Equals() {
        when(this.accountRepositoryDouble.findAll()).thenReturn(accountList);
        this.createAccountAndStatusListAssembler.when(() -> CreateAccountAndStatusListAssembler.createAccountAndStatusList(accountList)).thenReturn(accountAndStatusDTOList);

        List<AccountAndStatusDTO> result = accountService.createAccountAndStatusList();

        assertEquals(accountAndStatusDTOList, result);
    }

    @Test
    void requestAccountList_ReturnsEmptyList_Equals() {
        when(this.accountRepositoryDouble.findAll()).thenReturn(emptyAccountAndStatusList);
        this.createAccountAndStatusListAssembler.when(() -> CreateAccountAndStatusListAssembler.createAccountAndStatusList(emptyAccountAndStatusList)).thenReturn(emptyDTOList);

        List<AccountAndStatusDTO> result = accountService.createAccountAndStatusList();

        assertEquals(emptyDTOList, result);
    }

    @Test
    void changeAccountStatus_Inactivate_EverythingGoesWell_True() {
        Email emailVO = new Email(email);
        AccountStatus accountStatus = new AccountStatus(false);

        when(accountRepositoryDouble.findById(emailVO)).thenReturn(Optional.of(accountDouble));
        when(accountDouble.setAccountStatus(accountStatus)).thenReturn(true);

        assertTrue(accountService.changeAccountStatus("sofia@sofia.com", false));
    }

    @Test
    void changeAccountStatus_Inactivate_AccountDoesNotExist_Exception() {
        Email emailVO = new Email(email);

        when(accountRepositoryDouble.findById(emailVO)).thenReturn(Optional.empty());

        String result = assertThrows(IllegalArgumentException.class, () -> accountService.changeAccountStatus(email, false)).getMessage();
        String expected = "Selected Account does not exist.";
        assertEquals(expected, result);
    }

    @Test
    void changeAccountStatus_Inactivate_StatusAlreadyInactiveTriesToInactivate_Exception() {
        Email emailVO = new Email(email);
        AccountStatus accountStatus = new AccountStatus(false);

        when(accountRepositoryDouble.findById(emailVO)).thenReturn(Optional.of(accountDouble));
        when(accountDouble.setAccountStatus(accountStatus)).thenReturn(false);
        IllegalStateException exception = new IllegalStateException("The account already has the chosen status");
        when(accountDouble.setAccountStatus(accountStatus)).thenThrow(exception);
        String result = assertThrows(IllegalStateException.class, () -> accountService.changeAccountStatus("sofia@sofia.com", false)).getMessage();
        String expected = "The account already has the chosen status";
        assertEquals(expected, result);
    }

    @Test
    void changeAccountStatus_Activate_EverythingGoesWell_True() {
        Email emailVO = new Email(email);
        AccountStatus accountStatus = new AccountStatus(true);

        when(accountRepositoryDouble.findById(emailVO)).thenReturn(Optional.of(accountDouble));
        when(accountDouble.setAccountStatus(accountStatus)).thenReturn(true);

        assertTrue(accountService.changeAccountStatus("sofia@sofia.com", true));
    }

    @Test
    void changeAccountStatus_Activate_AccountDoesNotExist_Exception() {
        Email emailVO = new Email(email);

        when(accountRepositoryDouble.findById(emailVO)).thenReturn(Optional.empty());

        String result = assertThrows(IllegalArgumentException.class, () -> accountService.changeAccountStatus(email, true)).getMessage();
        String expected = "Selected Account does not exist.";
        assertEquals(expected, result);
    }

    @Test
    void changeAccountStatus_Activate_StatusAlreadyActiveTriesToActivate_Exception() {
        Email emailVO = new Email(email);
        AccountStatus accountStatus = new AccountStatus(true);

        when(accountRepositoryDouble.findById(emailVO)).thenReturn(Optional.of(accountDouble));
        IllegalStateException exception = new IllegalStateException("The account already has the chosen status");
        when(accountDouble.setAccountStatus(accountStatus)).thenThrow(exception);
        String result = assertThrows(IllegalStateException.class, () -> accountService.changeAccountStatus("sofia@sofia.com", true)).getMessage();
        String expected = "The account already has the chosen status";
        assertEquals(expected, result);
    }
}