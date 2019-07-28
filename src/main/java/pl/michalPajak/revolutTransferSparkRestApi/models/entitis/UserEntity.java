package pl.michalPajak.revolutTransferSparkRestApi.models.entitis;


import lombok.Data;

import java.util.Objects;

@Data
public class UserEntity {

    private int id;
    private String name;
    private int isDelete;

    public boolean isDelete() {
        return isDelete > 0 ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return getId() == that.getId() &&
                getIsDelete() == that.getIsDelete() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getIsDelete());
    }
}
