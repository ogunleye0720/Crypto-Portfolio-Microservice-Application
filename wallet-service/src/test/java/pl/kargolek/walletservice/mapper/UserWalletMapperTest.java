package pl.kargolek.walletservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.dto.*;
import pl.kargolek.walletservice.testutils.BaseParamTest;
import pl.kargolek.walletservice.testutils.fixture.DataEthereumWallets;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("UnitTest")
class UserWalletMapperTest extends BaseParamTest {

    private final UserWalletMapper underTest = UserWalletMapper.INSTANCE;
    private WalletMultiBalance walletMultiBalance;
    private WalletBalance walletBalance1;
    private WalletBalance walletBalance2;

    private UserWallet userWallet;

    private TokenDTO tokenDTO;

    @BeforeEach
    void setUp(DataEthereumWallets data) {
        walletBalance1 = new WalletBalance()
                .setAccount(data.WALLETS_1_VALID)
                .setQuantity(new BigDecimal("10000"));

        walletBalance2 = new WalletBalance()
                .setAccount(data.WALLETS_ANOTHER_1_VALID)
                .setQuantity(new BigDecimal("20000"));

        walletMultiBalance = new WalletMultiBalance()
                .setStatus("1")
                .setMessage("OK")
                .setResult(List.of(walletBalance1, walletBalance2));

        tokenDTO = new TokenDTO()
                .setName("Ethereum")
                .setSymbol("ETH");

        userWallet = new UserWallet()
                .setName(null)
                .setSymbol(null);
    }

    @Test
    void whenWalletMultiBalance_thenReturnToUserWallet() {
        var expected = underTest.toUserWallet(walletMultiBalance);

        assertThat(expected)
                .extracting(UserWallet::getBalance)
                .isNotNull();

        assertThat(expected.getBalance())
                .extracting(UserBalance::getWalletAddress, UserBalance::getQuantity)
                .contains(tuple(walletBalance1.getAccount(), walletBalance1.getQuantity()))
                .contains(tuple(walletBalance2.getAccount(), walletBalance2.getQuantity()));
    }

    @Test
    void whenUpdateByTokenDTO_thenReturnUserWallet() {
        var expected = underTest.updateUserWallet(userWallet, tokenDTO);

        assertThat(expected)
                .extracting(UserWallet::getName, UserWallet::getSymbol)
                .contains(tokenDTO.getName(), tokenDTO.getSymbol());

        assertThat(expected).isEqualTo(userWallet);
    }
}