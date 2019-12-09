package de.cloud.fundamentals.distributor.rest;

import de.cloud.fundamentals.distributor.rest.dto.RequestDetails;
import de.cloud.fundamentals.distributor.telegram.UpdateManager;
import de.cloud.fundamentals.distributor.userfeedback.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final I18n USER_FEEDBACK = new I18n();
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private final UpdateManager updateManager;

    @Autowired
    public Controller(UpdateManager updateManager) {
        this.updateManager = updateManager;
        updateManager.setRequestCallback(this::getResponseFor);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public String getStatus() {
        return "Distributor is active.";
    }

    @PostMapping(value = "/api", produces = JSON, consumes = JSON)
    public ResponseEntity<String> receiveRequestDetails(@RequestBody RequestDetails details) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateManager.onUpdateReceived(details));
    }

    private String getResponseFor(String uri, String message) {
        String response;
        try {
            response = HttpClient
                    .newHttpClient()
                    .sendAsync(getPOSTRequest(uri, message), HttpResponse.BodyHandlers.ofString())
                    .thenApply(httpResponse -> HttpStatus.valueOf(httpResponse.statusCode()).equals(HttpStatus.OK)
                            ? httpResponse.body()
                            : USER_FEEDBACK.get("error.invalid-parameters"))
                    .get(30, TimeUnit.SECONDS);

        } catch (Exception e) {
            LOGGER.warn("invalid response of service {}", uri, e);
            response = USER_FEEDBACK.get("error.service.response");
        }
        return response;
    }

    private HttpRequest getPOSTRequest(String uri, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

}
