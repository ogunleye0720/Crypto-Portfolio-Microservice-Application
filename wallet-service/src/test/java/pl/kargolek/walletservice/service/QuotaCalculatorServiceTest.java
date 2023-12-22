package pl.kargolek.walletservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.exception.NoSuchCryptoPriceDataException;
import pl.kargolek.walletservice.exception.NoSuchWalletDataException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("UnitTest")
class QuotaCalculatorServiceTest {

    private QuotaCalculatorService underTest;

    @BeforeEach
    void setup() {
        underTest = new QuotaCalculatorService();
    }

    @Test
    void whenQuantityPricePositive_thenCalcCorrectValue() {
        var quantity = new BigDecimal("100.0");
        var price = new BigDecimal("2000.0");

        var expected = underTest.calcBalanceFromPrice(quantity, price);

        assertThat(expected).isEqualTo(new BigDecimal("200000.00"));
    }

    @Test
    void whenQuantityZeroPricePositive_thenCalcCorrectValue() {
        var quantity = new BigDecimal("0.0");
        var price = new BigDecimal("2000.0");

        var expected = underTest.calcBalanceFromPrice(quantity, price);

        assertThat(expected).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void whenQuantityPositivePriceZero_thenCalcCorrectValue() {
        var quantity = new BigDecimal("10.0");
        var price = new BigDecimal("0.0");

        var expected = underTest.calcBalanceFromPrice(quantity, price);

        assertThat(expected).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void whenQuantityNullPricePositive_thenThrowNoSuchWalletDataExc() {
        BigDecimal quantity = null;
        BigDecimal price = new BigDecimal("10.0");

        assertThatThrownBy(() -> underTest.calcBalanceFromPrice(quantity, price))
                .isInstanceOf(NoSuchWalletDataException.class);
    }

    @Test
    void whenQuantityPositivePriceNull_thenThrowNoSuchCryptoPriceExc() {
        var quantity = new BigDecimal("10.0");
        BigDecimal price = null;

        assertThatThrownBy(() -> underTest.calcBalanceFromPrice(quantity, price))
                .isInstanceOf(NoSuchCryptoPriceDataException.class);
    }

    @Test
    void whenBalancePercentPositive_thenReturnCorrectCalcVal() {
        var balance = new BigDecimal("1000.00");
        var percent = new BigDecimal("10.00");

        var expected = underTest.calcBalancePercentChange(balance, percent);

        assertThat(expected).isEqualTo(new BigDecimal("900.00"));
    }

    @Test
    void whenBalancePositivePercentZero_thenReturnCorrectCalcVal() {
        var balance = new BigDecimal("1000.00");
        var percent = new BigDecimal("0.00");

        var expected = underTest.calcBalancePercentChange(balance, percent);

        assertThat(expected).isEqualTo(new BigDecimal("1000.00"));
    }

    @Test
    void whenBalancePositivePercentNegative_thenReturnCorrectCalcVal() {
        var balance = new BigDecimal("1000.00");
        var percent = new BigDecimal("-10.00");

        var expected = underTest.calcBalancePercentChange(balance, percent);

        assertThat(expected).isEqualTo(new BigDecimal("1100.00"));
    }

    @Test
    void whenBalancePositivePercentNull_thenThrowNoSuchCryptoPriceExc() {
        var balance = new BigDecimal("1000.00");
        BigDecimal percent = null;

        assertThatThrownBy(() -> underTest.calcBalancePercentChange(balance, percent))
                .isInstanceOf(NoSuchCryptoPriceDataException.class);
    }

    @Test
    void whenBalanceZeroPercentPositive_thenReturnCorrectCalcVal() {
        var balance = new BigDecimal("0.00");
        var percent = new BigDecimal("10.00");

        var expected = underTest.calcBalancePercentChange(balance, percent);

        assertThat(expected).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void whenBalanceNegativePercentPositive_thenReturnCorrectCalcVal() {
        var balance = new BigDecimal("-100.00");
        var percent = new BigDecimal("10.00");

        var expected = underTest.calcBalancePercentChange(balance, percent);

        assertThat(expected).isEqualTo(new BigDecimal("-90.00"));
    }

    @Test
    void whenBalanceNegativePercentNegative_thenReturnCorrectCalcVal() {
        var balance = new BigDecimal("-100.00");
        var percent = new BigDecimal("-10.00");

        var expected = underTest.calcBalancePercentChange(balance, percent);

        assertThat(expected).isEqualTo(new BigDecimal("-110.00"));
    }

    @Test
    void whenBalanceNullPercentPositive_thenThrowNoSuchWalletDataExc() {
        BigDecimal balance = null;
        BigDecimal percent = new BigDecimal("1000.00");

        assertThatThrownBy(() -> underTest.calcBalancePercentChange(balance, percent))
                .isInstanceOf(NoSuchWalletDataException.class);
    }

}