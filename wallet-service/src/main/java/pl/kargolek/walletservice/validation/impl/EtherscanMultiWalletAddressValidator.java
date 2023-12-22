package pl.kargolek.walletservice.validation.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.kargolek.walletservice.exception.InvalidAddressException;
import pl.kargolek.walletservice.validation.MultiWalletAddressValidator;
import pl.kargolek.walletservice.validation.WalletAddressValidator;

/**
 * @author Karol Kuta-Orlowicz
 */
@Component
@Qualifier("etherscanMultiWalletAddressValidator")
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class EtherscanMultiWalletAddressValidator implements MultiWalletAddressValidator {

    @Autowired
    @Qualifier("ethereumWalletAddressValidator")
    private WalletAddressValidator walletAddressValidator;

    @Value("${api.etherscan.maxWalletsCheck}")
    private String maxWalletsCheck;

    @Override
    public boolean isValidAddresses(String wallets) {
        log.info("Validating wallets: {}", wallets);
        if (wallets == null || wallets.isEmpty() || wallets.matches("[^a-zA-Z0-9,]+")) {
            throw new InvalidAddressException("ETH", wallets, "wallets are null, empty or not match to the pattern");
        }
        String[] addresses = wallets.split(",");
        if (addresses.length > Integer.parseInt(maxWalletsCheck)) {
            throw new InvalidAddressException("ETH", wallets, "wallets exceed max num 20 for etherscan multi wallet balance");
        }
        for (String address : addresses) {
            if (!walletAddressValidator.isValidAddress(address)) {
                throw new InvalidAddressException("ETH", address, "address is invalid");
            }
        }
        return true;
    }
}
