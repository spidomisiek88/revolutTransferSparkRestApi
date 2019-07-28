package pl.michalPajak.revolutTransferSparkRestApi.models.Services;

import lombok.Data;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.AccountEntity;
import pl.michalPajak.revolutTransferSparkRestApi.models.repositoris.AccountRepository;

import java.util.Optional;

@Data
public class AccountService {

    AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public Iterable<AccountEntity> getAllAccaunts() {
        return accountRepository.findAll();
    }

    public Optional<AccountEntity> getAccountById(int accountId) {
        return accountRepository.findAccountById(accountId);
    }

    public boolean transferFundsBetweenAccounts(TransferSession transferSession) {
        if (!transferSession.isTransferFinished())
            return false;

        transferSession.setTransferFinished(false);

        if (!reductionAccountBalances(transferSession))
            return false;
        raisingAccountBalance(transferSession);

        transferSession.setTransferFinished(true);
        return true;
    }

    public boolean reductionAccountBalances(TransferSession transferSession) {

        if (transferSession.getTransferAmount() > transferSession.getSendingAccount().getAccountBalance())
            return false;

        double reductionedAccountBalances = transferSession.getSendingAccount().getAccountBalance() - transferSession.getTransferAmount();
        updateAccountBalance(transferSession.getSendingAccount(), reductionedAccountBalances);

        return true;
    }

    public AccountEntity raisingAccountBalance(TransferSession transferSession) {

        double raisedAccountBalances = transferSession.getReceivingAccount().getAccountBalance() + transferSession.getTransferAmount();

        return updateAccountBalance(transferSession.getReceivingAccount(), raisedAccountBalances);
    }

    public AccountEntity updateAccountBalance(AccountEntity accountEntity, double accountBalance) {

        accountEntity.setAccountBalance(accountBalance);
        accountRepository.save(accountEntity);

        return accountEntity;
    }

    public boolean deleteAccountById(int accountId){

        Optional<AccountEntity> optionalAccountEntity = getAccountById(accountId);
        if (optionalAccountEntity.isPresent()) {
            accountRepository.softDeleteAccountById(accountId);
            return getAccountById(accountId).get().isDelete();
        }

        return false;
    }
}
