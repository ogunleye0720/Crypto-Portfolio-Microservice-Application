package pl.kargolek.walletservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class InvalidAddressException extends RuntimeException {
    public InvalidAddressException(String crypto, String address, String message) {
        super(String.format("Address is invalid for crypto %s and address %s, message: %s", crypto, address, message));
    }
}
