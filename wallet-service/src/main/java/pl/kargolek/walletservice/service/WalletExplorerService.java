package pl.kargolek.walletservice.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.kargolek.walletservice.util.CryptoType;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service
@NoArgsConstructor
@AllArgsConstructor
public class WalletExplorerService {

    @Value("${explorer.wallet.etherscan}")
    private String etherscanWalletExplorerURL;

    @Value("${explorer.wallet.polygonscan}")
    private String polygonscanWalletExplorerURL;

    @Value("${explorer.wallet.snowtrace}")
    private String avalanchescanWalletExplorerURL;

    public String getWalletExplorerAddress(String walletAddress, CryptoType cryptoType){
        return switch (cryptoType) {
            case ETHEREUM -> etherscanWalletExplorerURL.concat(walletAddress);
            case POLYGON -> polygonscanWalletExplorerURL.concat(walletAddress);
            case AVALANCHE -> avalanchescanWalletExplorerURL.concat(walletAddress);
            default -> "unknown";
        };
    }
}
