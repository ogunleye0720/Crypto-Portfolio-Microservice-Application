package pl.kargolek.walletservice.testutils.fixture;

import pl.kargolek.walletservice.dto.TokenDTO;
import pl.kargolek.walletservice.dto.TokenPlatformDTO;
import pl.kargolek.walletservice.dto.TokenPriceDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Karol Kuta-Orlowicz
 */
public class DataCryptocurrency {

    private final LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 21, 23, 12, 59, 999_999_999);

    public TokenDTO getCryptocurrencyBTC() {
        return new TokenDTO()
                .setId(1L)
                .setName("Bitcoin")
                .setSymbol("BTC")
                .setCoinMarketId(1L)
                .setPlatform(new TokenPlatformDTO()
                        .setPlatform("platform_btc")
                        .setTokenAddress("token_address_btc"))
                .setPrice(new TokenPriceDTO()
                        .setId(1L)
                        .setPriceCurrent(new BigDecimal("20000.5"))
                        .setPercentChange1h(new BigDecimal("0.5"))
                        .setPercentChange24h(new BigDecimal("1.0"))
                        .setPercentChange7d(new BigDecimal("1.5"))
                        .setPercentChange30d(new BigDecimal("2.0"))
                        .setPercentChange60d(new BigDecimal("2.5"))
                        .setPercentChange90d(new BigDecimal("3.0"))
                        .setLastUpdate(localDateTime)
                ).setLastUpdate(localDateTime);
    }

    public TokenDTO getCryptocurrencyETH() {
        return new TokenDTO()
                .setId(2L)
                .setName("Ethereum")
                .setSymbol("ETH")
                .setCoinMarketId(1027L)
                .setPlatform(new TokenPlatformDTO()
                        .setPlatform("platform_eth")
                        .setTokenAddress("token_address_eth"))
                .setPrice(new TokenPriceDTO()
                        .setId(2L)
                        .setPriceCurrent(new BigDecimal("1800.5"))
                        .setPercentChange1h(new BigDecimal("10.5"))
                        .setPercentChange24h(new BigDecimal("11.0"))
                        .setPercentChange7d(new BigDecimal("11.5"))
                        .setPercentChange30d(new BigDecimal("12.0"))
                        .setPercentChange60d(new BigDecimal("12.5"))
                        .setPercentChange90d(new BigDecimal("13.0"))
                        .setLastUpdate(localDateTime)
                ).setLastUpdate(localDateTime);
    }

    public TokenDTO getCryptocurrencyMatic() {
        return new TokenDTO()
                .setId(3L)
                .setName("Polygon")
                .setSymbol("MATIC")
                .setCoinMarketId(3890L)
                .setPlatform(new TokenPlatformDTO()
                        .setPlatform("platform_eth")
                        .setTokenAddress("token_address_eth"))
                .setPrice(new TokenPriceDTO()
                        .setId(3L)
                        .setPriceCurrent(new BigDecimal("1800.5"))
                        .setPercentChange1h(new BigDecimal("10.5"))
                        .setPercentChange24h(new BigDecimal("11.0"))
                        .setPercentChange7d(new BigDecimal("11.5"))
                        .setPercentChange30d(new BigDecimal("12.0"))
                        .setPercentChange60d(new BigDecimal("12.5"))
                        .setPercentChange90d(new BigDecimal("13.0"))
                        .setLastUpdate(localDateTime)
                ).setLastUpdate(localDateTime);
    }

    public TokenDTO getCryptocurrencyAvax() {
        return new TokenDTO()
                .setId(4L)
                .setName("Avalanche")
                .setSymbol("AVAX")
                .setCoinMarketId(5805L)
                .setPlatform(new TokenPlatformDTO()
                        .setPlatform("platform_eth")
                        .setTokenAddress("token_address_eth"))
                .setPrice(new TokenPriceDTO()
                        .setId(4L)
                        .setPriceCurrent(new BigDecimal("1800.5"))
                        .setPercentChange1h(new BigDecimal("10.5"))
                        .setPercentChange24h(new BigDecimal("11.0"))
                        .setPercentChange7d(new BigDecimal("11.5"))
                        .setPercentChange30d(new BigDecimal("12.0"))
                        .setPercentChange60d(new BigDecimal("12.5"))
                        .setPercentChange90d(new BigDecimal("13.0"))
                        .setLastUpdate(localDateTime)
                ).setLastUpdate(localDateTime);
    }

    public TokenDTO[] getCryptocurrencies() {
        return new TokenDTO[]{
                getCryptocurrencyBTC(),
                getCryptocurrencyETH(),
                getCryptocurrencyMatic(),
                getCryptocurrencyAvax()
        };
    }
}
