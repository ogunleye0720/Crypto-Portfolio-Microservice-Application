package pl.kargolek.cryptopriceservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import pl.kargolek.cryptopriceservice.exception.PriceNotFoundException;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;
import pl.kargolek.cryptopriceservice.repository.PriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karol Kuta-Orlowicz
 */
@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
class PriceServiceUnitTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    private Price price;

    @BeforeEach
    void setUp() {
        var cryptocurrency = Cryptocurrency.builder()
                .id(1L)
                .name("Bitcoin")
                .symbol("BTC")
                .coinMarketId(RandomUtils.nextLong())
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        price = Price.builder()
                .id(1L)
                .priceCurrent(new BigDecimal("0.0000000001"))
                .percentChange1h(new BigDecimal("1.1"))
                .percentChange24h(new BigDecimal("2.1"))
                .percentChange7d(new BigDecimal("3.1"))
                .percentChange30d(new BigDecimal("4.1"))
                .percentChange60d(new BigDecimal("5.1"))
                .percentChange90d(new BigDecimal("6.1"))
                .cryptocurrency(cryptocurrency)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        cryptocurrency.setPrice(price);
    }

    @Test
    void whenUpdateCurrentPrice_thenShouldUpdateSuccessful() {
        when(priceRepository.findById(any()))
                .thenReturn(Optional.of(price));
        when(priceRepository.saveAll(List.of(price)))
                .thenReturn(List.of(price));

        var expected = priceService.updatePrices(List.of(price));

        assertThat(expected).extracting(
                        Price::getPriceCurrent
                )
                .containsExactly(
                        price.getPriceCurrent()
                );
    }

    @Test
    void whenUpdateByNotExistPrice_thenThrowPriceNotFoundExc() {
        when(priceRepository.findById(any()))
                .thenThrow(new PriceNotFoundException(""));

        assertThatThrownBy(() -> priceService.updatePrices(List.of(price)))
                .isInstanceOf(PriceNotFoundException.class);
    }

}