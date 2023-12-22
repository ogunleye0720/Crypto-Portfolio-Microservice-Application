package pl.kargolek.cryptopriceservice.service;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import pl.kargolek.cryptopriceservice.exception.PriceNotFoundException;
import pl.kargolek.cryptopriceservice.extension.MySqlTestContainerExtension;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;
import pl.kargolek.cryptopriceservice.repository.CryptocurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Karol Kuta-Orlowicz
 */
@ExtendWith(MySqlTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Tag("IntegrationTest")
@ActiveProfiles("test")
class PriceServiceIntegrationTest {

    @Autowired
    private PriceService underTestService;

    private Cryptocurrency cryptocurrency;

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    private Price priceSetup;

    @BeforeEach
    public void setup() {
        priceSetup = Price.builder()
                .priceCurrent(null)
                .percentChange1h(null)
                .percentChange24h(null)
                .percentChange7d(null)
                .percentChange30d(null)
                .percentChange60d(null)
                .percentChange90d(null)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC).minusDays(1L))
                .build();

        cryptocurrency = Cryptocurrency.builder()
                .name(RandomStringUtils.random(20))
                .symbol(RandomStringUtils.random(8))
                .coinMarketId(RandomUtils.nextLong())
                .price(priceSetup)
                .platform(RandomStringUtils.random(20))
                .tokenAddress(RandomStringUtils.random(20))
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC).minusDays(1L))
                .build();
        priceSetup.setCryptocurrency(cryptocurrency);

        cryptocurrencyRepository.save(cryptocurrency);
    }

    @AfterEach
    public void tearDown() {
        cryptocurrencyRepository.deleteAll();
    }

    @Test
    void whenUpdatePrice_thenPriceShouldUpdatedSuccessful() {
        var priceUpdate = Price.builder()
                .id(priceSetup.getId())
                .priceCurrent(new BigDecimal("100.5"))
                .percentChange1h(new BigDecimal("100.5"))
                .percentChange24h(new BigDecimal("100.5"))
                .percentChange7d(new BigDecimal("100.5"))
                .percentChange30d(new BigDecimal("100.5"))
                .percentChange60d(new BigDecimal("100.5"))
                .percentChange90d(new BigDecimal("100.5"))
                .cryptocurrency(cryptocurrency)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC).plusDays(1L))
                .build();

        var expected = underTestService.updatePrices(List.of(priceUpdate));

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
                        Tuple.tuple(
                                priceUpdate.getId(),
                                priceUpdate.getPriceCurrent(),
                                priceUpdate.getPercentChange1h(),
                                priceUpdate.getPercentChange24h(),
                                priceUpdate.getPercentChange7d(),
                                priceUpdate.getPercentChange30d(),
                                priceUpdate.getPercentChange60d(),
                                priceUpdate.getPercentChange90d()
                        )
                );

        assertThat(expected.stream().map(Price::getLastUpdate).findFirst().orElseThrow())
                .isEqualToIgnoringNanos(priceUpdate.getLastUpdate());
    }

    @Test
    void whenUpdatePricesByEmptyList_thenReturnEmptyEntitiesList() {
        var expected = underTestService.updatePrices(List.of());

        assertThat(expected).hasSize(0);
    }

    @Test
    void whenUpdatePricesByNotExistPrice_thenThrowPriceNotFoundExc() {
        var priceNotExist = Price.builder()
                .id(120L)
                .priceCurrent(new BigDecimal("120.0"))
                .cryptocurrency(cryptocurrency)
                .build();

        assertThatThrownBy(() -> underTestService.updatePrices(List.of(priceNotExist)))
                .isInstanceOf(PriceNotFoundException.class);
    }
}