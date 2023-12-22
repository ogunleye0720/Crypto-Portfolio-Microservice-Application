package pl.kargolek.cryptopriceservice.repository;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import pl.kargolek.cryptopriceservice.extension.MySqlTestContainerExtension;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MySqlTestContainerExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Tag("IntegrationTest")
@ActiveProfiles("test")
public class PriceRepositoryIntegrationTest {

    @Autowired
    private PriceRepository underTestRepository;
    @Autowired
    private CryptocurrencyRepository cryptoRepository;

    private Cryptocurrency cryptocurrency;
    private Price price;

    @BeforeEach
    public void setup() {
        cryptocurrency = Cryptocurrency.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .coinMarketId(RandomUtils.nextLong())
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        price = Price.builder()
                .priceCurrent(new BigDecimal("0.0000000000812"))
                .percentChange1h(new BigDecimal("1.1"))
                .percentChange24h(new BigDecimal("2.1"))
                .percentChange7d(new BigDecimal("3.1"))
                .percentChange30d(new BigDecimal("4.1"))
                .percentChange60d(new BigDecimal("5.1"))
                .percentChange90d(new BigDecimal("6.1"))
                .cryptocurrency(cryptocurrency)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void whenFindByIdPriceEntity_thenReturnOptionalPriceEntity() {
        cryptoRepository.save(cryptocurrency);
        underTestRepository.save(price);

        var expected = underTestRepository.findById(price.getId());

        assertThat(expected)
                .get()
                .extracting(
                        Price::getId,
                        Price::getPriceCurrent,
                        Price::getPercentChange1h,
                        Price::getPercentChange24h
                ).containsExactly(
                        price.getId(),
                        price.getPriceCurrent(),
                        price.getPercentChange1h(),
                        price.getPercentChange24h()
                );
        assertThat(expected.map(Price::getCryptocurrency))
                .get()
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId
                ).containsExactly(
                        cryptocurrency.getId(),
                        cryptocurrency.getName(),
                        cryptocurrency.getSymbol(),
                        cryptocurrency.getCoinMarketId()
                );
    }

    @Test
    void whenFindAllPrices_thenReturnPricesList() {
        cryptoRepository.save(cryptocurrency);
        underTestRepository.save(price);

        var expected = underTestRepository.findAll();

        assertThat(expected)
                .hasSize(1)
                .extracting(
                        Price::getId,
                        Price::getPriceCurrent,
                        Price::getPercentChange1h,
                        Price::getPercentChange24h
                ).containsExactly(
                        tuple(
                                price.getId(),
                                price.getPriceCurrent(),
                                price.getPercentChange1h(),
                                price.getPercentChange24h()
                        )
                );
    }

    @Test
    void whenDeletePriceEntity_thenShouldDeleteEntity() {
        cryptoRepository.save(cryptocurrency);
        underTestRepository.save(price);

        underTestRepository.delete(price);

        var prices = underTestRepository.findAll();
        assertThat(prices)
                .hasSize(0)
                .extracting(
                        Price::getId,
                        Price::getPriceCurrent,
                        Price::getPercentChange1h,
                        Price::getPercentChange24h
                ).doesNotContain(
                        tuple(
                                price.getId(),
                                price.getPriceCurrent(),
                                price.getPercentChange1h(),
                                price.getPercentChange24h()
                        )
                );
    }

    @Test
    void whenSavePriceEntityWithNullPriceCurrent_thenShouldShouldSave() {
        cryptoRepository.save(cryptocurrency);

        var priceGiven = Price.builder()
                .priceCurrent(null)
                .percentChange1h(new BigDecimal("1.1"))
                .percentChange24h(new BigDecimal("2.1"))
                .percentChange7d(new BigDecimal("3.1"))
                .percentChange30d(new BigDecimal("4.1"))
                .percentChange60d(new BigDecimal("5.1"))
                .percentChange90d(new BigDecimal("6.1"))
                .cryptocurrency(cryptocurrency)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        var expected = underTestRepository.save(priceGiven);

        assertThat(expected)
                .isIn(underTestRepository.findAll());
    }

    @Test
    void whenSavePriceWithNullCryptocurrency_thenThrowExc() {
        cryptoRepository.save(cryptocurrency);

        var priceGiven = Price.builder()
                .priceCurrent(new BigDecimal("1.0"))
                .percentChange1h(new BigDecimal("1.1"))
                .percentChange24h(new BigDecimal("2.1"))
                .percentChange7d(new BigDecimal("3.1"))
                .percentChange30d(new BigDecimal("4.1"))
                .percentChange60d(new BigDecimal("5.1"))
                .percentChange90d(new BigDecimal("6.1"))
                .cryptocurrency(null)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        assertThatThrownBy(() -> underTestRepository.save(priceGiven))
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void whenSavePriceWithoutAnyPercentageEntityField_thenShouldSaveSuccessful() {
        cryptoRepository.save(cryptocurrency);

        var priceGiven = Price.builder()
                .priceCurrent(new BigDecimal("1.0"))
                .percentChange1h(new BigDecimal("1.1"))
                .percentChange24h(new BigDecimal("2.1"))
                .percentChange7d(new BigDecimal("3.1"))
                .percentChange30d(new BigDecimal("4.1"))
                .percentChange60d(new BigDecimal("5.1"))
                .percentChange90d(new BigDecimal("6.1"))
                .cryptocurrency(cryptocurrency)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        var expected = underTestRepository.save(priceGiven);

        assertThat(expected).isIn(underTestRepository.findAll());
    }

    @Test
    void whenSavePriceWithoutLastUpdate_thenThrowExc() {
        cryptoRepository.save(cryptocurrency);

        var priceGiven = Price.builder()
                .priceCurrent(new BigDecimal("1.0"))
                .percentChange1h(new BigDecimal("1.1"))
                .percentChange24h(new BigDecimal("2.1"))
                .percentChange7d(new BigDecimal("3.1"))
                .percentChange30d(new BigDecimal("4.1"))
                .percentChange60d(new BigDecimal("5.1"))
                .percentChange90d(new BigDecimal("6.1"))
                .cryptocurrency(cryptocurrency)
                .build();

        assertThatThrownBy(() -> underTestRepository.save(priceGiven))
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void whenSaveTwoPricesWithSameCryptocurrency_thenThrowConstraintViolationExc() {
        cryptoRepository.save(cryptocurrency);
        underTestRepository.save(price);

        var priceGiven = Price.builder()
                .priceCurrent(new BigDecimal("1.0"))
                .percentChange1h(new BigDecimal("1.1"))
                .percentChange24h(new BigDecimal("2.1"))
                .percentChange7d(new BigDecimal("3.1"))
                .percentChange30d(new BigDecimal("4.1"))
                .percentChange60d(new BigDecimal("5.1"))
                .percentChange90d(new BigDecimal("6.1"))
                .cryptocurrency(cryptocurrency)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        assertThatThrownBy(() -> underTestRepository.save(priceGiven))
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void whenDeleteCryptocurrencyEntity_thenShouldDeletePriceEntity() {
        cryptoRepository.save(cryptocurrency);
        underTestRepository.save(price);

        cryptoRepository.deleteAll();

        var prices = underTestRepository.findAll();

        assertThat(prices)
                .extracting(
                        Price::getId,
                        Price::getPriceCurrent,
                        Price::getPercentChange1h,
                        Price::getPercentChange24h
                ).doesNotContain(
                        tuple(
                                price.getId(),
                                price.getPriceCurrent(),
                                price.getPercentChange1h(),
                                price.getPercentChange24h()
                        )
                );
    }
}