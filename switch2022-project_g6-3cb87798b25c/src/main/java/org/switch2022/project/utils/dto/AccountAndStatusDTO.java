package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The AccountAndStatusDTO class represents a data transfer object that holds information about an account.
 * It includes the email, account status, and account name.
 */
public class AccountAndStatusDTO {

    public String email;
    public String accountStatus;
    public String accountName;

    /**
     * Checks whether the current object is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountAndStatusDTO)) {
            return false;
        }
        AccountAndStatusDTO that = (AccountAndStatusDTO) o;
        return accountName.equals(that.accountName)
                && email.equals(that.email)
                && accountStatus.equals(that.accountStatus
        );
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(accountName, email, accountStatus);
    }
}

