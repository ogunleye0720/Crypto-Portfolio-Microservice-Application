package pl.kargolek.walletservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoSuchCryptoPriceDataException extends RuntimeException{
    public NoSuchCryptoPriceDataException(String cryptoName) {
        super(String.format("Unable to get price for crypto: %s", cryptoName));
    }
    public NoSuchCryptoPriceDataException() {
        super("Unable to get price for crypto");
    }
}
