package pl.kargolek.cryptopriceservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoMatchCryptoMapException extends RuntimeException {
    public NoMatchCryptoMapException(String name) {
        super(String.format("Received coin market cap crypto name, no match with provided name:%s", name));
    }
}