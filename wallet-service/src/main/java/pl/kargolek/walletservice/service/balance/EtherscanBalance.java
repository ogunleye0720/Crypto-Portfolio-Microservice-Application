package pl.kargolek.walletservice.service.balance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kargolek.walletservice.dto.TokenDTO;
import pl.kargolek.walletservice.dto.UserWallet;
import pl.kargolek.walletservice.mapper.UserWalletMapper;
import pl.kargolek.walletservice.service.client.TokenClientService;
import pl.kargolek.walletservice.service.price.PriceService;
import pl.kargolek.walletservice.util.CryptoType;
import pl.kargolek.walletservice.util.WalletDataResolver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class EtherscanBalance {

    private final TokenClientService tokenClientService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private WalletDataResolver walletDataResolver;

    @Autowired
    private UserWalletService userWalletService;

    private final UserWalletMapper mapper = UserWalletMapper.INSTANCE;

    public EtherscanBalance(TokenClientService tokenClientService) {
        this.tokenClientService = tokenClientService;
    }

    public UserWallet calculateMultiWalletBalance(String wallets, CryptoType cryptoType, Integer maxWalletPerRequest){
        log.info("Getting multi wallet balance calculation. Wallets: {} Crypto: {}", wallets, cryptoType);
        var tokenData = this.priceService.getCryptoPrice(cryptoType);

        var userWallets = this.walletDataResolver.splitWalletsData(wallets, maxWalletPerRequest)
                .parallel()
                .map(walletAddresses -> walletAddresses.stream()
                        .map(tokenClientService::getWalletMultiBalance)
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .map(mapper::toUserWallet)
                .map(userWallet -> this.prepareUserBalances(userWallet, tokenData, cryptoType))
                .map(userWallet -> mapper.updateUserWallet(userWallet, tokenData))
                .toList();

        var userWallet = this.userWalletService.mergeUserWallets(userWallets);

        return this.userWalletService.calculateTotal(userWallet);
    }

    private UserWallet prepareUserBalances(UserWallet userWallet, TokenDTO tokenDTO, CryptoType cryptoType) {
        var userBalances = userWallet.getBalance()
                .stream()
                .map(userBalance -> this.userWalletService.convertUnit(userBalance, cryptoType))
                .map(userBalance -> this.userWalletService.calculateUserBalance(userBalance, tokenDTO))
                .map(userBalance -> this.userWalletService.setWalletAddressExplorer(userBalance, cryptoType))
                .toList();
        return userWallet.setBalance(userBalances);
    }
}
