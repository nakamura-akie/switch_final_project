package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.account.AccountJPA;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactoryImplementation;
import org.switch2022.project.domain.valueobject.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountDomainDataAssemblerTest {

    @Test
    void toData_happyPath_equals() {
        AccountFactoryImplementation factoryDummy = mock(AccountFactoryImplementation.class);
        AccountDomainDataAssembler assembler = new AccountDomainDataAssembler(factoryDummy);

        Account accountDouble = mock(Account.class);

        AccountName name = mock(AccountName.class);
        Email email = mock(Email.class);
        PhoneNumber phoneNumber = mock(PhoneNumber.class);
        ProfileName profile = mock(ProfileName.class);
        AccountStatus status = mock(AccountStatus.class);

        when(accountDouble.getAccountName()).thenReturn(name);
        when(name.toString()).thenReturn("Pedro");
        when(accountDouble.getEmail()).thenReturn(email);
        when(email.getEmailValue()).thenReturn("pedro@pedro.com");
        when(accountDouble.getPhoneNumber()).thenReturn(phoneNumber);
        when(phoneNumber.getPhoneNumberValue()).thenReturn(123456789);
        when(accountDouble.getProfile()).thenReturn(profile);
        when(profile.getProfileNameValue()).thenReturn("user");
        when(accountDouble.getAccountStatus()).thenReturn(status);
        when(status.isStatusActive()).thenReturn(true);

        AccountJPA expected = new AccountJPA(
                "Pedro",
                "pedro@pedro.com",
                123456789,
                "user",
                true);

        AccountJPA result = assembler.toData(accountDouble);

        assertEquals(expected, result);
    }


    @Test
    void toDomain_verifyThatConversionToDomainIsWorkingAsExpected_equals() {
        AccountFactoryImplementation factoryDouble =
                mock(AccountFactoryImplementation.class);

        AccountDomainDataAssembler assembler = new AccountDomainDataAssembler(factoryDouble);

        AccountJPA accountJpa = mock(AccountJPA.class);
        Account expected = mock(Account.class);

        when(accountJpa.getAccountName()).thenReturn("name");
        when(accountJpa.getEmail()).thenReturn("mail@mail.com");
        when(accountJpa.getPhoneNumber()).thenReturn(123456789);
        when(accountJpa.getProfileName()).thenReturn("profile");
        when(accountJpa.getAccountIsActive()).thenReturn(true);

        when(factoryDouble.createAccountJpa(
                "name",
                "mail@mail.com",
                null,
                123456789,
                "profile",
                true)).thenReturn(expected);

        Account result = assembler.toDomain(accountJpa);

        assertEquals(expected, result);
    }
}
