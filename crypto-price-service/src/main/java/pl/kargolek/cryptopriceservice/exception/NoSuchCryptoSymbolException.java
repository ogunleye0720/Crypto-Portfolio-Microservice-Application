package pl.kargolek.cryptopriceservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoSuchCryptoSymbolException extends RuntimeException {

    public NoSuchCryptoSymbolException(String symbol) {
        super(String.format("Unable to fetch crypto map from coin market api for provided symbol: %s", symbol));
    }
}