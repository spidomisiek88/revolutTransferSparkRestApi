package pl.michalPajak.revolutTransferSparkRestApi.models.entitis;

import lombok.Data;

import java.util.Objects;

@Data
public class AccountEntity {


    private int id;
    private double accountBalance;
    private int userId;
    private int isDelete;

    public boolean isDelete() {
        return isDelete > 0 ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity)) return false;
        AccountEntity that = (AccountEntity) o;
        return getId() == that.getId() &&
                Double.compare(that.getAccountBalance(), getAccountBalance()) == 0 &&
                getUserId() == that.getUserId() &&
                getIsDelete() == that.getIsDelete();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountBalance(), getUserId(), getIsDelete());
    }
}
