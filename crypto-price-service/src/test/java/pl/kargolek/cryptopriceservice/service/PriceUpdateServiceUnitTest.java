package pl.kargolek.cryptopriceservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kargolek.cryptopriceservice.dto.client.CryptocurrencyQuoteDTO;
import pl.kargolek.cryptopriceservice.dto.client.PriceQuoteDTO;
import pl.kargolek.cryptopriceservice.dto.client.QuotesDataDTO;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karol Kuta-Orlowicz
 */
@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
class PriceUpdateServiceUnitTest {
    @Mock
    private CryptocurrencyService cryptocurrencyService;

    @Mock
    private MarketApiClientService marketApiClientService;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceUpdateService underTest;

    private List<Cryptocurrency> cryptocurrencyEntities;
    private Price priceETH;
    private Price priceBTC;
    private QuotesDataDTO quotesDataDTO;
    private PriceQuoteDTO priceQuoteDtoBTC;
    private PriceQuoteDTO priceQuoteDtoETH;
    private CryptocurrencyQuoteDTO cryptoQuoteDtoBTC;
    private CryptocurrencyQuoteDTO cryptoQuoteDtoETH;

    @BeforeEach
    void setUp() {
        final String btcName = "Bitcoin";
        final String btcSymbol = "BTC";
        final long btcMarketId = 1L;
        var bitcoin = Cryptocurrency.builder()
                .id(1L)
                .name(btcName)
                .symbol(btcSymbol)
                .coinMarketId(btcMarketId)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        final String ethName = "Ethereum";
        final String ethSymbol = "ETH";
        final long ethMarketId = 1027L;
        var ethereum = Cryptocurrency.builder()
                .id(2L)
                .name(ethName)
                .symbol(ethSymbol)
                .coinMarketId(ethMarketId)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        priceBTC = Price.builder()
                .id(1L)
                .priceCurrent(new BigDecimal("20000.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange1h(new BigDecimal("0.5").setScale(12, RoundingMode.HALF_UP))
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .cryptocurrency(bitcoin)
                .build();
        bitcoin.setPrice(priceBTC);

        priceETH = Price.builder()
                .id(2L)
                .priceCurrent(new BigDecimal("1500.5").setScale(12, RoundingMode.HALF_UP))
                .percentChange1h(new BigDecimal("-1.5").setScale(12, RoundingMode.HALF_UP))
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .cryptocurrency(ethereum)
                .build();
        ethereum.setPrice(priceETH);

        cryptocurrencyEntities = List.of(bitcoin, ethereum);


        priceQuoteDtoBTC = new PriceQuoteDTO()
                .setPriceCurrent(new BigDecimal("21000.5").setScale(12, RoundingMode.HALF_UP))
                .setPercentChange1h(new BigDecimal("5.0").setScale(12, RoundingMode.HALF_UP))
                .setPercentChange24h(new BigDecimal("7.5").setScale(12, RoundingMode.HALF_UP));
        priceQuoteDtoETH = new PriceQuoteDTO()
                .setPriceCurrent(new BigDecimal("2000.5").setScale(12, RoundingMode.HALF_UP))
                .setPercentChange1h(new BigDecimal("25.5").setScale(12, RoundingMode.HALF_UP))
                .setPercentChange24h(new BigDecimal("40.5").setScale(12, RoundingMode.HALF_UP));

        cryptoQuoteDtoBTC = new CryptocurrencyQuoteDTO()
                .setName(btcName)
                .setSymbol(btcSymbol)
                .setCoinMarketId(btcMarketId)
                .setQuote(Map.of("USD", priceQuoteDtoBTC));
        cryptoQuoteDtoETH = new CryptocurrencyQuoteDTO()
                .setName(ethName)
                .setSymbol(ethSymbol)
                .setCoinMarketId(ethMarketId)
                .setQuote(Map.of("USD", priceQuoteDtoETH));

        quotesDataDTO = new QuotesDataDTO()
                .setData(Map.of("1", cryptoQuoteDtoBTC, "1027", cryptoQuoteDtoETH));

    }

    @Test
    void whenUpdateCryptocurrencyPrices_thenPricesShouldBeUpdated() {
        when(cryptocurrencyService.getCryptocurrencies())
                .thenReturn(cryptocurrencyEntities);

        when(marketApiClientService.getLatestPriceByIds(any()))
                .thenReturn(quotesDataDTO);

        var priceBTC = Price.builder()
                .priceCurrent(priceQuoteDtoBTC.getPriceCurrent())
                .percentChange1h(priceQuoteDtoBTC.getPercentChange1h())
                .percentChange24h(priceQuoteDtoBTC.getPercentChange24h())
                .percentChange7d(priceQuoteDtoBTC.getPercentChange7d())
                .percentChange30d(priceQuoteDtoBTC.getPercentChange30d())
                .percentChange60d(priceQuoteDtoBTC.getPercentChange60d())
                .percentChange90d(priceQuoteDtoBTC.getPercentChange90d())
                .build();

        var priceETH = Price.builder()
                .priceCurrent(priceQuoteDtoETH.getPriceCurrent())
                .percentChange1h(priceQuoteDtoETH.getPercentChange1h())
                .percentChange24h(priceQuoteDtoETH.getPercentChange24h())
                .percentChange7d(priceQuoteDtoETH.getPercentChange7d())
                .percentChange30d(priceQuoteDtoETH.getPercentChange30d())
                .percentChange60d(priceQuoteDtoETH.getPercentChange60d())
                .percentChange90d(priceQuoteDtoETH.getPercentChange90d())
                .build();

        when(priceService.updatePrices(any()))
                .thenReturn(List.of(priceBTC, priceETH));

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected)
                .extracting(
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
                                priceQuoteDtoBTC.getPriceCurrent(),
                                priceQuoteDtoBTC.getPercentChange1h(),
                                priceQuoteDtoBTC.getPercentChange24h(),
                                priceQuoteDtoBTC.getPercentChange7d(),
                                priceQuoteDtoBTC.getPercentChange30d(),
                                priceQuoteDtoBTC.getPercentChange60d(),
                                priceQuoteDtoBTC.getPercentChange90d()
                        ),
                        tuple(
                                priceETH.getId(),
                                priceQuoteDtoETH.getPriceCurrent(),
                                priceQuoteDtoETH.getPercentChange1h(),
                                priceQuoteDtoETH.getPercentChange24h(),
                                priceQuoteDtoETH.getPercentChange7d(),
                                priceQuoteDtoETH.getPercentChange30d(),
                                priceQuoteDtoETH.getPercentChange60d(),
                                priceQuoteDtoETH.getPercentChange90d()
                        )
                );
    }

    @Test
    void whenCryptocurrenciesIsEmpty_thenReturnEmptyList() {
        when(cryptocurrencyService.getCryptocurrencies())
                .thenReturn(Collections.emptyList());

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected).hasSize(0);
    }

    @Test
    void whenCryptoQuoteDTOFieldsAreNull_thenReturnPreviousPrices() {
        when(cryptocurrencyService.getCryptocurrencies())
                .thenReturn(cryptocurrencyEntities);

        cryptoQuoteDtoBTC.setName(null)
                .setSymbol(null)
                .setCoinMarketId(null)
                .setQuote(null);

        cryptoQuoteDtoETH.setName(null)
                .setSymbol(null)
                .setCoinMarketId(null)
                .setQuote(null);

        quotesDataDTO = new QuotesDataDTO()
                .setData(Map.of("1", cryptoQuoteDtoBTC, "1027", cryptoQuoteDtoETH));

        when(marketApiClientService.getLatestPriceByIds(any()))
                .thenReturn(quotesDataDTO);

        var priceEntities = cryptocurrencyEntities.stream()
                .map(Cryptocurrency::getPrice)
                .toList();
        when(priceService.updatePrices(any()))
                .thenReturn(priceEntities);

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected)
                .extracting(
                        Price::getId,
                        Price::getPriceCurrent,
                        Price::getPercentChange1h,
                        Price::getPercentChange24h
                ).containsExactly(
                        tuple(
                                priceBTC.getId(),
                                priceBTC.getPriceCurrent(),
                                priceBTC.getPercentChange1h(),
                                priceBTC.getPercentChange24h()
                        ),
                        tuple(
                                priceETH.getId(),
                                priceETH.getPriceCurrent(),
                                priceETH.getPercentChange1h(),
                                priceETH.getPercentChange24h()
                        )
                );
    }

    @Test
    void whenPriceQuoteDTOSFieldsAreNull_thenReturnPreviousPrices() {
        when(cryptocurrencyService.getCryptocurrencies())
                .thenReturn(cryptocurrencyEntities);

        cryptoQuoteDtoBTC.setQuote(new HashMap<>());
        cryptoQuoteDtoETH.setQuote(new HashMap<>());

        quotesDataDTO = new QuotesDataDTO()
                .setData(Map.of("1", cryptoQuoteDtoBTC, "1027", cryptoQuoteDtoETH));

        when(marketApiClientService.getLatestPriceByIds(any()))
                .thenReturn(quotesDataDTO);

        var priceEntities = cryptocurrencyEntities.stream()
                .map(Cryptocurrency::getPrice)
                .toList();
        when(priceService.updatePrices(any()))
                .thenReturn(priceEntities);

        var expected = underTest.updateCryptocurrencyPrices();

        assertThat(expected)
                .extracting(
                        Price::getId,
                        Price::getPriceCurrent,
                        Price::getPercentChange1h,
                        Price::getPercentChange24h
                ).containsExactly(
                        tuple(
                                priceBTC.getId(),
                                priceBTC.getPriceCurrent(),
                                priceBTC.getPercentChange1h(),
                                priceBTC.getPercentChange24h()
                        ),
                        tuple(
                                priceETH.getId(),
                                priceETH.getPriceCurrent(),
                                priceETH.getPercentChange1h(),
                                priceETH.getPercentChange24h()
                        )
                );
    }
}