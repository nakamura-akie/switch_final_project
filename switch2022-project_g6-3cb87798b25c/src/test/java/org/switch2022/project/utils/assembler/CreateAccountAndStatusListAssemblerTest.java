package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.account.Account;
import org.switch2022.project.domain.valueobject.AccountName;
import org.switch2022.project.domain.valueobject.AccountStatus;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.utils.dto.AccountAndStatusDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateAccountAndStatusListAssemblerTest {

        private List<Account> accountList;

        @BeforeEach
        void init() {
            accountList = new ArrayList<>();

            Account firstAccount = mock(Account.class);
            this.accountList.add(firstAccount);

            AccountName accountName = new AccountName("Account Name");
            Email email = new Email("email.email@email.com");
            AccountStatus accountStatus = new AccountStatus(true);

            when(firstAccount.identity()).thenReturn(email);
            when(firstAccount.getAccountName()).thenReturn(accountName);
            when(firstAccount.getAccountStatus()).thenReturn(accountStatus);
        }

        @AfterEach
        void tearDown(){
            accountList = null;
        }

        @Test
        void createAccountList_ReturnsListOfAccounts_Equals() {
            AccountAndStatusDTO firstAccountAndStatusDTO = new AccountAndStatusDTO();
            firstAccountAndStatusDTO.accountName = "Account Name";
            firstAccountAndStatusDTO.email = "email.email@email.com";
            firstAccountAndStatusDTO.accountStatus = "active";

            List<AccountAndStatusDTO> expected = new ArrayList<>();
            expected.add(firstAccountAndStatusDTO);

            List<AccountAndStatusDTO> result = CreateAccountAndStatusListAssembler.createAccountAndStatusList(accountList);

            assertEquals(expected, result);
        }

        @Test
        void createAccountList_ReturnsEmptyList_Equals() {
            List<Account> emptyAccountList = new ArrayList<>();
            List<AccountAndStatusDTO> expected = new ArrayList<>();

            List<AccountAndStatusDTO> result = CreateAccountAndStatusListAssembler.createAccountAndStatusList(emptyAccountList);

            assertEquals(expected, result);
        }

    }