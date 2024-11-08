package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.switch2022.project.service.AccountService;

@Controller
public class InactivateAccountController {
    private final AccountService accountService;

    /**
     * Instantiates a new InactivateAccountController.
     *
     * @param accountService the customer Service instance.
     * @throws IllegalArgumentException when accountService is null.
     */
    @Autowired
    public InactivateAccountController(AccountService accountService) {
        if (accountService == null) {
            throw new IllegalArgumentException("Account Service cannot be null");
        }
        this.accountService = accountService;
    }

    /**
     * Inactivates an account by changing the boolean  accountStatusValue.
     *
     * @param emailValue The email address of the that identifies
     *                   the account to be inactivated.
     * @return true if account is successfully inactivated.
     * @throws IllegalArgumentException if account has already been deactivated.
     */
    public boolean inactivateAccount(String emailValue) {
        try {
            boolean accountStatusValue = false;
            return accountService.changeAccountStatus(emailValue, accountStatusValue);
        } catch (IllegalStateException illegalArgumentException) {
            throw new IllegalArgumentException("Account is already deactivated");
        }
    }
}