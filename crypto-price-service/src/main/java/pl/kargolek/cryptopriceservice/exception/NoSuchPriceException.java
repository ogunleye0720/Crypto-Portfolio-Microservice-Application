package pl.kargolek.cryptopriceservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoSuchPriceException extends RuntimeException {
    public NoSuchPriceException(String ids) {
        super(String.format("Unable to fetch price from coin market api for provided ids: %s", ids));
    }
}