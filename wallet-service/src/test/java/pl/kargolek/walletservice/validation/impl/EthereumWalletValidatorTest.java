package pl.kargolek.walletservice.validation.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.testutils.fixture.DataWalletAddressInvalid;
import pl.kargolek.walletservice.testutils.fixture.DataWalletsAddressValid;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("UnitTest")
class EthereumWalletValidatorTest {

    private EthereumWalletAddressValidator underTest;

    @BeforeEach
    public void setUp() {
        underTest = new EthereumWalletAddressValidator();
    }

    @Test
    public void whenAddressIsValid_thenReturnTrue() {
        var expected = underTest.isValidAddress(DataWalletsAddressValid.WALLET_1.getAddress());
        assertThat(expected).isTrue();
    }

    @Test
    public void whenAddressIsInvalid_thenReturnFalse() {
        var expected = underTest.isValidAddress(DataWalletAddressInvalid.WALLET_1.getAddress());
        assertThat(expected).isFalse();
    }

}