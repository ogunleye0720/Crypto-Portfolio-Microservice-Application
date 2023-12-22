package pl.kargolek.cryptopriceservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Karol Kuta-Orlowicz
 */

public class MarketApiClientException extends RuntimeException {

    private final String serverMessage;

    private final HttpStatus httpStatus;

    public MarketApiClientException(HttpStatus httpStatus, String clientMessage, String serverMessage) {
        super(String.format("Status code: %d, clientMessage: %s, serverMessage: %s",
                httpStatus.value(),
                clientMessage,
                serverMessage));
        this.httpStatus = httpStatus;
        this.serverMessage = serverMessage;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
