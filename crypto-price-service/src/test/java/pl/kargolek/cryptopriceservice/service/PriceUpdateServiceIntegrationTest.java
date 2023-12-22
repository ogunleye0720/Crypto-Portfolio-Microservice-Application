package pl.kargolek.cryptopriceservice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import pl.kargolek.cryptopriceservice.exception.MarketApiClientException;
import pl.kargolek.cryptopriceservice.extension.MarketMockServerDispatcherExtension;
import pl.kargolek.cryptopriceservice.extension.MySqlTestContainerExtension;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;
import pl.kargolek.cryptopriceservice.repository.CryptocurrencyRepository;
import pl.kargolek.cryptopriceservice.repository.PriceRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author Karol Kuta-Orlowicz
 */

@ExtendWith(MySqlTestContainerExtension.class)
@ExtendWith(MarketMockServerDispatcherExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IntegrationTest")
@ActiveProfiles("test")
class PriceUpdateServiceIntegrationTest {

    private static final BigDecimal BTC_RES_PRICE = new BigDecimal("25000.12345");
    private static final BigDecimal BTC_RES_PERCENT_1H = new BigDecimal("1.54321");
    private static final BigDecimal BTC_RES_PERCENT_24H = new BigDecimal("-2.54321");
    private static final BigDecimal BTC_RES_PERCENT_7D = new BigDecimal("3.54321");
    private static final BigDecimal BTC_RES_PERCENT_30D = new BigDecimal("-4.54321");
    private static final BigDecimal BTC_RES_PERCENT_60D = new BigDecimal("5.54321");
    private static final BigDecimal BTC_RES_PERCENT_90D = new BigDecimal("-6.54321");
    private static final BigDecimal ETH_RES_PRICE = new BigDecimal("2543.3040228407024");
    private static final BigDecimal ETH_PERCENT_1H = new BigDecimal("7.54321");
    private static final BigDecimal ETH_PERCENT_24H = new BigDecimal("-8.54321");
    private static final BigDecimal ETH_PERCENT_7D = new BigDecimal("9.54321");
    private static final BigDecimal ETH_PERCENT_30D = new BigDecimal("-10.54321");
    private static final BigDecimal ETH_PERCENT_60D = new BigDecimal("11.54321");
    private static final BigDecimal ETH_PERCENT_90D = new BigDecimal("-12.54321");
    private static final long COIN_MARKET_ID_FOR_EMPTY_DATA = 1000L;
    private static final long COIN_MARKET_ID_FOR_SERVER_STATUS_500 = 500L;
    private static final long COIN_MARKET_ID_FOR_NO_CRYPTOCURRENCY_DATA = 1500L;
    private static final long COIN_MARKET_ID_FOR_NULL_PRICE_DATA = 2000L;
    @Autowired
    private PriceUpdateService underTest;

    @Autowired
    CryptocurrencyService cryptocurrencyService;

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    private Price priceBTC;
    private Price priceETH;
    @Autowired
    private PriceRepository priceRepository;
    private Cryptocurrency cryptocurrencyBTC;
    private Cryptocurrency cryptocurrencyETH;

    @DynamicPropertySource
    static void webclientProperties(DynamicPropertyRegistry registry) {
        registry.add("api.coin.market.cap.baseUrl",
                () -> MarketMockServerDispatcherExtension.mockWebServer.url("/").toString());
    }

    @BeforeEach
    void setUp() {
        priceBTC = Price.builder()
                .priceCurrent(new BigDecimal("20000.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange1h(new BigDecimal("0.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange24h(new BigDecimal("1.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange7d(new BigDecimal("2.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange30d(new BigDecimal("3.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange60d(new BigDecimal("4.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange90d(new BigDecimal("5.5").setScale(12, RoundingMode.HALF_UP))
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        cryptocurrencyBTC = Cryptocurrency.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .coinMarketId(1L)
                .price(priceBTC)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        priceBTC.setCryptocurrency(cryptocurrencyBTC);

        priceETH = Price.builder()
                .priceCurrent(new BigDecimal("1800.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange1h(new BigDecimal("0.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange24h(new BigDecimal("1.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange7d(new BigDecimal("2.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange30d(new BigDecimal("3.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange60d(new BigDecimal("4.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange90d(new BigDecimal("5.5").setScale(12, RoundingMode.HALF_UP))
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        cryptocurrencyETH = Cryptocurrency.builder()
                .name("Ethereum")
                .symbol("ETH")
                .coinMarketId(1027L)
                .price(priceETH)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        priceETH.setCryptocurrency(cryptocurrencyETH);
    }

    @AfterEach
    void tearDown() {
        cryptocurrencyRepository.deleteAll();
    }

    @Test
    void whenReceiveOneCryptoPriceUpdate_thenPricesShouldBeUpdatedForOneEntity() {
        cryptocurrencyRepository.save(cryptocurrencyBTC);

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected).extracting(
                Price::getId,
                Price::getPriceCurrent,
                Price::getPercentChange1h,
                Price::getPercentChange24h,
                Price::getPercentChange7d,
                Price::getPercentChange30d,
                Price::getPercentChange60d,
                Price::getPercentChange90d
        ).containsExactly(
                tuple(
                        priceBTC.getId(),
                        BTC_RES_PRICE,
                        BTC_RES_PERCENT_1H,
                        BTC_RES_PERCENT_24H,
                        BTC_RES_PERCENT_7D,
                        BTC_RES_PERCENT_30D,
                        BTC_RES_PERCENT_60D,
                        BTC_RES_PERCENT_90D

                )
        );
    }

    @Test
    void whenReceiveTwoCryptoPriceUpdate_thenPricesShouldBeUpdatedForTwoEntities() {
        cryptocurrencyRepository.save(cryptocurrencyBTC);
        cryptocurrencyRepository.save(cryptocurrencyETH);

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected).extracting(
                Price::getId,
                Price::getPriceCurrent,
                Price::getPercentChange1h,
                Price::getPercentChange24h,
                Price::getPercentChange7d,
                Price::getPercentChange30d,
                Price::getPercentChange60d,
                Price::getPercentChange90d
        ).containsExactly(
                tuple(
                        priceBTC.getId(),
                        BTC_RES_PRICE,
                        BTC_RES_PERCENT_1H,
                        BTC_RES_PERCENT_24H,
                        BTC_RES_PERCENT_7D,
                        BTC_RES_PERCENT_30D,
                        BTC_RES_PERCENT_60D,
                        BTC_RES_PERCENT_90D
                ),
                tuple(
                        priceETH.getId(),
                        ETH_RES_PRICE,
                        ETH_PERCENT_1H,
                        ETH_PERCENT_24H,
                        ETH_PERCENT_7D,
                        ETH_PERCENT_30D,
                        ETH_PERCENT_60D,
                        ETH_PERCENT_90D
                )
        );
    }

    @Test
    void whenReceiveNoCryptoPriceUpdate_thenPriceShouldNotBeUpdate() {
        cryptocurrencyBTC.setCoinMarketId(COIN_MARKET_ID_FOR_NO_CRYPTOCURRENCY_DATA);
        cryptocurrencyRepository.save(cryptocurrencyBTC);

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected).extracting(
                Price::getId,
                Price::getPriceCurrent,
                Price::getPercentChange1h,
                Price::getPercentChange24h,
                Price::getPercentChange7d,
                Price::getPercentChange30d,
                Price::getPercentChange60d,
                Price::getPercentChange90d
        ).containsExactly(
                tuple(
                        priceBTC.getId(),
                        priceBTC.getPriceCurrent(),
                        priceBTC.getPercentChange1h(),
                        priceBTC.getPercentChange24h(),
                        priceBTC.getPercentChange7d(),
                        priceBTC.getPercentChange30d(),
                        priceBTC.getPercentChange60d(),
                        priceBTC.getPercentChange90d()
                )
        );
    }

    @Test
    void whenReceiveResponse500_thenPriceShouldNotBeUpdate() {
        cryptocurrencyBTC.setCoinMarketId(COIN_MARKET_ID_FOR_SERVER_STATUS_500);
        cryptocurrencyRepository.save(cryptocurrencyBTC);

        assertThatThrownBy(() -> underTest.updateCryptocurrencyPrices())
                .isInstanceOf(MarketApiClientException.class);
    }

    @Test
    void whenCryptocurrencyRepoEmpty_thenReturnEmptyList() {
        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected).hasSize(0);
    }

    @Test
    void whenResponse200PriceDataNull_thenNotUpdatePricesEntities() {
        cryptocurrencyBTC.setCoinMarketId(COIN_MARKET_ID_FOR_EMPTY_DATA);
        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected)
                .hasSize(0);
    }

    @Test
    void whenResponse200QuotaDataNull_thenNotUpdatePricesEntities() {
        cryptocurrencyBTC.setCoinMarketId(COIN_MARKET_ID_FOR_NULL_PRICE_DATA);
        cryptocurrencyRepository.save(cryptocurrencyBTC);

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected).extracting(
                Price::getId,
                Price::getPriceCurrent,
                Price::getPercentChange1h,
                Price::getPercentChange24h,
                Price::getPercentChange7d,
                Price::getPercentChange30d,
                Price::getPercentChange60d,
                Price::getPercentChange90d
        ).containsExactly(
                tuple(
                        priceBTC.getId(),
                        priceBTC.getPriceCurrent(),
                        priceBTC.getPercentChange1h(),
                        priceBTC.getPercentChange24h(),
                        priceBTC.getPercentChange7d(),
                        priceBTC.getPercentChange30d(),
                        priceBTC.getPercentChange60d(),
                        priceBTC.getPercentChange90d()
                )
        );
    }
}