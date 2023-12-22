package pl.kargolek.walletservice.service;

import org.springframework.stereotype.Service;
import pl.kargolek.walletservice.exception.NoSuchCryptoPriceDataException;
import pl.kargolek.walletservice.exception.NoSuchWalletDataException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service
public class QuotaCalculatorService {

    public BigDecimal calcBalanceFromPrice(BigDecimal tokenQuantity, BigDecimal price) {
        var priceValid = Optional.ofNullable(price).orElseThrow(NoSuchCryptoPriceDataException::new);
        var tokenValid = Optional.ofNullable(tokenQuantity).orElseThrow(NoSuchWalletDataException::new);

        var balance = tokenValid.multiply(priceValid);

        return balance.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calcBalancePercentChange(BigDecimal balance, BigDecimal percent) {
        var percentValid = Optional.ofNullable(percent).orElseThrow(NoSuchCryptoPriceDataException::new);
        var balanceValid = Optional.ofNullable(balance).orElseThrow(NoSuchWalletDataException::new);

        var diff = balanceValid.multiply(percentValid.divide(new BigDecimal("100")));
        var balance_change = balanceValid.subtract(diff);

        return balance_change.setScale(2, RoundingMode.HALF_EVEN);
    }

}
