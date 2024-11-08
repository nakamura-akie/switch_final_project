package org.switch2022.project.datamodel.jpa.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * The AccountJPA class represents an account entity in the database.
 */
@Entity
@Table(name = "accounts")
public class AccountJPA {
    private String accountName;
    @Id
    private String email;
    private Integer phoneNumber;
    private String profileName;
    private Boolean accountIsActive;

    /**
     * Constructs a new AccountJPA object with the specified account details.
     *
     * @param accountName     the account name.
     * @param email           the account email.
     * @param phoneNumber     the account phone number.
     * @param profileName     the profile name.
     * @param accountIsActive the account's active status.
     */
    public AccountJPA(String accountName,
                      String email,
                      Integer phoneNumber,
                      String profileName,
                      Boolean accountIsActive) {

        this.accountName = accountName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileName = profileName;
        this.accountIsActive = accountIsActive;
    }

    /**
     * Default constructor for AccountJPA (protected access).
     * This constructor is protected to prevent direct instantiation of AccountJPA objects outside the class hierarchy.
     * Subclasses can use this constructor to create instances of AccountJPA.
     */
    protected AccountJPA() {
    }

    /**
     * Gets the account name.
     *
     * @return the account name.
     */
    public String getAccountName() {
        return this.accountName;
    }

    /**
     * Gets the account email.
     *
     * @return the account email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the account phone number.
     *
     * @return the account phone number.
     */
    public Integer getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Gets the profile name.
     *
     * @return the profile name.
     */
    public String getProfileName() {
        return this.profileName;
    }

    /**
     * Returns the account's active status.
     *
     * @return the account's active status.
     */
    public Boolean getAccountIsActive() {
        return this.accountIsActive;
    }

    /**
     * Checks if this AccountJPA object is equal to another object.
     *
     * @param o the object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o.getClass().equals(this.getClass()))) {
            return false;
        }
        AccountJPA that = (AccountJPA) o;
        return Objects.equals(accountName, that.accountName)
                && Objects.equals(email, that.email)
                && Objects.equals(phoneNumber, that.phoneNumber)
                && Objects.equals(profileName, that.profileName)
                && Objects.equals(accountIsActive, that.accountIsActive);
    }

    /**
     * Computes the hash code for this AccountJPA object.
     *
     * @return the hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(accountName, email, phoneNumber, profileName, accountIsActive);
    }
}

