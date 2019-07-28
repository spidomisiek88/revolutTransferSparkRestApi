package pl.michalPajak.revolutTransferSparkRestApi.models.Services;

import lombok.Data;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.UserEntity;
import pl.michalPajak.revolutTransferSparkRestApi.models.repositoris.UserRepository;

import java.util.Optional;

@Data
public class UserService {

    UserRepository userRepository;

    TransferSession userSession;

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    public boolean deleteUserById(int userId){

        Optional<UserEntity> optionalUserEntity = getUserById(userId);
        if (optionalUserEntity.isPresent()) {
            userRepository.softDeleteUserById(userId);
            return getUserById(userId).get().isDelete();
        }

        return false;
    }
}
