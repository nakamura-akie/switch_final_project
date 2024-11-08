package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.valueobject.AccountName;
import org.switch2022.project.domain.valueobject.AccountStatus;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.utils.dto.AccountAndStatusDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The CreateAccountAndStatusListAssembler class is responsible for converting Account objects
 * into AccountAndStatusDTO objects.
 */
public final class CreateAccountAndStatusListAssembler {

    /**
     * Private constructor to prevent instantiation of the CreateAccountAndStatusListAssembler class.
     */
    private CreateAccountAndStatusListAssembler() {}

    /**
     * Converts an iterable collection of Account objects into a list of AccountAndStatusDTO objects.
     *
     * @param accountList the iterable collection of Account objects to convert
     * @return a list of AccountAndStatusDTO objects
     */
    public static List<AccountAndStatusDTO> createAccountAndStatusList(Iterable<Account> accountList) {
        List<AccountAndStatusDTO> accountAndStatusDTOList = new ArrayList<>();

        accountList.forEach(account -> {
            AccountAndStatusDTO accountAndStatusDTO = generateDTO(account);
            accountAndStatusDTOList.add(accountAndStatusDTO);
        });
        return accountAndStatusDTOList;
    }

    /**
     * Generates an AccountAndStatusDTO object from the given Account object.
     *
     * @param account the Account object to convert
     * @return the generated AccountAndStatusDTO object
     */
    private static AccountAndStatusDTO generateDTO(Account account) {
        AccountAndStatusDTO accountAndStatusDTO = new AccountAndStatusDTO();

        AccountName accountName = account.getAccountName();
        accountAndStatusDTO.accountName = accountName.toString();

        Email email = account.identity();
        accountAndStatusDTO.email = email.getEmailValue();

        AccountStatus accountStatus = account.getAccountStatus();
        accountAndStatusDTO.accountStatus = accountStatus.getValue();

        return accountAndStatusDTO;
    }
}
