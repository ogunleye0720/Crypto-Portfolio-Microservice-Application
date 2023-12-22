package pl.kargolek.walletservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Karol Kuta-Orlowicz
 */
public class WebClientStatusException extends RuntimeException {
    public WebClientStatusException(HttpStatus httpStatus) {
        super(String.format("Unexpected status code occurred after call request. Status code: %d", httpStatus.value()));
    }
}
