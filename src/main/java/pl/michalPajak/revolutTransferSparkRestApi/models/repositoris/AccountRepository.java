package pl.michalPajak.revolutTransferSparkRestApi.models.repositoris;

import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.AccountEntity;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.UserEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountRepository {

    private static Map<Integer, AccountEntity> accounts = new HashMap<>();
    private static final AtomicInteger idReservoir = new AtomicInteger(0);

    public AccountRepository() {

        completionExamblesAccounts();
    }

    public Iterable<AccountEntity> findAll() {
        return accounts.values();
    }


    public Optional<AccountEntity> findAccountById(int accountId) {
        return Optional.of(accounts.get(accountId));
    }

    public AccountEntity save(AccountEntity accountEntity) {
        accounts.put(accountEntity.getId(), accountEntity);
        return accountEntity;
    }

    public void softDeleteAccountById(int accountId) {
        accounts.get(accountId).setIsDelete(1);
    }

    private void completionExamblesAccounts() {

        AccountEntity firstAccountEntity = new AccountEntity();
        firstAccountEntity.setId(idReservoir.incrementAndGet());
        firstAccountEntity.setIsDelete(0);
        firstAccountEntity.setAccountBalance(100);
        firstAccountEntity.setUserId(1);
        accounts.put(firstAccountEntity.getId(), firstAccountEntity);

        AccountEntity secondAccountEntity = new AccountEntity();
        secondAccountEntity.setId(idReservoir.incrementAndGet());
        secondAccountEntity.setIsDelete(0);
        secondAccountEntity.setAccountBalance(200);
        secondAccountEntity.setUserId(2);
        accounts.put(secondAccountEntity.getId(), secondAccountEntity);

        AccountEntity thirdAccountEntity = new AccountEntity();
        thirdAccountEntity.setId(idReservoir.incrementAndGet());
        thirdAccountEntity.setIsDelete(0);
        thirdAccountEntity.setAccountBalance(300);
        thirdAccountEntity.setUserId(3);
        accounts.put(thirdAccountEntity.getId(), thirdAccountEntity);
    }
}
