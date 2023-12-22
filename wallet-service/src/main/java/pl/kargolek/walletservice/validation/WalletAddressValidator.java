package pl.kargolek.walletservice.validation;

/**
 * @author Karol Kuta-Orlowicz
 */
public interface WalletAddressValidator {

    boolean isValidAddress(String walletAddress);

}
