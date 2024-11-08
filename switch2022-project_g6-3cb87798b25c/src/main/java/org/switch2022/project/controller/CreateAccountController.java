package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.service.AccountService;

import java.awt.*;

@Controller
public class CreateAccountController {

    AccountService accountService;

    /**
     * Instantiates a new CreateAccountController
     *
     * @param accountService which cannot be null.
     * @throws IllegalArgumentException if accountService is null.
     */
    @Autowired
    public CreateAccountController(AccountService accountService) {
        if (accountService == null) {
            throw new IllegalArgumentException("Account Service cannot be null");
        }

        this.accountService = accountService;
    }

    /**
     * Creates a new account with the given account name, email, photo, and phone number
     * values.
     *
     * @param accountNameValue Name of the account to be created.
     * @param emailValue       Email address of the account being created.
     * @param photo            Profile picture of the account being created. It is
     *                         an optional parameter, meaning that it can be null
     *                         if the user does not want to upload a profile picture.
     * @param phoneNumberValue Phone number associated with the account.
     * @return Created Account.
     */
    public Account createAccount(String accountNameValue, String emailValue, Image photo, int phoneNumberValue) {
        return accountService.createAccount(accountNameValue, emailValue, photo, phoneNumberValue);
    }
}
