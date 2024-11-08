package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.switch2022.project.service.AccountService;

@Controller
public class ActivateAccountController {
    private final AccountService accountService;

    /**
     * Instantiates a new ActivateAccountController
     *
     * @param accountService which cannot be null.
     * @throws IllegalArgumentException if accountService is null.
     */
    @Autowired
    public ActivateAccountController(AccountService accountService) {
        if (accountService == null) {
            throw new IllegalArgumentException("Account Service cannot be null");
        }
        this.accountService = accountService;
    }

    /**
     * Activates an account by changing the boolean accountStatusValue
     *
     * @param emailValue The email address of the that identifies
     *                   the account to be activated.
     * @return true if account is successfully activated.
     * @throws IllegalArgumentException if account is already active.
     */
    public boolean activateAccount(String emailValue) {
        try {
            boolean accountStatusValue = true;
            return accountService.changeAccountStatus(emailValue, accountStatusValue);
        } catch (IllegalStateException illegalArgumentException) {
            throw new IllegalArgumentException("Account is already active");
        }
    }
}