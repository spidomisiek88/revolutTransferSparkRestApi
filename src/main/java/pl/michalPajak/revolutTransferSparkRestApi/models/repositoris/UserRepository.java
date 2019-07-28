package pl.michalPajak.revolutTransferSparkRestApi.models.repositoris;

import lombok.Data;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.UserEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class UserRepository {

    public static Map<Integer, UserEntity> users = new HashMap<>();
    private static final AtomicInteger idReservoir = new AtomicInteger(0);


    public Iterable<UserEntity> findAll() {
        return users.values();
    }

    public Optional<UserEntity> findUserById(int userId) {
        return Optional.of(users.get(userId));
    }

    public void softDeleteUserById(int userId) {
        users.get(userId).setIsDelete(1);
    }
}
