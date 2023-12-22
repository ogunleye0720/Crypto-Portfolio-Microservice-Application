package pl.kargolek.walletservice.validation.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.web3j.crypto.WalletUtils;
import pl.kargolek.walletservice.validation.WalletAddressValidator;

/**
 * @author Karol Kuta-Orlowicz
 */
@Component
@Qualifier("ethereumWalletAddressValidator")
public class EthereumWalletAddressValidator implements WalletAddressValidator {
    @Override
    public boolean isValidAddress(String walletAddress) {
        try {
            return WalletUtils.isValidAddress(walletAddress);
        } catch (Exception e) {
            return false;
        }
    }
}
