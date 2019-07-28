package pl.michalPajak.revolutTransferSparkRestApi.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;
import org.eclipse.jetty.http.HttpStatus;

@Data
public class StandardResponse {

    private HttpStatus.Code status;
    private String message;
    private JsonElement data;

    public StandardResponse(HttpStatus.Code status) {

        this.status = status;
        this.message = status.getMessage();
        this.data = new JsonObject().get("");
    }
    public StandardResponse(HttpStatus.Code status, String message) {
        this.status = status;
        this.message = message;
        this.data = new JsonObject().get("");
    }
    public StandardResponse(HttpStatus.Code status, JsonElement data) {
        this.status = status;
        this.message = status.getMessage();
        this.data = data;
    }
}
