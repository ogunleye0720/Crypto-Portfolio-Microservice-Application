package pl.kargolek.data.constant;

import pl.kargolek.data.model.Wallet;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */
public class TestnetWallet {
    public static final Wallet ETHEREUM_WALLET_1 = Wallet.builder()
            .address("0x8d81156294aBC787e5cBcc16774799Aeba574439")
            .amount(new BigDecimal("0.23519701419126102"))
            .build();

    public static final Wallet POLYGON_WALLET_1 = Wallet.builder()
            .address("0x8d81156294aBC787e5cBcc16774799Aeba574439")
            .amount(new BigDecimal("0.4"))
            .build();

    public static final Wallet AVALANCHE_WALLET_1 = Wallet.builder()
            .address("0x8d81156294aBC787e5cBcc16774799Aeba574439")
            .amount(new BigDecimal("1.5"))
            .build();

    public static final Wallet ETHEREUM_WALLET_2_EMPTY = Wallet.builder()
            .address("0x0E510578889ce76db52686625E2E12D35D0b092e")
            .amount(new BigDecimal("0.0"))
            .build();

    public static final Wallet POLYGON_WALLET_2_EMPTY = Wallet.builder()
            .address("0x0E510578889ce76db52686625E2E12D35D0b092e")
            .amount(new BigDecimal("0.0"))
            .build();

    public static final Wallet AVALANCHE_WALLET_2_EMPTY = Wallet.builder()
            .address("0x0E510578889ce76db52686625E2E12D35D0b092e")
            .amount(new BigDecimal("0.0"))
            .build();

}
