package pl.kargolek.walletservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.dto.UserTotalBalance;
import pl.kargolek.walletservice.testutils.BaseParamTest;
import pl.kargolek.walletservice.testutils.fixture.DataUserWallet;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("UnitTest")
class TotalCalculatorServiceTest extends BaseParamTest {

    private TotalCalculatorService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TotalCalculatorService();
    }

    @Test
    void whenQuantityPositiveBalancePositive_thenReturnCorrectValueCount(DataUserWallet data){
        var userWallet = data.getUserWalletOneEth();

        var expected = underTest.calcQuantityBalance(userWallet);

        assertThat(expected).extracting(
                UserTotalBalance::getTotalQuantity,
                UserTotalBalance::getTotalBalance,
                UserTotalBalance::getTotalBalance1h,
                UserTotalBalance::getTotalBalance24h,
                UserTotalBalance::getTotalBalance7d
        ).containsExactly(
                new BigDecimal("33.333333333"),
                new BigDecimal("6600.66"),
                new BigDecimal("6800.84"),
                new BigDecimal("7001.04"),
                new BigDecimal("7201.24")
        );
    }

    @Test
    void whenQuantityZeroBalanceZero_thenReturnCorrectValueZero(DataUserWallet data){
        var userWallet = data.getUserWalletBalanceZeroEth();

        var expected = underTest.calcQuantityBalance(userWallet);

        assertThat(expected).extracting(
                UserTotalBalance::getTotalQuantity,
                UserTotalBalance::getTotalBalance,
                UserTotalBalance::getTotalBalance1h,
                UserTotalBalance::getTotalBalance24h,
                UserTotalBalance::getTotalBalance7d
        ).containsExactly(
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("0")
        );
    }

    @Test
    void whenQuantityNullBalanceNull_thenReturnCorrectValueZero(DataUserWallet data){
        var userWallet = data.getUserWalletBalanceNullEth();

        var expected = underTest.calcQuantityBalance(userWallet);

        assertThat(expected).extracting(
                UserTotalBalance::getTotalQuantity,
                UserTotalBalance::getTotalBalance,
                UserTotalBalance::getTotalBalance1h,
                UserTotalBalance::getTotalBalance24h,
                UserTotalBalance::getTotalBalance7d
        ).containsExactly(
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("0")
        );
    }
}