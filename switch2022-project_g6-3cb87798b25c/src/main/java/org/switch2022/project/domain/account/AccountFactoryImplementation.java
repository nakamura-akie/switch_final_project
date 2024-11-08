package org.switch2022.project.domain.account;

import org.springframework.stereotype.Service;
import org.switch2022.project.domain.valueobject.*;

import java.awt.*;

/**
 * The AccountFactoryImplementation class is an implementation of the AccountFactory interface.
 */
@Service
public class AccountFactoryImplementation implements AccountFactory {

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
    @Override
    public Account createAccount(AccountName accountName, Email accountEmail, Image photo, PhoneNumber phoneNumber,
                                 ProfileName profileName) {
        AccountStatus accountStatus = new AccountStatus(true);
        return new Account(accountName, accountEmail, photo, phoneNumber, profileName, accountStatus);
    }

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
    @Override
    public Account createAccountJpa(String accountName,
                                    String accountEmail,
                                    Image photo,
                                    Integer accountPhoneNumber,
                                    String accountProfile,
                                    boolean accountStatus) {

        AccountName name = new AccountName(accountName);
        Email email = new Email(accountEmail);
        PhoneNumber phoneNumber = new PhoneNumber(accountPhoneNumber);
        ProfileName profile = new ProfileName(accountProfile);
        AccountStatus status = new AccountStatus(accountStatus);

        return new Account(name, email, photo, phoneNumber, profile, status);
    }
}
