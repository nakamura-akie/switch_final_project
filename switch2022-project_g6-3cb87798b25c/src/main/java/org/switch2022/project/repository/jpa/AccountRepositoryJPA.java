package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.jpa.account.AccountJPA;

/**
 * Repository interface for managing AccountJPA entities in the database.
 */
public interface AccountRepositoryJPA extends CrudRepository<AccountJPA, String> {

    /**
     * Confirms if an account exists with the given email and profile
     *
     * @param email   the email to search for
     * @param profile the profile to search for
     * @return true if the account is found, false otherwise
     */
    boolean existsByEmailAndProfileName(String email, String profile);
}
