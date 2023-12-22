package pl.kargolek.walletservice.service.balance.polygon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.kargolek.walletservice.dto.UserWallet;
import pl.kargolek.walletservice.service.balance.BalanceService;
import pl.kargolek.walletservice.service.balance.EtherscanBalance;
import pl.kargolek.walletservice.service.client.impl.PolygonscanTokenClientService;
import pl.kargolek.walletservice.util.CryptoType;
import pl.kargolek.walletservice.validation.MultiWalletAddressValidator;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service
public class PolygonBalanceService extends EtherscanBalance implements BalanceService {

    @Autowired
    private MultiWalletAddressValidator walletsValidator;

    @Value("${api.polygonscan.maxWalletsPerRequest}")
    private String maxWalletsPerRequest;

    @Autowired
    public PolygonBalanceService(@Qualifier("polygonscanTokenClientService") PolygonscanTokenClientService polygonscanTokenClientService) {
        super(polygonscanTokenClientService);
    }

    @Override
    public UserWallet getMultiBalance(String walletAddresses) {
        this.walletsValidator.isValidAddresses(walletAddresses);
        return this.calculateMultiWalletBalance(walletAddresses, CryptoType.POLYGON, Integer.parseInt(maxWalletsPerRequest));
    }
}
