package org.switch2022.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.jpa.account.AccountJPA;
import org.switch2022.project.datamodel.assembler.AccountDomainDataAssembler;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.AccountRepository;
import org.switch2022.project.repository.jpa.AccountRepositoryJPA;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence implementation of the {@link AccountRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link AccountRepositoryJPA} interface to store accounts.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class AccountRepositoryPersistence implements AccountRepository {
    private final AccountDomainDataAssembler accountAssembler;
    private final AccountRepositoryJPA accountJpaRepository;

    /**
     * Instantiates an AccountRepositoryPersistence.
     *
     * @param accountAssembler     the account domain data assembler
     * @param accountJpaRepository the account JPA repository
     */
    @Autowired
    public AccountRepositoryPersistence(
            AccountDomainDataAssembler accountAssembler,
            AccountRepositoryJPA accountJpaRepository) {

        this.accountAssembler = accountAssembler;
        this.accountJpaRepository = accountJpaRepository;
    }

    /**
     * Saves an account entity.
     *
     * @param entity the account to be saved
     * @return the saved account
     */
    @Override
    public Account save(Account entity) {
        AccountJPA data = accountAssembler.toData(entity);
        AccountJPA savedAccountJpa = accountJpaRepository.save(data);
        return accountAssembler.toDomain(savedAccountJpa);
    }

    /**
     * Retrieves all account in the JPA repository.
     *
     * @return An iterable collection of all accounts in the repository
     */
    @Override
    public Iterable<Account> findAll() {
        List<AccountJPA> data = (List<AccountJPA>) accountJpaRepository.findAll();

        return (data.stream()
                .map(accountAssembler::toDomain)
                .collect(Collectors.toList()));
    }

    /**
     * Retrieves an account with the given email.
     *
     * @param id the email to search for
     * @return An optional containing the account entity if found, an empty optional otherwise
     */
    @Override
    public Optional<Account> findById(Email id) {
        return accountJpaRepository
                .findById(id.getEmailValue())
                .map(accountAssembler::toDomain);

    }

    /**
     * Confirms if an account exists with the given id (email).
     *
     * @param id the email to search for
     * @return true if the account exists, false otherwise
     */
    @Override
    public boolean existsById(Email id) {
        return accountJpaRepository.existsById(id.getEmailValue());
    }

    /**
     * Deletes all accounts from the JPA repository.
     */
    @Override
    public void deleteAll() {
        accountJpaRepository.deleteAll();
    }

    /**
     * Deletes an account with the given id (email).
     *
     * @param id the email to search for
     */
    @Override
    public void deleteById(Email id) {
        accountJpaRepository.deleteById(id.getEmailValue());
    }

    /**
     * Confirms if an account exists with the given email and profile name.
     *
     * @param email       the email to search for
     * @param profileName the profile name to search for
     * @return true if the account exists, false otherwise
     */
    @Override
    public boolean existsByEmailAndProfileName(Email email, ProfileName profileName) {
        return accountJpaRepository.existsByEmailAndProfileName(email.getEmailValue(),
                profileName.getProfileNameValue());
    }
}
