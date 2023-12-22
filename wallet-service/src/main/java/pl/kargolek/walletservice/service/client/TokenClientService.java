package pl.kargolek.walletservice.service.client;

import pl.kargolek.walletservice.dto.WalletMultiBalance;

/**
 * @author Karol Kuta-Orlowicz
 */
public interface TokenClientService {

    WalletMultiBalance getWalletMultiBalance(String wallets);

}
