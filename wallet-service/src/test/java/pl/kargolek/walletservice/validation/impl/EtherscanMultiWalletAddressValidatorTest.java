package pl.kargolek.walletservice.validation.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.exception.InvalidAddressException;
import pl.kargolek.walletservice.testutils.BaseParamTest;
import pl.kargolek.walletservice.testutils.fixture.DataEthereumWallets;
import pl.kargolek.walletservice.testutils.fixture.DataWalletAddressInvalid;
import pl.kargolek.walletservice.testutils.fixture.DataWalletsAddressValid;
import pl.kargolek.walletservice.validation.MultiWalletAddressValidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Karol Kuta-Orlowicz
 */

@Tag("UnitTest")
class EtherscanMultiWalletAddressValidatorTest extends BaseParamTest {

    private MultiWalletAddressValidator underTest;

    @BeforeEach
    void setUp() {
        var ethWalletValidator = new EthereumWalletAddressValidator();
        underTest = new EtherscanMultiWalletAddressValidator(ethWalletValidator, "120");
    }

    @Test
    public void when20WalletsValid_thenReturnTrue(DataEthereumWallets data) {
        assertThat(underTest.isValidAddresses(data.WALLETS_20_ALL_VALID)).isTrue();
    }

    @Test
    public void when19WalletsValid_thenReturnTrue(DataEthereumWallets data) {
        assertThat(underTest.isValidAddresses(data.WALLETS_19_ALL_VALID)).isTrue();
    }

    @Test
    public void when121WalletsValid_thenThrowInvalidAddressException(DataEthereumWallets data) {
        assertThatThrownBy(() -> underTest.isValidAddresses(data.WALLETS_21_ALL_VALID + "," +
                data.WALLETS_21_ALL_VALID + "," +
                data.WALLETS_21_ALL_VALID + "," +
                data.WALLETS_21_ALL_VALID + "," +
                data.WALLETS_21_ALL_VALID + "," +
                data.WALLETS_21_ALL_VALID
        )).isInstanceOf(InvalidAddressException.class);
    }

    @Test
    public void when20WalletsValidOneInvalid_thenThrowInvalidAddressException(DataEthereumWallets data) {
        assertThatThrownBy(() -> underTest.isValidAddresses(data.WALLETS_20_ONE_INVALID))
                .isInstanceOf(InvalidAddressException.class);
    }

    @Test
    public void when1WalletsInvalid_thenThrowInvalidAddressException() {
        assertThatThrownBy(() -> underTest.isValidAddresses(DataWalletAddressInvalid.WALLET_1.getAddress()))
                .isInstanceOf(InvalidAddressException.class);
    }

    @Test
    public void whenWalletsEmpty_thenThrowInvalidAddressException() {
        assertThatThrownBy(() -> underTest.isValidAddresses(""))
                .isInstanceOf(InvalidAddressException.class);
    }

    @Test
    public void whenWalletsNULL_thenThrowInvalidAddressException() {
        assertThatThrownBy(() -> underTest.isValidAddresses(null))
                .isInstanceOf(InvalidAddressException.class);
    }

    @Test
    public void whenWalletsWhiteSpace_thenThrowInvalidAddressException() {
        var multiWalletsWhiteSpace = DataWalletsAddressValid.WALLET_1 + ", " + DataWalletsAddressValid.WALLET_2;
        assertThatThrownBy(() -> underTest.isValidAddresses(multiWalletsWhiteSpace))
                .isInstanceOf(InvalidAddressException.class);
    }

    @Test
    public void whenWalletsInvalidCharacter_thenThrowInvalidAddressException() {
        var multiWalletsWhiteSpace = DataWalletsAddressValid.WALLET_1 + "!" + DataWalletsAddressValid.WALLET_2;
        assertThatThrownBy(() -> underTest.isValidAddresses(multiWalletsWhiteSpace))
                .isInstanceOf(InvalidAddressException.class);
    }
}