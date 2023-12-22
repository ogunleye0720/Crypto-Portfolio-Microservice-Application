package pl.kargolek.walletservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoSuchCryptocurrencyException extends RuntimeException {
    public NoSuchCryptocurrencyException(String message) {
        super(message);
    }
}
