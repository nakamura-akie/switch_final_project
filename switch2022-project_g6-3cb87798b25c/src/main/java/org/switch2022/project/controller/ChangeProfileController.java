package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.switch2022.project.service.AccountService;

@Controller
public class ChangeProfileController {

    private final AccountService accountService;

    /**
     * Instantiates a new ChangeProfileController
     *
     * @param accountService AccountService instance.
     * @throws IllegalArgumentException if accountService is null.
     */
    @Autowired
    public ChangeProfileController(AccountService accountService) {
        if (accountService == null) {
            throw new IllegalArgumentException("Account Service cannot be " +
                    "null.");
        }
        this.accountService = accountService;
    }

    /**
     * Updates profile associated to an account,
     * which in turn is associated to an email
     *
     * @param userEmail      The email address of the that identifies
     *                       the account to which the profile is associated.
     * @param desiredProfile name of the profile.
     * @return true if profile is successfully updated.
     */
    public boolean changeProfileOfAccount(String userEmail, String desiredProfile) {

        return accountService.changeProfileOfUserAccount(userEmail, desiredProfile);
    }
}
