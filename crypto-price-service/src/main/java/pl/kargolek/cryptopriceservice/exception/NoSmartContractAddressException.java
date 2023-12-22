package pl.kargolek.cryptopriceservice.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoSmartContractAddressException extends RuntimeException {
    public NoSmartContractAddressException(String contractAddress) {
        super(String.format("Provided contract address is not exist, contractAddress: %s", contractAddress));
    }
}
