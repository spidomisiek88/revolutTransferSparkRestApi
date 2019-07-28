package pl.michalPajak.revolutTransferSparkRestApi.models.Services;

import lombok.Data;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.AccountEntity;

@Data
public class TransferSession {

    private double transferAmount;
    private AccountEntity sendingAccount;
    private AccountEntity receivingAccount;
    private boolean isTransferFinished;
}
