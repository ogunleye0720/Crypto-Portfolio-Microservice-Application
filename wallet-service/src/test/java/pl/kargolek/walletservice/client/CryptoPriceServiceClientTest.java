package pl.kargolek.walletservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.kargolek.walletservice.dto.TokenDTO;
import pl.kargolek.walletservice.dto.TokenPriceDTO;
import pl.kargolek.walletservice.testutils.BaseParamTest;
import pl.kargolek.walletservice.testutils.config.ConfigCryptoPriceMockServer;
import pl.kargolek.walletservice.testutils.config.InitializerCryptoPriceMockWebServer;
import pl.kargolek.walletservice.testutils.fixture.DataCryptocurrency;
import pl.kargolek.walletservice.testutils.fixture.ResponseCryptoPriceService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author Karol Kuta-Orlowicz
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {InitializerCryptoPriceMockWebServer.class}, classes = {ConfigCryptoPriceMockServer.class})
@Tag("IntegrationTest")
class CryptoPriceServiceClientTest extends BaseParamTest {

    @Autowired
    private CryptoPriceServiceClient underTest;

    @Autowired
    private MockWebServer mockWebServer;

    @Test
    void whenGetAllCryptocurrenciesStatus200_thenReturnListCryptocurrencyDTO(ResponseCryptoPriceService responseCryptoPriceService,
                                                                             DataCryptocurrency dataCryptocurrency) throws JsonProcessingException {
        mockWebServer.enqueue(
                responseCryptoPriceService.getAllCryptocurrenciesHttpStatusOK()
        );

        var expected = underTest.getTokens();

        var cryptocurrencyBTC = dataCryptocurrency.getCryptocurrencyBTC();
        var cryptocurrencyETH = dataCryptocurrency.getCryptocurrencyETH();
        var cryptocurrencyMATIC = dataCryptocurrency.getCryptocurrencyMatic();
        var cryptocurrencyAVAX = dataCryptocurrency.getCryptocurrencyAvax();

        assertThat(expected)
                .hasSizeGreaterThan(0)
                .extracting(
                        TokenDTO::getId,
                        TokenDTO::getName,
                        TokenDTO::getSymbol,
                        TokenDTO::getCoinMarketId,
                        TokenDTO::getLastUpdate
                ).containsExactly(
                        tuple(
                                cryptocurrencyBTC.getId(),
                                cryptocurrencyBTC.getName(),
                                cryptocurrencyBTC.getSymbol(),
                                cryptocurrencyBTC.getCoinMarketId(),
                                cryptocurrencyBTC.getLastUpdate()
                        ),
                        tuple(
                                cryptocurrencyETH.getId(),
                                cryptocurrencyETH.getName(),
                                cryptocurrencyETH.getSymbol(),
                                cryptocurrencyETH.getCoinMarketId(),
                                cryptocurrencyETH.getLastUpdate()
                        ),
                        tuple(
                                cryptocurrencyMATIC.getId(),
                                cryptocurrencyMATIC.getName(),
                                cryptocurrencyMATIC.getSymbol(),
                                cryptocurrencyMATIC.getCoinMarketId(),
                                cryptocurrencyMATIC.getLastUpdate()
                        ),
                        tuple(
                                cryptocurrencyAVAX.getId(),
                                cryptocurrencyAVAX.getName(),
                                cryptocurrencyAVAX.getSymbol(),
                                cryptocurrencyAVAX.getCoinMarketId(),
                                cryptocurrencyAVAX.getLastUpdate()
                        )
                );

        var priceBTC = cryptocurrencyBTC.getPrice();
        var priceETH = cryptocurrencyETH.getPrice();
        var priceMATIC = cryptocurrencyMATIC.getPrice();
        var priceAVAX = cryptocurrencyAVAX.getPrice();

        assertThat(expected).extracting(
                TokenDTO::getPrice
        ).extracting(
                TokenPriceDTO::getId,
                TokenPriceDTO::getPriceCurrent,
                TokenPriceDTO::getPercentChange1h,
                TokenPriceDTO::getPercentChange24h,
                TokenPriceDTO::getPercentChange7d,
                TokenPriceDTO::getPercentChange30d,
                TokenPriceDTO::getPercentChange60d,
                TokenPriceDTO::getPercentChange90d,
                TokenPriceDTO::getLastUpdate
        ).containsExactly(
                tuple(
                        priceBTC.getId(),
                        priceBTC.getPriceCurrent(),
                        priceBTC.getPercentChange1h(),
                        priceBTC.getPercentChange24h(),
                        priceBTC.getPercentChange7d(),
                        priceBTC.getPercentChange30d(),
                        priceBTC.getPercentChange60d(),
                        priceBTC.getPercentChange90d(),
                        priceBTC.getLastUpdate()
                ),
                tuple(
                        priceETH.getId(),
                        priceETH.getPriceCurrent(),
                        priceETH.getPercentChange1h(),
                        priceETH.getPercentChange24h(),
                        priceETH.getPercentChange7d(),
                        priceETH.getPercentChange30d(),
                        priceETH.getPercentChange60d(),
                        priceETH.getPercentChange90d(),
                        priceETH.getLastUpdate()
                ),
                tuple(
                        priceMATIC.getId(),
                        priceMATIC.getPriceCurrent(),
                        priceMATIC.getPercentChange1h(),
                        priceMATIC.getPercentChange24h(),
                        priceMATIC.getPercentChange7d(),
                        priceMATIC.getPercentChange30d(),
                        priceMATIC.getPercentChange60d(),
                        priceMATIC.getPercentChange90d(),
                        priceMATIC.getLastUpdate()
                ),
                tuple(
                        priceAVAX.getId(),
                        priceAVAX.getPriceCurrent(),
                        priceAVAX.getPercentChange1h(),
                        priceAVAX.getPercentChange24h(),
                        priceAVAX.getPercentChange7d(),
                        priceAVAX.getPercentChange30d(),
                        priceAVAX.getPercentChange60d(),
                        priceAVAX.getPercentChange90d(),
                        priceAVAX.getLastUpdate()
                )
        );
    }

    @Test
    void whenGetAllCryptocurrenciesStatus500_thenReturnEmptyListCryptocurrencyDTO(ResponseCryptoPriceService responseCryptoPriceService) {
        mockWebServer.enqueue(
                responseCryptoPriceService.getAllCryptocurrenciesHttpStatus500()
        );

        var expected = underTest.getTokens();

        assertThat(expected).hasSize(0);
    }

    @Test
    void whenGetCryptocurrenciesByNameStatus200_thenReturnListCryptocurrencyDTO(ResponseCryptoPriceService responseCryptoPriceService,
                                                                                DataCryptocurrency dataCryptocurrency) throws JsonProcessingException {
        mockWebServer.enqueue(
                responseCryptoPriceService.getAllCryptocurrenciesHttpStatusOK()
        );

        var expected = underTest.getTokensByName(List.of("BTC", "ETH"));

        var cryptocurrencyBTC = dataCryptocurrency.getCryptocurrencyBTC();
        var cryptocurrencyETH = dataCryptocurrency.getCryptocurrencyETH();
        var cryptocurrencyMATIC = dataCryptocurrency.getCryptocurrencyMatic();
        var cryptocurrencyAVAX = dataCryptocurrency.getCryptocurrencyAvax();

        assertThat(expected)
                .hasSizeGreaterThan(0)
                .extracting(
                        TokenDTO::getId,
                        TokenDTO::getName,
                        TokenDTO::getSymbol,
                        TokenDTO::getCoinMarketId,
                        TokenDTO::getLastUpdate
                ).containsExactly(
                        tuple(
                                cryptocurrencyBTC.getId(),
                                cryptocurrencyBTC.getName(),
                                cryptocurrencyBTC.getSymbol(),
                                cryptocurrencyBTC.getCoinMarketId(),
                                cryptocurrencyBTC.getLastUpdate()
                        ),
                        tuple(
                                cryptocurrencyETH.getId(),
                                cryptocurrencyETH.getName(),
                                cryptocurrencyETH.getSymbol(),
                                cryptocurrencyETH.getCoinMarketId(),
                                cryptocurrencyETH.getLastUpdate()
                        ),
                        tuple(
                                cryptocurrencyMATIC.getId(),
                                cryptocurrencyMATIC.getName(),
                                cryptocurrencyMATIC.getSymbol(),
                                cryptocurrencyMATIC.getCoinMarketId(),
                                cryptocurrencyMATIC.getLastUpdate()
                        ),
                        tuple(
                                cryptocurrencyAVAX.getId(),
                                cryptocurrencyAVAX.getName(),
                                cryptocurrencyAVAX.getSymbol(),
                                cryptocurrencyAVAX.getCoinMarketId(),
                                cryptocurrencyAVAX.getLastUpdate()
                        )
                );

        var priceBTC = cryptocurrencyBTC.getPrice();
        var priceETH = cryptocurrencyETH.getPrice();
        var priceMATIC = cryptocurrencyMATIC.getPrice();
        var priceAVAX = cryptocurrencyAVAX.getPrice();

        assertThat(expected).extracting(
                TokenDTO::getPrice
        ).extracting(
                TokenPriceDTO::getId,
                TokenPriceDTO::getPriceCurrent,
                TokenPriceDTO::getPercentChange1h,
                TokenPriceDTO::getPercentChange24h,
                TokenPriceDTO::getPercentChange7d,
                TokenPriceDTO::getPercentChange30d,
                TokenPriceDTO::getPercentChange60d,
                TokenPriceDTO::getPercentChange90d,
                TokenPriceDTO::getLastUpdate
        ).containsExactly(
                tuple(
                        priceBTC.getId(),
                        priceBTC.getPriceCurrent(),
                        priceBTC.getPercentChange1h(),
                        priceBTC.getPercentChange24h(),
                        priceBTC.getPercentChange7d(),
                        priceBTC.getPercentChange30d(),
                        priceBTC.getPercentChange60d(),
                        priceBTC.getPercentChange90d(),
                        priceBTC.getLastUpdate()
                ),
                tuple(
                        priceETH.getId(),
                        priceETH.getPriceCurrent(),
                        priceETH.getPercentChange1h(),
                        priceETH.getPercentChange24h(),
                        priceETH.getPercentChange7d(),
                        priceETH.getPercentChange30d(),
                        priceETH.getPercentChange60d(),
                        priceETH.getPercentChange90d(),
                        priceETH.getLastUpdate()
                ),
                tuple(
                        priceMATIC.getId(),
                        priceMATIC.getPriceCurrent(),
                        priceMATIC.getPercentChange1h(),
                        priceMATIC.getPercentChange24h(),
                        priceMATIC.getPercentChange7d(),
                        priceMATIC.getPercentChange30d(),
                        priceMATIC.getPercentChange60d(),
                        priceMATIC.getPercentChange90d(),
                        priceMATIC.getLastUpdate()
                ),
                tuple(
                        priceAVAX.getId(),
                        priceAVAX.getPriceCurrent(),
                        priceAVAX.getPercentChange1h(),
                        priceAVAX.getPercentChange24h(),
                        priceAVAX.getPercentChange7d(),
                        priceAVAX.getPercentChange30d(),
                        priceAVAX.getPercentChange60d(),
                        priceAVAX.getPercentChange90d(),
                        priceAVAX.getLastUpdate()
                )
        );
    }

    @Test
    void whenGetCryptocurrenciesByNameStatus500_thenReturnEmptyListCryptocurrencyDTO(ResponseCryptoPriceService responseCryptoPriceService) {
        mockWebServer.enqueue(
                responseCryptoPriceService.getAllCryptocurrenciesHttpStatus500()
        );

        var expected = underTest.getTokensByName(List.of("BTC", "ETH"));

        assertThat(expected).hasSize(0);
    }
}