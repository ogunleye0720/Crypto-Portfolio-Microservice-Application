package pl.kargolek.walletservice.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class ResponseFieldException extends RuntimeException {
    public ResponseFieldException(String field, String expected) {
        super(String.format("Verification of field %s failed, expected: %s", field, expected));
        log.warn("Response validation failed");
    }
}
