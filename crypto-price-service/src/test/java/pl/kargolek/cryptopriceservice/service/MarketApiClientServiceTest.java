package pl.kargolek.cryptopriceservice.service;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kargolek.cryptopriceservice.dto.client.CryptocurrencyMapDTO;
import pl.kargolek.cryptopriceservice.dto.client.CryptocurrencyQuoteDTO;
import pl.kargolek.cryptopriceservice.dto.client.PriceQuoteDTO;
import pl.kargolek.cryptopriceservice.exception.MarketApiClientException;
import pl.kargolek.cryptopriceservice.extension.MarketMockServerDispatcherExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author Karol Kuta-Orlowicz
 */
@ExtendWith(MarketMockServerDispatcherExtension.class)
@Tag("UnitTest")
class MarketApiClientServiceTest {
    private static final String MARKET_ID_400_RESPONSE = "400";
    private static final String MARKET_ID_401_RESPONSE = "401";
    private MarketApiClientService underTest;

    @BeforeEach
    void setUp(MockWebServer mockWebServer) {
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();
        underTest = new MarketApiClientService(webClient,
                "/v2/cryptocurrency/quotes/latest",
                "/v1/cryptocurrency/map");
    }

    @Test
    void whenGetPriceServerRespond200_thenClientReturnListQuotesDataDTO() {
        var response = underTest.getLatestPriceByIds("1");
        var cryptoResponse = response.getData().get("1");
        var priceResponse = response.getData().get("1").getQuote().get("USD");

        assertThat(cryptoResponse)
                .extracting(
                        CryptocurrencyQuoteDTO::getName,
                        CryptocurrencyQuoteDTO::getSymbol,
                        CryptocurrencyQuoteDTO::getCoinMarketId
                ).containsExactly(
                        "Bitcoin",
                        "BTC",
                        1L
                );

        assertThat(priceResponse).extracting(
                PriceQuoteDTO::getPriceCurrent,
                PriceQuoteDTO::getPercentChange1h,
                PriceQuoteDTO::getPercentChange24h,
                PriceQuoteDTO::getPercentChange7d,
                PriceQuoteDTO::getPercentChange30d,
                PriceQuoteDTO::getPercentChange60d,
                PriceQuoteDTO::getPercentChange90d
        ).containsExactly(
                new BigDecimal("25000.12345"),
                new BigDecimal("1.54321"),
                new BigDecimal("-2.54321"),
                new BigDecimal("3.54321"),
                new BigDecimal("-4.54321"),
                new BigDecimal("5.54321"),
                new BigDecimal("-6.54321")
        );
    }

    @Test
    void whenGetPriceServerRespond400_thenClientThrowCustomExc() {
        assertThatThrownBy(() -> underTest.getLatestPriceByIds(MARKET_ID_400_RESPONSE))
                .isInstanceOf(MarketApiClientException.class)
                .hasMessageContaining("Status code: 400, clientMessage: Error during calling get request, serverMessage: Invalid value for \"id\"");
    }

    @Test
    void whenGetPriceServerRespond401_thenClientThrowCustomExc() {
        assertThatThrownBy(() -> underTest.getLatestPriceByIds(MARKET_ID_401_RESPONSE))
                .isInstanceOf(MarketApiClientException.class)
                .hasMessageContaining("Status code: 401, clientMessage: Error during calling get request, serverMessage: API key missing.");
    }

    @Test
    void whenGetPriceServerRespond500_thenClientThrowCustomExc() {
        assertThatThrownBy(() -> underTest.getLatestPriceByIds("500"))
                .isInstanceOf(MarketApiClientException.class)
                .hasMessageContaining("Error during calling get request, serverMessage: An internal server error occurred");
    }

    @Test
    void whenGetMapServerRes200_thenClientReturnMapDataDTO() {
        var expected = underTest.getMapCryptocurrencyInfo("ETH");

        assertThat(expected.getData())
                .extracting(
                        CryptocurrencyMapDTO::getName,
                        CryptocurrencyMapDTO::getSymbol,
                        CryptocurrencyMapDTO::getCoinMarketId
                ).containsExactly(
                        tuple(
                                "Ethereum",
                                "ETH",
                                1027L
                        )
                );
    }

    @Test
    void whenGetMapServerRes400_thenThrowMarketClientExc() {
        assertThatThrownBy(() -> underTest.getMapCryptocurrencyInfo(""))
                .isInstanceOf(MarketApiClientException.class)
                .hasMessageContaining("serverMessage: \"symbol\" is not allowed to be empty");
    }

    @Test
    void whenGetMapServerRes401_thenThrowMarketClientExc() {
        assertThatThrownBy(() -> underTest.getMapCryptocurrencyInfo("SERVER_401"))
                .isInstanceOf(MarketApiClientException.class)
                .hasMessageContaining("Status code: 401, clientMessage: Error during calling get request, serverMessage: API key missing.");
    }

    @Test
    void whenGetMapServerRes500_thenThrowMarketClientExc() {
        assertThatThrownBy(() -> underTest.getMapCryptocurrencyInfo("SERVER_500"))
                .isInstanceOf(MarketApiClientException.class)
                .hasMessageContaining("Status code: 500, clientMessage: Error during calling get request, serverMessage: An internal server error occurred");
    }

}