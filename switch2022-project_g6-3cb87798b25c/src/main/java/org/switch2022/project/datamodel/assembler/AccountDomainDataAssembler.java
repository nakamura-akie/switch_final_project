package org.switch2022.project.datamodel.assembler;

import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.jpa.account.AccountJPA;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactory;

@Component
public class AccountDomainDataAssembler {

    private final AccountFactory accountFactory;

    /**
     * Constructs an AccountDomainDataAssembler with the specified AccountFactory.
     *
     * @param accountFactory the {@link AccountFactory} to be used for creating Accounts.
     */
    public AccountDomainDataAssembler(AccountFactory accountFactory) {
        this.accountFactory = accountFactory;
    }

    /**
     * Converts an Account object to its corresponding {@link AccountJPA} entity.
     *
     * @param account the Account domain object to be converted.
     * @return the corresponding JPA entity.
     */
    public AccountJPA toData(Account account) {

        String accountName = account.getAccountName().toString();
        String email = account.getEmail().getEmailValue();
        Integer phoneNumber = account.getPhoneNumber().getPhoneNumberValue();
        String profileName = account.getProfile().getProfileNameValue();
        boolean accountIsActive = account.getAccountStatus().isStatusActive();

        return new AccountJPA(accountName, email, phoneNumber, profileName, accountIsActive);
    }

    /**
     * Converts an AccountJPA entity to its corresponding {@link Account} domain object.
     *
     * @param accountJpa the JPA entity to be converted.
     * @return the corresponding Account domain object.
     */
    public Account toDomain(AccountJPA accountJpa) {
        String accountName = accountJpa.getAccountName();
        String accountEmail = accountJpa.getEmail();
        Integer accountPhoneNumber = accountJpa.getPhoneNumber();
        String accountProfile = accountJpa.getProfileName();
        boolean accountStatus = accountJpa.getAccountIsActive();

        return accountFactory.createAccountJpa(accountName, accountEmail,
                null, accountPhoneNumber, accountProfile, accountStatus);
    }
}
