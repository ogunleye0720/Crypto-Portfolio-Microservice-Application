package pl.kargolek.walletservice.util;

import org.springframework.stereotype.Component;
import org.web3j.utils.Convert;
import pl.kargolek.walletservice.exception.NoSuchCryptocurrencyException;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */

@Component
public class CryptoUnitConvert {

    public BigDecimal convert(String value, CryptoType cryptoType) {
        if (cryptoType == CryptoType.ETHEREUM || cryptoType == CryptoType.POLYGON || cryptoType == CryptoType.AVALANCHE) {
            return convertWeiToEther(value);
        }
        throw new NoSuchCryptocurrencyException("Unable to make conversion for cryptocurrency for given crypto type: "
                + cryptoType.getName());
    }

    private BigDecimal convertWeiToEther(String value) {
        return Convert.fromWei(value, Convert.Unit.ETHER);
    }

}
