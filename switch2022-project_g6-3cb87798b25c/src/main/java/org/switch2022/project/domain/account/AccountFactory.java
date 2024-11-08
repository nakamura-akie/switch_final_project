package org.switch2022.project.domain.account;

import org.switch2022.project.domain.valueobject.*;

import java.awt.*;

/**
 * The AccountFactory interface defines methods for creating Account objects.
 */
public interface AccountFactory {

    /**
     * Creates an Account object with the specified parameters.
     *
     * @param accountName the account name
     * @param accountEmail the email address
     * @param photo the account photo
     * @param phoneNumber the phone number
     * @param profileName the profile name
     * @return the created Account object
     */
    Account createAccount(AccountName accountName,
                          Email accountEmail,
                          Image photo,
                          PhoneNumber phoneNumber,
                          ProfileName profileName);

    /**
     * Creates an AccountJPA object with the specified parameters.
     *
     * @param accountName        the account name
     * @param accountEmail       the email address
     * @param photo              the account photo
     * @param accountPhoneNumber the phone number
     * @param accountProfile     the profile name
     * @param accountStatus      the account status
     * @return the created Account object
     */
    Account createAccountJpa(String accountName,
                             String accountEmail,
                             Image photo,
                             Integer accountPhoneNumber,
                             String accountProfile,
                             boolean accountStatus);
}
