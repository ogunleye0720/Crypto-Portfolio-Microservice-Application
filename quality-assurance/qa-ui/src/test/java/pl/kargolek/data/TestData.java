package pl.kargolek.data;

import lombok.Getter;
import pl.kargolek.data.constant.TestnetWallet;
import pl.kargolek.data.model.Cryptocurrency;
import pl.kargolek.data.model.Price;
import pl.kargolek.data.model.Wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Karol Kuta-Orlowicz
 */
@Getter
public class TestData {

    private final Cryptocurrency ethereum = Cryptocurrency.builder()
            .id(1L)
            .name("Ethereum")
            .symbol("ETH")
            .coinMarketId(1027L)
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Cryptocurrency polygon = Cryptocurrency.builder()
            .id(2L)
            .name("Polygon")
            .symbol("MATIC")
            .coinMarketId(3890L)
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Cryptocurrency avalanche = Cryptocurrency.builder()
            .id(3L)
            .name("Avalanche")
            .symbol("AVAX")
            .coinMarketId(5805L)
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Price ethereumPrice = Price.builder()
            .id(1L)
            .cryptocurrencyId(1L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("10.0"))
            .percentChange24h(new BigDecimal("10.0"))
            .percentChange7d(new BigDecimal("10.0"))
            .percentChange30d(new BigDecimal("10.0"))
            .percentChange60d(new BigDecimal("10.0"))
            .percentChange90d(new BigDecimal("10.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Price ethereumPricePastNegative = Price.builder()
            .id(1L)
            .cryptocurrencyId(1L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("-10.0"))
            .percentChange24h(new BigDecimal("-10.0"))
            .percentChange7d(new BigDecimal("-10.0"))
            .percentChange30d(new BigDecimal("-10.0"))
            .percentChange60d(new BigDecimal("-10.0"))
            .percentChange90d(new BigDecimal("-10.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Price ethereumPricePastNegativeMinus10Each = Price.builder()
            .id(1L)
            .cryptocurrencyId(1L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("-10.0"))
            .percentChange24h(new BigDecimal("-20.0"))
            .percentChange7d(new BigDecimal("-30.0"))
            .percentChange30d(new BigDecimal("-40.0"))
            .percentChange60d(new BigDecimal("-50.0"))
            .percentChange90d(new BigDecimal("-60.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();


    private final Price polygonPrice = Price.builder()
            .id(2L)
            .cryptocurrencyId(2L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("10.0"))
            .percentChange24h(new BigDecimal("10.0"))
            .percentChange7d(new BigDecimal("10.0"))
            .percentChange30d(new BigDecimal("10.0"))
            .percentChange60d(new BigDecimal("10.0"))
            .percentChange90d(new BigDecimal("10.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Price polygonPricePastNegative = Price.builder()
            .id(2L)
            .cryptocurrencyId(2L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("-10.0"))
            .percentChange24h(new BigDecimal("-10.0"))
            .percentChange7d(new BigDecimal("-10.0"))
            .percentChange30d(new BigDecimal("-10.0"))
            .percentChange60d(new BigDecimal("-10.0"))
            .percentChange90d(new BigDecimal("-10.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Price polygonPricePastNegativeMinus10Each = Price.builder()
            .id(2L)
            .cryptocurrencyId(2L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("-10.0"))
            .percentChange24h(new BigDecimal("-20.0"))
            .percentChange7d(new BigDecimal("-30.0"))
            .percentChange30d(new BigDecimal("-40.0"))
            .percentChange60d(new BigDecimal("-50.0"))
            .percentChange90d(new BigDecimal("-60.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();


    private final Price avalanchePrice = Price.builder()
            .id(3L)
            .cryptocurrencyId(3L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("10.0"))
            .percentChange24h(new BigDecimal("10.0"))
            .percentChange7d(new BigDecimal("10.0"))
            .percentChange30d(new BigDecimal("10.0"))
            .percentChange60d(new BigDecimal("10.0"))
            .percentChange90d(new BigDecimal("10.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Price avalanchePricePastNegative = Price.builder()
            .id(3L)
            .cryptocurrencyId(3L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("-10.0"))
            .percentChange24h(new BigDecimal("-10.0"))
            .percentChange7d(new BigDecimal("-10.0"))
            .percentChange30d(new BigDecimal("-10.0"))
            .percentChange60d(new BigDecimal("-10.0"))
            .percentChange90d(new BigDecimal("-10.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final Price avalanchePricePastNegativeMinus10Each = Price.builder()
            .id(3L)
            .cryptocurrencyId(3L)
            .priceCurrent(new BigDecimal("1000.54321"))
            .percentChange1h(new BigDecimal("-10.0"))
            .percentChange24h(new BigDecimal("-20.0"))
            .percentChange7d(new BigDecimal("-30.0"))
            .percentChange30d(new BigDecimal("-40.0"))
            .percentChange60d(new BigDecimal("-50.0"))
            .percentChange90d(new BigDecimal("-60.0"))
            .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
            .build();

    private final String walletAddressToShort = "0x189B9cBd4AfF470aF2C0102f365FC1823d85796";
    private final String walletAddressValid = "0x189B9cBd4AfF470aF2C0102f365FC1823d857965";
    private final String walletAddressToLong = "0x189B9cBd4AfF470aF2C0102f365FC1823d8579650";
    private final String walletAddressInvalid = "0xZB6eD29A95753C3Ad948348e3e7b1A251080Ffb9";
    private final String walletAddressIllegalChar = "0x0a4c79cE84202b03e95B7a692E5D728d83C44c7!";

    private final Wallet ethereumTestNetWallet = TestnetWallet.ETHEREUM_WALLET_1;
    private final Wallet polygonTestNetWallet = TestnetWallet.POLYGON_WALLET_1;
    private final Wallet avalancheTestNetWallet = TestnetWallet.AVALANCHE_WALLET_1;

    private final Wallet ethereumTestNetWalletEmpty = TestnetWallet.ETHEREUM_WALLET_2_EMPTY;
    private final Wallet polygonTestNetWalletEmpty = TestnetWallet.POLYGON_WALLET_2_EMPTY;
    private final Wallet avalancheTestNetWalletEmpty = TestnetWallet.AVALANCHE_WALLET_2_EMPTY;

    private final String explorerAddressTestnetEthereum = "https://goerli.etherscan.io/address/";
    private final String explorerAddressTestnetPolygon = "https://mumbai.polygonscan.com/address/";
    private final String explorerAddressTestnetAvalanche = "https://testnet.snowtrace.io/address/";

    private final String redColorLowerValues = "rgba(255, 77, 0, 1)";
    private final String greenColorHigherValues = "rgba(7, 243, 107, 1)";

    private final String redColorLowerValuesFirefox = "rgb(255, 77, 0)";
    private final String greenColorHigherValuesFirefox = "rgb(7, 243, 107)";

}
