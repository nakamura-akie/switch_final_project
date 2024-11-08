package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProfileName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountRepositoryFakeTest {

    Repository<Account, Email> accountRepository;
    Account accountOneDouble;
    Account accountTwoDouble;
    Email emailOneDouble;
    Email emailTwoDouble;

    @BeforeEach
    void init() {
        accountRepository = new AccountRepositoryFake();

        accountOneDouble = mock(Account.class);
        accountTwoDouble = mock(Account.class);

        emailOneDouble = mock(Email.class);
        emailTwoDouble = mock(Email.class);

        when(accountOneDouble.identity()).thenReturn(emailOneDouble);
        when(accountTwoDouble.identity()).thenReturn(emailTwoDouble);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountOneDouble = null;
        accountTwoDouble = null;
        emailOneDouble = null;
        emailTwoDouble = null;
    }

    @Test
    void save_SuccessfullySaves_True() {
        Account result = accountRepository.save(accountOneDouble);

        assertEquals(accountOneDouble, result);
    }

    @Test
    void save_ExistingAccount_False() {
        accountRepository.save(accountOneDouble);

        Account result = accountRepository.save(accountOneDouble);

        assertEquals(accountOneDouble, result);
    }

    @Test
    void findAll_ReturnsCompleteList_True() {
        accountRepository.save(accountOneDouble);
        accountRepository.save(accountTwoDouble);

        List<Account> expectedList = new ArrayList<>();
        expectedList.add(accountOneDouble);
        expectedList.add(accountTwoDouble);
        Iterable<Account> expected = expectedList;

        Iterable<Account> result = accountRepository.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findByID_SuccessfullyReturnsAccount_Equals() {
        accountRepository.save(accountOneDouble);
        accountRepository.save(accountTwoDouble);

        Optional<Account> expected = Optional.of(accountOneDouble);

        Optional<Account> result = accountRepository.findById(emailOneDouble);

        assertEquals(expected, result);
    }

    @Test
    void findByID_AccountDoesNotExist_EmptyOptional() {
        Optional<Account> expected = Optional.empty();

        Optional<Account> result = accountRepository.findById(emailOneDouble);

        assertEquals(expected, result);
    }

    @Test
    void existsByID_AccountExists_True() {
        accountRepository.save(accountOneDouble);

        boolean result = accountRepository.existsById(emailOneDouble);

        assertTrue(result);
    }

    @Test
    void existsByID_AccountDoesNotExist_False() {
        accountRepository.save(accountOneDouble);

        boolean result = accountRepository.existsById(emailTwoDouble);

        assertFalse(result);
    }

    @Test
    void deleteAll_DeletesAll_True() {
        accountRepository.save(accountOneDouble);

        accountRepository.deleteAll();

        List<Account> expectedList = new ArrayList<>();
        Iterable<Account> expected = expectedList;

        Iterable<Account> result = accountRepository.findAll();

        assertEquals(expected, result);
    }

    @Test
    void deleteByID_ThrowsException_True() {
        Exception exception = assertThrows(UnsupportedOperationException.class, () ->
                accountRepository.deleteById(emailOneDouble));

        assertEquals("Account Repository doesn't support the deleteByID method yet",
                exception.getMessage());
    }

}