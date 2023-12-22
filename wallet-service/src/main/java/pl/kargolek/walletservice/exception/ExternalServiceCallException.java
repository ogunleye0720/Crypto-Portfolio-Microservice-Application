package pl.kargolek.walletservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ExternalServiceCallException extends RuntimeException {
    public ExternalServiceCallException(String message) {
        super(message);
    }
}
