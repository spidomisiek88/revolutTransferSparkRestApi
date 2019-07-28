package pl.michalPajak.revolutTransferSparkRestApi;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import pl.michalPajak.revolutTransferSparkRestApi.controllers.AccountApiController;
import pl.michalPajak.revolutTransferSparkRestApi.controllers.StandardResponse;
import pl.michalPajak.revolutTransferSparkRestApi.models.Services.AccountService;
import pl.michalPajak.revolutTransferSparkRestApi.models.entitis.AccountEntity;

import java.util.Optional;

import static spark.Spark.*;

public class RevolutTransferSparkRestApi {

    private static final String REQUEST_MAPPING = "/api";

    public static void main(String[] args) {

        AccountApiController accountApiController = new AccountApiController(new AccountService());

        port(8080);

        get(REQUEST_MAPPING + "/accounts", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(
                    new StandardResponse(HttpStatus.Code.OK, new Gson()
                            .toJsonTree(accountApiController.getUsers())));
        });

        get(REQUEST_MAPPING + "/account/:accountId", (request, response) -> {
            response.type("application/json");

            Optional<AccountEntity> optionalAccountEntity = accountApiController.getAccountById(
                    Integer.valueOf(request.params(":accountId")));

            if (!optionalAccountEntity.isPresent())
                return new Gson().toJson(
                        new StandardResponse(HttpStatus.Code.NOT_FOUND, new Gson()
                                .toJsonTree("")));

            return new Gson().toJson(
                    new StandardResponse(HttpStatus.Code.OK, new Gson()
                            .toJsonTree(optionalAccountEntity.get())));
        });

        put(REQUEST_MAPPING + "/account/update/:accountId/:accountBalance", (request, response) -> {

            response.type("application/json");

            Optional<AccountEntity> optionalAccountEntity = accountApiController.getAccountById(
                    Integer.valueOf(request.params(":accountId")));

            if (!optionalAccountEntity.isPresent())
                return new Gson().toJson(
                        new StandardResponse(HttpStatus.Code.NOT_FOUND, new Gson()
                                .toJsonTree("")));

            return new Gson().toJson(
                    new StandardResponse(HttpStatus.Code.OK, new Gson()
                            .toJsonTree(accountApiController.updateAccountBalance(
                                    optionalAccountEntity.get(), Double.valueOf(request.params(":accountBalance"))))));
        });

        put(REQUEST_MAPPING + "/account/transfer/:sendingAccountId/:receivingAccountId/:transferAmount",
                (request, response) -> {

                    response.type("application/json");

                    Optional<AccountEntity> optionalSendingAccountEntity = accountApiController
                            .getAccountById(Integer.valueOf(request.params(":sendingAccountId")));
                    Optional<AccountEntity> optionalReceivingAccountEntity = accountApiController
                            .getAccountById(Integer.valueOf(request.params(":receivingAccountId")));

                    if (!optionalSendingAccountEntity.isPresent() || !optionalReceivingAccountEntity.isPresent())
                        return new Gson().toJson(
                                new StandardResponse(HttpStatus.Code.NOT_FOUND, new Gson()
                                        .toJsonTree("")));

                    return new Gson().toJson(
                            new StandardResponse(HttpStatus.Code.OK, new Gson()
                                    .toJsonTree(accountApiController
                                            .transferFundsBetweenAccounts(optionalSendingAccountEntity.get()
                                                    ,optionalReceivingAccountEntity.get()
                                                    ,Double.valueOf(request.params(":transferAmount"))))));
        });
    }
}
