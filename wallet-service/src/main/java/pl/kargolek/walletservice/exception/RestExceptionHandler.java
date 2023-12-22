package pl.kargolek.walletservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.kargolek.walletservice.dto.JsonApiErrorDTO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Karol Kuta-Orlowicz
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(ExternalServiceCallException.class)
    public ResponseEntity<?> handleException(ExternalServiceCallException ex) {
        var jsonApiError = JsonApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(ex.getMessage())
                .build();
        log.info(String.format("Status: %s Message: %s",
                jsonApiError.getStatus(),
                jsonApiError.getMessage()));
        return new ResponseEntity<>(jsonApiError, jsonApiError.getStatus());
    }

    @ExceptionHandler(ResponseFieldException.class)
    public ResponseEntity<?> handleException(ResponseFieldException ex) {
        var jsonApiError = JsonApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(ex.getMessage())
                .build();
        log.info(String.format("Status: %s Message: %s",
                jsonApiError.getStatus(),
                jsonApiError.getMessage()));
        return new ResponseEntity<>(jsonApiError, jsonApiError.getStatus());
    }

    @ExceptionHandler(NoSuchCryptoPriceDataException.class)
    public ResponseEntity<?> handleException(NoSuchCryptoPriceDataException ex) {
        var jsonApiError = JsonApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(ex.getMessage())
                .build();
        log.info(String.format("Status: %s Message: %s",
                jsonApiError.getStatus(),
                jsonApiError.getMessage()));
        return new ResponseEntity<>(jsonApiError, jsonApiError.getStatus());
    }

    @ExceptionHandler(NoSuchCryptocurrencyException.class)
    public ResponseEntity<?> handleException(NoSuchCryptocurrencyException ex) {
        var jsonApiError = JsonApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(ex.getMessage())
                .build();
        log.info(String.format("Status: %s Message: %s",
                jsonApiError.getStatus(),
                jsonApiError.getMessage()));
        return new ResponseEntity<>(jsonApiError, jsonApiError.getStatus());
    }

    @ExceptionHandler(WebClientStatusException.class)
    public ResponseEntity<?> handleException(WebClientStatusException ex) {
        var jsonApiError = JsonApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(ex.getMessage())
                .build();
        log.info(String.format("Status: %s Message: %s",
                jsonApiError.getStatus(),
                jsonApiError.getMessage()));
        return new ResponseEntity<>(jsonApiError, jsonApiError.getStatus());
    }

    @ExceptionHandler(NoSuchWalletDataException.class)
    public ResponseEntity<?> handleException(NoSuchWalletDataException ex) {
        var jsonApiError = JsonApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(ex.getMessage())
                .build();
        log.info(String.format("Status: %s Message: %s",
                jsonApiError.getStatus(),
                jsonApiError.getMessage()));
        return new ResponseEntity<>(jsonApiError, jsonApiError.getStatus());
    }

    @ExceptionHandler(InvalidAddressException.class)
    public ResponseEntity<?> handleException(InvalidAddressException ex) {
        var jsonApiError = JsonApiErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(ex.getMessage())
                .build();
        log.info(String.format("Status: %s Message: %s",
                jsonApiError.getStatus(),
                jsonApiError.getMessage()));
        return new ResponseEntity<>(jsonApiError, jsonApiError.getStatus());
    }
}
