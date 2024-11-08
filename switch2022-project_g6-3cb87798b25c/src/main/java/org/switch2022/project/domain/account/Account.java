package org.switch2022.project.domain.account;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.*;

import java.awt.*;
import java.util.Objects;

/**
 * The Account class represents a user account in the domain model.
 * It implements the AggregateRoot interface with the identity of the account being the email address.
 */
public class Account implements AggregateRoot<Email> {

    private final AccountName name;
    private final Email email;
    private final Image photo;
    private final PhoneNumber phoneNumber;
    private ProfileName profileName;
    private AccountStatus accountStatus;

    /**
     * Instantiates a new Account.
     *
     * @param name          the name
     * @param email         the email
     * @param photo         the photo
     * @param phoneNumber   the phone number
     * @param profileName   the profile name
     * @param accountStatus the account status
     */
    protected Account(AccountName name, Email email, Image photo, PhoneNumber phoneNumber,
                      ProfileName profileName, AccountStatus accountStatus) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.phoneNumber = phoneNumber;
        this.profileName = profileName;
        this.accountStatus = accountStatus;
    }

    /**
     * Checks if account has a specific profile.
     *
     * @param profileName the profile name
     * @return true or false
     */
    public boolean hasProfile(ProfileName profileName) {
        return this.profileName.equals(profileName);
    }

    /**
     * Sets account status.
     *
     * @param accountStatus the account status
     * @return true if the account status is set or false otherwise
     */
    public boolean setAccountStatus(AccountStatus accountStatus) {
        if (hasAccountStatus(accountStatus)) {
            throw new IllegalStateException("The account already has the chosen status");
        }

        this.accountStatus = accountStatus;
        return true;
    }

    /**
     * Checks if account has status.
     *
     * @param accountStatus the account status
     * @return true if the account has the status or false otherwise.
     */
    public boolean hasAccountStatus(AccountStatus accountStatus) {
        return this.accountStatus.equals(accountStatus);
    }

    /**
     * Gets account status.
     *
     * @return the account status
     */
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    /**
     * Change the profile name of the user account.
     *
     * @param profileName The new profile name to be set.
     */
    public void changeProfileOfUserAccount(ProfileName profileName) {
        this.profileName = profileName;
    }

    /**
     * Get the account name of the user account.
     *
     * @return The account name.
     */
    public AccountName getAccountName() {
        return this.name;
    }

    /**
     * Get the profile name of the user account.
     *
     * @return The profile name.
     */
    public ProfileName getProfile() {
        return this.profileName;
    }

    /**
     * Get the email address of the user account.
     *
     * @return The email address.
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Get the phone number of the user account.
     *
     * @return The phone number.
     */
    public PhoneNumber getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Returns the identity of this account, which is the email associated with the account.
     *
     * @return the email identity of the account
     */
    @Override
    public Email identity() {
        return this.email;
    }

    /**
     * Checks if this account parameters are the same as the given object, except the email.
     *
     * @param object the object to compare
     * @return true if the object is the same as this account, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof Account) {
            Account that = (Account) object;

            return this.name.equals(that.name)
                    && this.phoneNumber.equals(that.phoneNumber)
                    && this.profileName.equals(that.profileName)
                    && this.accountStatus.equals(that.accountStatus);
        }
        return false;
    }

    /**
     * Checks if this account is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this account, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account that = (Account) o;
        return this.email.equals(that.email);
    }

    /**
     * Generates the hash code value for the account.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

