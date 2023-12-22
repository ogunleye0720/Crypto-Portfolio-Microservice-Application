package pl.kargolek.walletservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.dto.UserBalance;
import pl.kargolek.walletservice.dto.WalletBalance;
import pl.kargolek.walletservice.testutils.BaseParamTest;
import pl.kargolek.walletservice.testutils.fixture.DataEthereumWallets;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */

@Tag("UnitTest")
class UserBalanceMapperTest extends BaseParamTest {

    private final UserBalanceMapper underTest = UserBalanceMapper.INSTANCE;
    private WalletBalance walletBalance1;
    private WalletBalance walletBalance2;


    @BeforeEach
    void setUp(DataEthereumWallets data) {
        walletBalance1 = new WalletBalance()
                .setAccount(data.WALLETS_1_VALID)
                .setQuantity(new BigDecimal("10000"));

        walletBalance2 = new WalletBalance()
                .setAccount(data.WALLETS_ANOTHER_1_VALID)
                .setQuantity(new BigDecimal("20000"));
    }

    @Test
    void whenWalletBalanceMap_thenToUserBalanceReturn() {
        var expected = underTest.toUserBalance(walletBalance1);

        assertThat(expected)
                .extracting(UserBalance::getWalletAddress, UserBalance::getQuantity)
                .contains(walletBalance1.getAccount(), walletBalance1.getQuantity());
    }

    @Test
    void whenWalletBalancesMap_thenToUserBalancesReturn() {
        var expected = underTest.toUserBalances(List.of(walletBalance1, walletBalance2));

        assertThat(expected).hasSize(2);
    }
}