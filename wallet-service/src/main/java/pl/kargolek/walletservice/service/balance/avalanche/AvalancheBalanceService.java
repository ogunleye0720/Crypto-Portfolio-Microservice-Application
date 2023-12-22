package pl.kargolek.walletservice.service.balance.avalanche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.kargolek.walletservice.dto.UserWallet;
import pl.kargolek.walletservice.service.balance.BalanceService;
import pl.kargolek.walletservice.service.balance.EtherscanBalance;
import pl.kargolek.walletservice.service.client.impl.AvalancheTokenClientService;
import pl.kargolek.walletservice.util.CryptoType;
import pl.kargolek.walletservice.validation.MultiWalletAddressValidator;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service
public class AvalancheBalanceService extends EtherscanBalance implements BalanceService {

    @Autowired
    private MultiWalletAddressValidator walletsValidator;

    @Value("${api.etherscan.maxWalletsPerRequest}")
    private String maxWalletsPerRequest;


    public AvalancheBalanceService(@Qualifier("avalancheTokenClientService") AvalancheTokenClientService tokenClientService) {
        super(tokenClientService);
    }

    @Override
    public UserWallet getMultiBalance(String walletAddresses) {
        System.out.println("Im running");
        this.walletsValidator.isValidAddresses(walletAddresses);
        return this.calculateMultiWalletBalance(walletAddresses, CryptoType.AVALANCHE, Integer.parseInt(maxWalletsPerRequest));
    }
}
