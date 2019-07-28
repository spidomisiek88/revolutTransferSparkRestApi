package pl.michalPajak.revolutTransferSparkRestApi.controllers;

import pl.michalPajak.revolutTransferSparkRestApi.models.Services.AccountService;
import pl.michalPajak.revolutTransferSparkRestApi.models.Services.TransferSession;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.AccountEntity;

import java.util.Arrays;
import java.util.Optional;

public class AccountApiController {

    final AccountService accountService;
    TransferSession transferSession;

    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
        this.transferSession = new TransferSession();
    }

    public Iterable<AccountEntity> getUsers() {

        return accountService.getAllAccaunts();
    }

    public Optional<AccountEntity> getAccountById(int accountId) {
        return accountService.getAccountById(accountId);

    }

    public AccountEntity updateAccountBalance(AccountEntity accountEntity, double accountBalance) {

        accountService.updateAccountBalance(accountEntity, accountBalance);

        return accountEntity;
    }

    public Iterable<AccountEntity> transferFundsBetweenAccounts(AccountEntity sendingAccount,
                                                                AccountEntity receivingAccount,
                                                                double transferAmount) {

        transferSession.setSendingAccount(sendingAccount);
        transferSession.setReceivingAccount(receivingAccount);
        transferSession.setTransferAmount(transferAmount);
        transferSession.setTransferFinished(true);

        accountService.transferFundsBetweenAccounts(transferSession);

        return Arrays.asList(sendingAccount, receivingAccount);
    }
}
