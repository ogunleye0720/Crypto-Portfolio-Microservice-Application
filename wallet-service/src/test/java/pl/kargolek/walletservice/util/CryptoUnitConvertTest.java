package pl.kargolek.walletservice.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kargolek.walletservice.exception.NoSuchCryptocurrencyException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Karol Kuta-Orlowicz
 */
@ExtendWith(MockitoExtension.class)
class CryptoUnitConvertTest {

    private final CryptoUnitConvert underTest = new CryptoUnitConvert();
    private final BigDecimal wei = new BigDecimal("1");
    private final BigDecimal oneWeiEtherResult = new BigDecimal("0.000000000000000001");

    @Test
    void whenConvertEthWeiToEther_thenConvertUnitReturnCorrectValue() {
        var result = underTest.convert(wei.toPlainString(), CryptoType.ETHEREUM);
        assertThat(result).isEqualTo(oneWeiEtherResult);
    }

    @Test
    void whenConvertUnknownCryptoType_thenThrowCustomExc() {
        assertThatThrownBy(() -> underTest.convert(wei.toPlainString(), CryptoType.UNKNOWN))
                .isInstanceOf(NoSuchCryptocurrencyException.class)
                .hasMessageContaining("Unable to make conversion for cryptocurrency for given crypto type: Unknown");
    }
}