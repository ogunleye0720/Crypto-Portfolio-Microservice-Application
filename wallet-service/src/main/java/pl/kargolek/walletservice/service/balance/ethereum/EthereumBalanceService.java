package pl.kargolek.walletservice.service.balance.ethereum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.kargolek.walletservice.dto.UserWallet;
import pl.kargolek.walletservice.service.balance.BalanceService;
import pl.kargolek.walletservice.service.balance.EtherscanBalance;
import pl.kargolek.walletservice.service.client.impl.EtherscanTokenClientService;
import pl.kargolek.walletservice.util.CryptoType;
import pl.kargolek.walletservice.validation.MultiWalletAddressValidator;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service
public class EthereumBalanceService extends EtherscanBalance implements BalanceService {

    @Autowired
    private MultiWalletAddressValidator walletsValidator;

    @Value("${api.etherscan.maxWalletsPerRequest}")
    private String maxWalletsPerRequest;


    public EthereumBalanceService(@Qualifier("etherscanTokenClientService") EtherscanTokenClientService tokenClientService) {
        super(tokenClientService);
    }

    @Override
    public UserWallet getMultiBalance(String wallets){
        this.walletsValidator.isValidAddresses(wallets);
        return this.calculateMultiWalletBalance(wallets, CryptoType.ETHEREUM, Integer.parseInt(maxWalletsPerRequest));
    }

}
