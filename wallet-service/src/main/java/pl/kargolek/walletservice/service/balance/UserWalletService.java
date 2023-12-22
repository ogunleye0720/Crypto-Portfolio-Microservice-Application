package pl.kargolek.walletservice.service.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kargolek.walletservice.dto.TokenDTO;
import pl.kargolek.walletservice.dto.UserBalance;
import pl.kargolek.walletservice.dto.UserWallet;
import pl.kargolek.walletservice.service.QuotaCalculatorService;
import pl.kargolek.walletservice.service.TotalCalculatorService;
import pl.kargolek.walletservice.service.WalletExplorerService;
import pl.kargolek.walletservice.util.CryptoType;
import pl.kargolek.walletservice.util.CryptoUnitConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Karol Kuta-Orlowicz
 */
@Component
public class UserWalletService {

    @Autowired
    private QuotaCalculatorService quotaCalculatorService;

    @Autowired
    private TotalCalculatorService totalCalculatorService;

    @Autowired
    private CryptoUnitConvert convertUnit;

    @Autowired
    private WalletExplorerService walletExplorerService;

    public UserWallet mergeUserWallets(List<UserWallet> userWallets) {
        return userWallets.stream()
                .collect(Collectors.toMap(UserWallet::getName, Function.identity(), (w1, w2) -> {
                            List<UserBalance> mergedBalances = new ArrayList<>(w1.getBalance());
                            mergedBalances.addAll(w2.getBalance());
                            return new UserWallet(w1.getName(), w1.getSymbol(), w1.getTotal(), mergedBalances);
                        }
                )).values().stream().reduce((w1, w2) -> new UserWallet(w1.getName(), w1.getSymbol(), w1.getTotal(),
                        Stream.concat(w1.getBalance().stream(), w2.getBalance().stream()).toList())
                ).orElse(new UserWallet());
    }

    public UserWallet calculateTotal(UserWallet userWallet) {
        var userTotalBalance = this.totalCalculatorService.calcQuantityBalance(userWallet);
        return userWallet.setTotal(userTotalBalance);
    }

    public UserBalance convertUnit(UserBalance userBalance, CryptoType cryptoType) {
        var convertedQuantity = this.convertUnit.convert(userBalance.getQuantity().toPlainString(), cryptoType);
        return userBalance.setQuantity(convertedQuantity);
    }

    public UserBalance calculateUserBalance(UserBalance userBalance, TokenDTO tokenDTO) {
        var quantity = userBalance.getQuantity();
        var priceDTO = tokenDTO.getPrice();

        var balance = quotaCalculatorService.calcBalanceFromPrice(quantity, priceDTO.getPriceCurrent());

        var balance1h = quotaCalculatorService.calcBalancePercentChange(balance, priceDTO.getPercentChange1h());
        var balance24h = quotaCalculatorService.calcBalancePercentChange(balance, priceDTO.getPercentChange24h());
        var balance7d = quotaCalculatorService.calcBalancePercentChange(balance, priceDTO.getPercentChange7d());
        var balance30d = quotaCalculatorService.calcBalancePercentChange(balance, priceDTO.getPercentChange30d());
        var balance60d = quotaCalculatorService.calcBalancePercentChange(balance, priceDTO.getPercentChange60d());
        var balance90d = quotaCalculatorService.calcBalancePercentChange(balance, priceDTO.getPercentChange90d());

        return userBalance
                .setBalance(balance)
                .setBalance1h(balance1h)
                .setBalance24h(balance24h)
                .setBalance7d(balance7d)
                .setBalance30d(balance30d)
                .setBalance60d(balance60d)
                .setBalance90d(balance90d);
    }

    public UserBalance setWalletAddressExplorer(UserBalance userBalance, CryptoType cryptoType){
        var walletAddress = userBalance.getWalletAddress();
        return userBalance.setWalletExplorer(this.walletExplorerService.getWalletExplorerAddress(walletAddress, cryptoType));
    }

}
