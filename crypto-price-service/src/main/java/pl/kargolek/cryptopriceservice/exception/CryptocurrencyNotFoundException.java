package pl.kargolek.cryptopriceservice.exception;

public class CryptocurrencyNotFoundException extends RuntimeException {
    public CryptocurrencyNotFoundException(Long id) {
        super(String.format("Unable to find cryptocurrency with id: %d", id));
    }

    public CryptocurrencyNotFoundException(String name) {
        super(String.format("Unable to find cryptocurrency with name: %s", name));
    }
}
