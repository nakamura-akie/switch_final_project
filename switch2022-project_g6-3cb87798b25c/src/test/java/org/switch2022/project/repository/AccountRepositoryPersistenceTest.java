package org.switch2022.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.assembler.AccountDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.account.AccountJPA;
import org.switch2022.project.domain.account.Account;

import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.repository.jpa.AccountRepositoryJPA;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountRepositoryPersistenceTest {

    private static Iterable<Email> emailList;
    private static List<Account> accountDoubleList;
    private static AccountRepositoryPersistence accountRepositoryPersistence;
    private static AccountDomainDataAssembler accountDomainDataAssembler;
    private static AccountJPA accountJPADouble;
    private static AccountRepositoryJPA accountRepositoryJPADouble;

    @BeforeEach
    void init() {
        accountJPADouble = mock(AccountJPA.class);
        accountRepositoryJPADouble = mock(AccountRepositoryJPA.class);
        accountDomainDataAssembler = mock(AccountDomainDataAssembler.class);
        accountRepositoryPersistence = new AccountRepositoryPersistence(accountDomainDataAssembler,
                accountRepositoryJPADouble);

        Email firstEmail = mock(Email.class);
        Email secondEmail = mock(Email.class);
        Email thirdEmail = mock(Email.class);
        emailList = List.of(firstEmail, secondEmail, thirdEmail);

        AccountJPA accountJPADoubleOne = mock(AccountJPA.class);
        AccountJPA accountJPADoubleTwo = mock(AccountJPA.class);
        AccountJPA accountJPADoubleThree = mock(AccountJPA.class);
        List<AccountJPA> accountJPADoubleList = List.of(accountJPADoubleOne, accountJPADoubleTwo,
                accountJPADoubleThree);

        Account accountDoubleOne = mock(Account.class);
        Account accountDoubleTwo = mock(Account.class);
        Account accountDoubleThree = mock(Account.class);
        accountDoubleList = List.of(accountDoubleOne, accountDoubleTwo, accountDoubleThree);

        when(accountRepositoryJPADouble.findAll()).thenReturn(accountJPADoubleList);

        when(accountRepositoryJPADouble.findById(any())).thenReturn(Optional.of(accountJPADoubleOne));

        when(accountRepositoryJPADouble.findAllById(any())).thenReturn(accountJPADoubleList);

        when(firstEmail.getEmailValue()).thenReturn("jack@jack.com");
        when(accountRepositoryJPADouble.existsById("jack@jack.com")).thenReturn(true);

        when(accountDomainDataAssembler.toDomain(accountJPADoubleOne)).thenReturn(accountDoubleOne);
        when(accountDomainDataAssembler.toDomain(accountJPADoubleTwo)).thenReturn(accountDoubleTwo);
        when(accountDomainDataAssembler.toDomain(accountJPADoubleThree)).thenReturn(accountDoubleThree);
    }

    @Test
    void save() {

        Account accountDouble = mock(Account.class);
        when(accountDomainDataAssembler.toData(accountDouble)).thenReturn(accountJPADouble);
        when(accountRepositoryJPADouble.save(accountJPADouble)).thenReturn(accountJPADouble);
        when(accountDomainDataAssembler.toDomain(accountJPADouble)).thenReturn(accountDouble);
        Account expected = accountDouble;
        Account result = accountRepositoryPersistence.save(accountDouble);

        assertEquals(expected, result);
    }


    @Test
    void findAll() {
        List<Account> expected = accountDoubleList;
        List<Account> result = (List<Account>) accountRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findById() {
        Email email = ((List<Email>) emailList).get(0);

        Optional<Account> expected = Optional.of(accountDoubleList.get(0));
        Optional<Account> result = accountRepositoryPersistence.findById(email);

        assertEquals(expected, result);
    }

    @Test
    void existsById() {
        Email existentEmail = ((List<Email>) emailList).get(0);

        boolean result = accountRepositoryPersistence.existsById(existentEmail);

        assertTrue(result);
    }

    @Test
    void deleteAll() {
        accountRepositoryPersistence.deleteAll();
        verify(accountRepositoryJPADouble).deleteAll();
    }

    @Test
    void deleteById() {
        Email email = mock(Email.class);
        accountRepositoryPersistence.deleteById(email);
        verify(accountRepositoryJPADouble).deleteById(any());
    }


}