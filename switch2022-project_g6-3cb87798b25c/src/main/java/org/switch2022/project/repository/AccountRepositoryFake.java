package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A fake implementation of the {@link AccountRepository} interface that uses an in-memory list to store
 * {@link Account} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class AccountRepositoryFake implements AccountRepository {
    private static final List<Account> accountList = new ArrayList<>();


    /**
     * Saves an account to the repository, if it is not already present in the account list.
     *
     * @param account the account to be saved
     * @return the saved account
     */
    @Override
    public Account save(Account account) {
        if (!accountList.contains(account)) {
            accountList.add(account);
        }
        return account;
    }

    /**
     * Retrieves all accounts from the repository.
     *
     * @return An iterable collection of all accounts.
     */
    @Override
    public Iterable<Account> findAll() {
        return new ArrayList<>(accountList);
    }

    /**
     * Retrieves an account with the given email.
     *
     * @param email the email to search for
     * @return An optional containing the account if found, an empty optional otherwise
     */
    @Override
    public Optional<Account> findById(Email email) {
        for (Account account : accountList) {
            if (account.identity().equals(email)) {
                return Optional.of(account);
            }
        }

        return Optional.empty();
    }

    /**
     * Confirms if an account exists with the given email.
     *
     * @param email the email to search for
     * @return true if the account exists, false otherwise
     */
    @Override
    public boolean existsById(Email email) {
        for (Account account : accountList) {
            if (account.identity().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all accounts from the repository.
     */
    @Override
    public void deleteAll() {
        accountList.clear();
    }

    /**
     * Deletes the account with the given email.
     *
     * @param id the email (id) to search for
     * @throws UnsupportedOperationException when method is called because this method is not yet supported by the
     *                                       Account Repository.
     */
    @Override
    public void deleteById(Email id) {
        throw new UnsupportedOperationException("Account Repository doesn't support the deleteByID method yet");
    }

    /**
     * Confirms if an account exists with the given email and profile name.
     *
     * @param email       The email to search for
     * @param profileName The profile name to search for
     * @return true if an account exists, false otherwise.
     */
    @Override
    public boolean existsByEmailAndProfileName(Email email, ProfileName profileName) {
        return accountList.stream()
                .anyMatch(account -> account.getEmail().equals(email)
                        && account.getProfile().equals(profileName));
    }
}