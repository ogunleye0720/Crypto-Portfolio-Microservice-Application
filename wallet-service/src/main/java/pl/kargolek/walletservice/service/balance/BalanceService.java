package pl.kargolek.walletservice.service.balance;

import pl.kargolek.walletservice.dto.UserWallet;

/**
 * @author Karol Kuta-Orlowicz
 */
public interface BalanceService {
    UserWallet getMultiBalance(String walletAddresses);
}
