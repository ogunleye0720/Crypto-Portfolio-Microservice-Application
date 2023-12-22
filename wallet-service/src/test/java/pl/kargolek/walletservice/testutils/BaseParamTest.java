package pl.kargolek.walletservice.testutils;

import org.junit.jupiter.api.extension.ExtendWith;
import pl.kargolek.walletservice.testutils.extension.*;

/**
 * @author Karol Kuta-Orlowicz
 */
@ExtendWith(ExtDataUserWalletResolver.class)
@ExtendWith(ExtCryptoPriceResponseResolver.class)
@ExtendWith(ExtCryptocurrencyResolver.class)
@ExtendWith(ExtEthereumWalletsResolver.class)
@ExtendWith(ExtEtherscanResponseResolver.class)
public abstract class BaseParamTest {}
