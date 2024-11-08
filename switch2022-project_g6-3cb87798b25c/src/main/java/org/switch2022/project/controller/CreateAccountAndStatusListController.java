package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.switch2022.project.service.AccountService;
import org.switch2022.project.utils.dto.AccountAndStatusDTO;

import java.util.List;

@Controller
public class CreateAccountAndStatusListController {
    private final AccountService accountService;

    /**
     * Instantiates a new CreateAccountAndStatusListController
     *
     * @param accountService which cannot be null.
     * @throws IllegalArgumentException if accountService is null.
     */
    @Autowired
    public CreateAccountAndStatusListController(AccountService accountService) {
        if (accountService == null) {
            throw new IllegalArgumentException("Account Service cannot be null");
        }

        this.accountService = accountService;
    }

    /**
     * Creates a list of AccountAndStatusDTO
     *
     * @return A list of AccountAndStatusDTO.
     */
    public List<AccountAndStatusDTO> createAccountAndStatusList() {
        return accountService.createAccountAndStatusList();
    }

}
