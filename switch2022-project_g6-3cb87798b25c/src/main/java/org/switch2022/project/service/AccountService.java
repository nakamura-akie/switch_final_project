package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.account.AccountFactory;
import org.switch2022.project.domain.valueobject.AccountName;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.PhoneNumber;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.AccountRepository;
import org.switch2022.project.repository.interfaces.ProfileRepository;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.assembler.CreateAccountAndStatusListAssembler;
import org.switch2022.project.utils.dto.AccountAndStatusDTO;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;
    private final AccountFactory accountFactory;

    /**
     * Instantiates a new Account service.
     *
     * @param accountRepository the account repository
     * @param profileRepository the profile repository
     * @param accountFactory    the account factory
     */
    @Autowired
    public AccountService(AccountRepository accountRepository,
                          ProfileRepository profileRepository,
                          AccountFactory accountFactory) {

        if (accountRepository == null) {
            throw new IllegalArgumentException("Account Repository cannot be null");
        }

        if (profileRepository == null) {
            throw new IllegalArgumentException("Profile Repository cannot be null");
        }

        if (accountFactory == null) {
            throw new IllegalArgumentException("Account Factory cannot be null");
        }

        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
        this.accountFactory = accountFactory;
    }

    /**
     * Creates a new account with the specified account name, email, photo, and phone number.
     *
     * @param accountNameValue  the value of the account name for the new account
     * @param emailValue  the value of the email for the new account
     * @param photo  the profile photo for the new account
     * @param phoneNumberValue  the value of the phone number for the new account
     * @return the newly created account
     * @throws IllegalArgumentException if the account already exists or if the profile does not exist
     */
    public Account createAccount(String accountNameValue, String emailValue, Image photo, int phoneNumberValue) {
        AccountName accountName = new AccountName(accountNameValue);
        Email email = new Email(emailValue);
        PhoneNumber phoneNumber = new PhoneNumber(phoneNumberValue);
        ProfileName profileName = new ProfileName("user");

        boolean profileExists = profileRepository.existsById(profileName);
        boolean accountExists = accountRepository.existsById(email);

        if (accountExists) {
            throw new IllegalArgumentException("Account already exists.");
        }
        if (!profileExists){
            throw new IllegalArgumentException("Profile does not exist.");
        }

        Account newAccount = accountFactory.createAccount(accountName, email, photo, phoneNumber, profileName);
        return accountRepository.save(newAccount);
    }

    /**
     * Changes the profile of a user account identified by the specified account email.
     *
     * @param accountEmail the email of the account whose profile is to be changed
     * @param profileName  the name of the profile to assign to the account
     * @return {@code true} if the profile was successfully changed, {@code false} otherwise
     */
    public boolean changeProfileOfUserAccount(String accountEmail, String profileName) {
        ProfileName profileID = new ProfileName(profileName);

        boolean profileExists = this.profileRepository.existsById(profileID);
        boolean result = false;

        if (profileExists) {
            Email accountID = new Email(accountEmail);
            Account account = getAccount(accountID);

            account.changeProfileOfUserAccount(profileID);
            accountRepository.save(account);
            result = true;
        }
        return result;
    }

    /**
     * Retrieves the account associated with the specified email ID.
     *
     * @param id the email ID of the account to retrieve
     * @return the account associated with the provided email ID
     * @throws IllegalArgumentException if the selected account does not exist
     */
    private Account getAccount(Email id) {
        Optional<Account> selectedAccount = accountRepository.findById(id);

        if (selectedAccount.isPresent()) {
            return selectedAccount.get();
        } else {
            throw new IllegalArgumentException("Selected Account does not exist.");
        }
    }

    /**
     * Changes the status of an account identified by the specified email.
     *
     * @param emailValue         the email of the account whose status is to be changed
     * @param accountStatusValue the new status value for the account
     * @return {@code true} if the account status was successfully changed, {@code false} otherwise
     * @throws IllegalArgumentException if the selected account does not exist
     */

    public boolean changeAccountStatus(String emailValue, boolean accountStatusValue) {
        Email email = new Email(emailValue);
        Optional<Account> accountOptional = accountRepository.findById(email);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            AccountStatus accountStatus = new AccountStatus(accountStatusValue);
            return account.setAccountStatus(accountStatus);
        } else {
            throw new IllegalArgumentException("Selected Account does not exist.");
        }
    }

    /**
     * Creates a list of {@link AccountAndStatusDTO} objects representing the accounts and their statuses.
     *
     * @return A list of {@link AccountAndStatusDTO} objects.
     */

    public List<AccountAndStatusDTO> createAccountAndStatusList() {
        Iterable<Account> accountList = accountRepository.findAll();
        return CreateAccountAndStatusListAssembler.createAccountAndStatusList(accountList);
    }
}
