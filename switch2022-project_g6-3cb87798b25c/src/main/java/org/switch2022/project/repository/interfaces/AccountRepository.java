package org.switch2022.project.repository.interfaces;

import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProfileName;

/**
 * Repository interface for managing Account entities.
 */
public interface AccountRepository extends Repository<Account, Email> {

    /**
     * Confirms if an account exists with a given email and profile.
     *
     * @param email       the email to search for
     * @param profileName the profile name to search for
     * @return true if the account exists, false otherwise
     */
    boolean existsByEmailAndProfileName(Email email, ProfileName profileName);
}
