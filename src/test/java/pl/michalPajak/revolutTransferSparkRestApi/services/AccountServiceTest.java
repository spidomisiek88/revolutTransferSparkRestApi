package pl.michalPajak.revolutTransferSparkRestApi.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.michalPajak.revolutTransferSparkRestApi.models.Services.AccountService;
import pl.michalPajak.revolutTransferSparkRestApi.models.Services.TransferSession;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.AccountEntity;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.UserEntity;
import pl.michalPajak.revolutTransferSparkRestApi.models.repositoris.AccountRepository;

import static org.mockito.ArgumentMatchers.any;

public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    TransferSession transferSession;
    @InjectMocks
    AccountService accountService;
    UserEntity testUser;
    AccountEntity firstTestAccount;
    AccountEntity secondTestAccount;
    TransferSession testTransferSession;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        testUser = new UserEntity();
        testUser.setId(0);
        testUser.setName("testUser");
        testUser.setIsDelete(0);

        firstTestAccount = new AccountEntity();
        firstTestAccount.setId(0);
        firstTestAccount.setUserId(testUser.getId());
        firstTestAccount.setIsDelete(0);
        firstTestAccount.setAccountBalance(200);

        secondTestAccount = new AccountEntity();
        secondTestAccount.setId(0);
        secondTestAccount.setUserId(testUser.getId());
        secondTestAccount.setIsDelete(0);
        secondTestAccount.setAccountBalance(50);

        testTransferSession = new TransferSession();
        testTransferSession.setSendingAccount(firstTestAccount);
        testTransferSession.setReceivingAccount(secondTestAccount);
        testTransferSession.setTransferAmount(0);
        testTransferSession.setTransferFinished(true);
    }

    @Test
    public void shouldUpdateAccountBalance() {
        double expectedAccountBalance = 100;


        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertEquals(expectedAccountBalance, accountService.updateAccountBalance(firstTestAccount, expectedAccountBalance).getAccountBalance());
    }

    @Test
    public void shouldReduceAccountBalance() {
        testTransferSession.setTransferAmount(100);

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertTrue(accountService.reductionAccountBalances(testTransferSession));
    }

    @Test
    public void shouldNotReduceAccountBalance() {
        testTransferSession.setTransferAmount(300);

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertFalse(accountService.reductionAccountBalances(testTransferSession));
    }

    @Test
    public void shouldRaiseAccountBalance() {
        double expectedRaisedAccountBalance = 150;
        testTransferSession.setTransferAmount(100);

        Mockito.when(accountRepository.save(any(AccountEntity.class))).thenReturn(firstTestAccount);

        Assertions.assertEquals(expectedRaisedAccountBalance,
                accountService.raisingAccountBalance(testTransferSession).getAccountBalance());
    }

    @Test
    public void shouldTransferFundsBetweenAccounts() {
        testTransferSession.setTransferAmount(100);

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertTrue(accountService.transferFundsBetweenAccounts(testTransferSession));
    }

    @Test
    public void shouldNotTransferFundsBetweenAccounts() {
        testTransferSession.setTransferAmount(300);

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertFalse(accountService.transferFundsBetweenAccounts(testTransferSession));
    }

    @Test
    public void shouldNotTransferFundsBetweenAccountsBecausetransferIsNotFinished() {
        testTransferSession.setTransferAmount(100);
        testTransferSession.setTransferFinished(false);

        Mockito.when(accountRepository.save(firstTestAccount)).thenReturn(firstTestAccount);
        Mockito.when(accountRepository.save(secondTestAccount)).thenReturn(secondTestAccount);

        Assertions.assertFalse(accountService.transferFundsBetweenAccounts(testTransferSession));
    }
}
