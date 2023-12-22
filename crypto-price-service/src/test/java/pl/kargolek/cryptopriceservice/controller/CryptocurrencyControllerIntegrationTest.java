package pl.kargolek.cryptopriceservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import pl.kargolek.cryptopriceservice.dto.controller.CryptocurrencyPostDTO;
import pl.kargolek.cryptopriceservice.dto.model.CryptocurrencyDTO;
import pl.kargolek.cryptopriceservice.dto.model.PlatformDTO;
import pl.kargolek.cryptopriceservice.dto.model.PriceDTO;
import pl.kargolek.cryptopriceservice.exception.JsonApiError;
import pl.kargolek.cryptopriceservice.extension.MarketMockServerDispatcherExtension;
import pl.kargolek.cryptopriceservice.extension.MySqlTestContainerExtension;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.repository.CryptocurrencyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author Karol Kuta-Orlowicz
 */

@ExtendWith(MySqlTestContainerExtension.class)
@ExtendWith(MarketMockServerDispatcherExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IntegrationTest")
@ActiveProfiles("test")
public class CryptocurrencyControllerIntegrationTest {

    private static final String BTC_NAME = "Bitcoin";
    private static final String BTC_SYMBOL = "BTC";
    private static final long BTC_MARKET_ID = 1L;
    private static final String ETH_NAME = "Ethereum";
    private static final String ETH_SYMBOL = "ETH";
    private static final long ETH_MARKET_ID = 1027L;
    private static final BigDecimal BTC_PRICE = new BigDecimal("20000.50").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal BTC_PERCENT_1H = new BigDecimal("0.5").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal BTC_PERCENT_24H = new BigDecimal("-1.0").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal BTC_PERCENT_7D = new BigDecimal("1.5").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal BTC_PERCENT_30D = new BigDecimal("-2.0").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal BTC_PERCENT_60D = new BigDecimal("2.5").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal BTC_PERCENT_90D = new BigDecimal("-3.0").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal ETH_PRICE = new BigDecimal("1800.50").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal ETH_PERCENT_1H = new BigDecimal("4.5").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal ETH_PERCENT_24H = new BigDecimal("-5.0").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal ETH_PERCENT_7D = new BigDecimal("5.5").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal ETH_PERCENT_30D = new BigDecimal("-6.0").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal ETH_PERCENT_60D = new BigDecimal("6.5").setScale(12, RoundingMode.HALF_UP);
    private static final BigDecimal ETH_PERCENT_90D = new BigDecimal("-7.0").setScale(12, RoundingMode.HALF_UP);
    private static final String POLYGON_NAME = "Polygon";
    private static final String POLYGON_SYMBOL = "MATIC";
    private static final long POLYGON_MARKET_ID = 3890L;
    private static final String API_BASE_URL = "/api/v1/cryptocurrency";
    private static final String PLATFORM_BTC = "platformBTC";
    private static final String TOKEN_ADDRESS_BTC = "tokenAddressBTC";
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    private Long bitcoinID;
    private Long ethereumID;

    @DynamicPropertySource
    static void webclientProperties(DynamicPropertyRegistry registry) {
        registry.add("api.coin.market.cap.baseUrl",
                () -> MarketMockServerDispatcherExtension.mockWebServer.url("/").toString());
    }

    @BeforeEach
    public void setup() {
        var cryptos = cryptocurrencyRepository.findAll();

        bitcoinID = cryptos.stream()
                .filter(cryptocurrency -> cryptocurrency.getName().equalsIgnoreCase(BTC_NAME))
                .map(Cryptocurrency::getId)
                .findFirst()
                .orElse(0L);

        ethereumID = cryptos.stream()
                .filter(cryptocurrency -> cryptocurrency.getName().equalsIgnoreCase(ETH_NAME))
                .map(Cryptocurrency::getId)
                .findFirst()
                .orElse(0L);
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptos_thenReturnBodyWithProperData() {
        var responseEntity = template.getForEntity(API_BASE_URL, CryptocurrencyDTO[].class);

        var cryptocurrencies = stream(requireNonNull(responseEntity.getBody())).toList();
        var prices = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .toList();
        var cryptoIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getId)
                .toList();
        var priceIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getId)
                .toList();

        var cryptosDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getLastUpdate)
                .toList();

        var pricesDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getLastUpdate)
                .toList();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(cryptoIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(cryptocurrencies).extracting(
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                tuple(
                        BTC_NAME,
                        BTC_SYMBOL,
                        BTC_MARKET_ID
                ),
                tuple(
                        ETH_NAME,
                        ETH_SYMBOL,
                        ETH_MARKET_ID
                )
        );

        assertThat(cryptosDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));

        assertThat(priceIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(prices).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                tuple(
                        BTC_PRICE,
                        BTC_PERCENT_1H,
                        BTC_PERCENT_24H,
                        BTC_PERCENT_7D,
                        BTC_PERCENT_30D,
                        BTC_PERCENT_60D,
                        BTC_PERCENT_90D),
                tuple(
                        ETH_PRICE,
                        ETH_PERCENT_1H,
                        ETH_PERCENT_24H,
                        ETH_PERCENT_7D,
                        ETH_PERCENT_30D,
                        ETH_PERCENT_60D,
                        ETH_PERCENT_90D)
        );

        assertThat(pricesDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));
    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenGetCryptosAndDBEmpty_thenReturnEmptyBody() {
        var responseEntity = template.getForEntity(API_BASE_URL, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("[]");
    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenGetCryptosByQueryNamesAndDBEmpty_thenReturnEmptyBody() {
        var responseEntity = template.getForEntity(API_BASE_URL + "?name=Bitcoin,Ethereum", String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("[]");
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptosByQueryNames_thenReturnBodyWithProperData() {
        var responseEntity = template.getForEntity(API_BASE_URL + "?name=Bitcoin,Ethereum", CryptocurrencyDTO[].class);

        var cryptocurrencies = stream(requireNonNull(responseEntity.getBody())).toList();
        var prices = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .toList();
        var cryptoIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getId)
                .toList();
        var priceIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getId)
                .toList();

        var cryptosDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getLastUpdate)
                .toList();

        var pricesDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getLastUpdate)
                .toList();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(cryptoIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(cryptocurrencies).extracting(
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                tuple(
                        BTC_NAME,
                        BTC_SYMBOL,
                        BTC_MARKET_ID
                ),
                tuple(
                        ETH_NAME,
                        ETH_SYMBOL,
                        ETH_MARKET_ID
                )
        );

        assertThat(cryptosDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));

        assertThat(priceIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(prices).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                tuple(
                        BTC_PRICE,
                        BTC_PERCENT_1H,
                        BTC_PERCENT_24H,
                        BTC_PERCENT_7D,
                        BTC_PERCENT_30D,
                        BTC_PERCENT_60D,
                        BTC_PERCENT_90D),
                tuple(
                        ETH_PRICE,
                        ETH_PERCENT_1H,
                        ETH_PERCENT_24H,
                        ETH_PERCENT_7D,
                        ETH_PERCENT_30D,
                        ETH_PERCENT_60D,
                        ETH_PERCENT_90D)
        );

        assertThat(pricesDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptosByNotExistQueryNames_thenReturnBodyWithProperData() {
        var responseEntity = template.getForEntity(API_BASE_URL + "?name=NotExist,NotExist2", String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("[]");
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptosBySecondExistQueryName_thenReturnBodyWithOneCryptoData() {
        var responseEntity = template.getForEntity(API_BASE_URL + "?name=NotExist,Ethereum", CryptocurrencyDTO[].class);

        var cryptocurrencies = stream(requireNonNull(responseEntity.getBody())).toList();
        var prices = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .toList();
        var cryptoIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getId)
                .toList();
        var priceIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getId)
                .toList();

        var cryptosDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getLastUpdate)
                .toList();

        var pricesDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getLastUpdate)
                .toList();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(cryptoIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(cryptocurrencies).extracting(
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                tuple(
                        ETH_NAME,
                        ETH_SYMBOL,
                        ETH_MARKET_ID
                )
        );

        assertThat(cryptosDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));

        assertThat(priceIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(prices).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                tuple(
                        ETH_PRICE,
                        ETH_PERCENT_1H,
                        ETH_PERCENT_24H,
                        ETH_PERCENT_7D,
                        ETH_PERCENT_30D,
                        ETH_PERCENT_60D,
                        ETH_PERCENT_90D)
        );

        assertThat(pricesDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptosByFirstExistQueryName_thenReturnBodyOneCryptoData() {
        var responseEntity = template.getForEntity(API_BASE_URL + "?name=Bitcoin,NotExist", CryptocurrencyDTO[].class);

        var cryptocurrencies = stream(requireNonNull(responseEntity.getBody())).toList();
        var prices = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .toList();
        var cryptoIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getId)
                .toList();
        var priceIds = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getId)
                .toList();

        var cryptosDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getLastUpdate)
                .toList();

        var pricesDateTime = cryptocurrencies.stream()
                .map(CryptocurrencyDTO::getPriceDTO)
                .map(PriceDTO::getLastUpdate)
                .toList();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(cryptoIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(cryptocurrencies).extracting(
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                tuple(
                        BTC_NAME,
                        BTC_SYMBOL,
                        BTC_MARKET_ID
                )
        );

        assertThat(cryptosDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));

        assertThat(priceIds)
                .allMatch(aLong -> aLong > 0L);

        assertThat(prices).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                tuple(
                        BTC_PRICE,
                        BTC_PERCENT_1H,
                        BTC_PERCENT_24H,
                        BTC_PERCENT_7D,
                        BTC_PERCENT_30D,
                        BTC_PERCENT_60D,
                        BTC_PERCENT_90D)
        );

        assertThat(pricesDateTime)
                .allMatch(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptosByEmptyQueryName_thenReturnEmptyBody() {
        var responseEntity = template.getForEntity(API_BASE_URL + "?name=", String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("[]");
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptoByID_thenReturnBodyWithCryptoData() {
        var responseEntity = template.getForEntity(API_BASE_URL + "/" + bitcoinID,
                CryptocurrencyDTO.class);

        var crypto = Objects.requireNonNull(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(crypto).extracting(
                CryptocurrencyDTO::getId,
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                bitcoinID,
                BTC_NAME,
                BTC_SYMBOL,
                BTC_MARKET_ID
        );

        assertThat(crypto.getLastUpdate())
                .isBefore(LocalDateTime.now());

        assertThat(crypto.getPriceDTO()).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                BTC_PRICE,
                BTC_PERCENT_1H,
                BTC_PERCENT_24H,
                BTC_PERCENT_7D,
                BTC_PERCENT_30D,
                BTC_PERCENT_60D,
                BTC_PERCENT_90D
        );

        assertThat(crypto.getPriceDTO().getId())
                .isGreaterThan(0L);

        assertThat(crypto.getPriceDTO().getLastUpdate())
                .isBefore(LocalDateTime.now());
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptoByNotExistID_thenReturnStatus404NotFound() {
        var responseEntity = template.getForEntity(API_BASE_URL + "/1234567890",
                JsonApiError.class);

        var crypto = Objects.requireNonNull(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(crypto).extracting(
                JsonApiError::getStatus,
                JsonApiError::getMessage
        ).containsExactly(
                HttpStatus.NOT_FOUND,
                "Unable to find cryptocurrency with id: 1234567890"
        );
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenDeleteCryptoByID_thenReturnStatus200() {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        var responseEntity = template.exchange(API_BASE_URL + "/" + bitcoinID,
                HttpMethod.DELETE,
                entity,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenDeleteCryptoByNoExistID_thenReturnStatus404() {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        var responseEntity = template.exchange(API_BASE_URL + "/1234567",
                HttpMethod.DELETE,
                entity,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenPostCryptocurrencyNameSymbolMarketID_thenReturnStatus200AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName(POLYGON_NAME)
                .setSymbol(POLYGON_SYMBOL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange("/api/v1/cryptocurrency/",
                HttpMethod.POST,
                entity,
                CryptocurrencyDTO.class);

        var crypto = Objects.requireNonNull(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(crypto).extracting(
                CryptocurrencyDTO::getId,
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                (ethereumID + 1L),
                POLYGON_NAME,
                POLYGON_SYMBOL,
                POLYGON_MARKET_ID
        );

        assertThat(crypto.getLastUpdate())
                .isBefore(LocalDateTime.now());

        assertThat(crypto.getPriceDTO()).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertThat(crypto.getPriceDTO().getId())
                .isGreaterThan(0L);

        assertThat(crypto.getPriceDTO().getLastUpdate())
                .isBefore(LocalDateTime.now());

    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenPostCryptocurrencyNameSymbol_thenReturnStatus200AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName(POLYGON_NAME)
                .setSymbol(POLYGON_SYMBOL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL,
                HttpMethod.POST,
                entity,
                CryptocurrencyDTO.class);

        var crypto = Objects.requireNonNull(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        assertThat(crypto.getId())
                .isGreaterThan(0L);

        assertThat(crypto).extracting(
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                POLYGON_NAME,
                POLYGON_SYMBOL,
                POLYGON_MARKET_ID
        );

        assertThat(crypto.getLastUpdate())
                .isBefore(LocalDateTime.now());

        assertThat(crypto.getPriceDTO()).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertThat(crypto.getPriceDTO().getId())
                .isGreaterThan(0L);

        assertThat(crypto.getPriceDTO().getLastUpdate())
                .isBefore(LocalDateTime.now());
    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenPostCryptocurrencyTooShortName_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName("a")
                .setSymbol(POLYGON_SYMBOL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL,
                HttpMethod.POST,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody()).extracting(
                JsonApiError::getStatus,
                JsonApiError::getMessage,
                JsonApiError::getErrors
        ).containsExactly(
                HttpStatus.BAD_REQUEST,
                "Method argument are not valid",
                List.of("Name length exceeds range [2,100]")
        );
    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenPostCryptocurrencyTooShortSymbol_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName(POLYGON_NAME)
                .setSymbol("a");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL,
                HttpMethod.POST,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody()).extracting(
                JsonApiError::getStatus,
                JsonApiError::getMessage
        ).containsExactly(
                HttpStatus.BAD_REQUEST,
                "Method argument are not valid"
        );
    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenPostCryptocurrencyEmptyName_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName("")
                .setSymbol(POLYGON_NAME);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL,
                HttpMethod.POST,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody()).extracting(
                JsonApiError::getStatus,
                JsonApiError::getMessage
        ).containsExactly(
                HttpStatus.BAD_REQUEST,
                "Method argument are not valid"
        );

        assertThat(requireNonNull(responseEntity.getBody()).getErrors())
                .anyMatch(error -> error.equalsIgnoreCase("Name cannot be an empty"))
                .anyMatch(error -> error.equalsIgnoreCase("Name length exceeds range [2,100]"));
    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenPostCryptocurrencyEmptySymbol_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName(POLYGON_NAME)
                .setSymbol("");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL,
                HttpMethod.POST,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody()).extracting(
                JsonApiError::getStatus,
                JsonApiError::getMessage
        ).containsExactly(
                HttpStatus.BAD_REQUEST,
                "Method argument are not valid"
        );

        assertThat(requireNonNull(responseEntity.getBody()).getErrors())
                .anyMatch(error -> error.equalsIgnoreCase("Symbol cannot be an empty"))
                .anyMatch(error -> error.equalsIgnoreCase("Symbol length exceeds range [2,20]"));
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenPostCryptocurrencyDuplicateName_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName(BTC_NAME)
                .setSymbol(BTC_SYMBOL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL,
                HttpMethod.POST,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody()).extracting(
                JsonApiError::getStatus,
                JsonApiError::getMessage
        ).containsExactly(
                HttpStatus.BAD_REQUEST,
                "Duplicate entry '1' for key 'cryptocurrency.UniqueCoinMarketCapId'"
        );
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenPostCryptocurrencyDuplicateSymbol_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName("NEW_NAME")
                .setSymbol(BTC_SYMBOL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL,
                HttpMethod.POST,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody()).extracting(
                JsonApiError::getStatus,
                JsonApiError::getMessage
        ).containsExactly(
                HttpStatus.BAD_REQUEST,
                "Received coin market cap crypto name, no match with provided name:NEW_NAME"
        );
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenPutCryptocurrency_thenReturnStatus200AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName("NEW_NAME")
                .setSymbol("NEW_SYMBOL");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL + "/" + bitcoinID,
                HttpMethod.PUT,
                entity,
                CryptocurrencyDTO.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        var cryptocurrency = responseEntity.getBody();

        assertThat(cryptocurrency).extracting(
                CryptocurrencyDTO::getId,
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                bitcoinID,
                "NEW_NAME",
                "NEW_SYMBOL",
                1L
        );

        assertThat(requireNonNull(cryptocurrency).getLastUpdate())
                .isBefore(LocalDateTime.now());

        assertThat(requireNonNull(cryptocurrency).getPriceDTO()).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                BTC_PRICE,
                BTC_PERCENT_1H,
                BTC_PERCENT_24H,
                BTC_PERCENT_7D,
                BTC_PERCENT_30D,
                BTC_PERCENT_60D,
                BTC_PERCENT_90D
        );

        assertThat(cryptocurrency.getPriceDTO().getId())
                .isGreaterThan(0L);

        assertThat(cryptocurrency.getPriceDTO().getLastUpdate())
                .isBefore(LocalDateTime.now());
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenPutCryptocurrencyNotExistID_thenReturnStatus404AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName("NEW_NAME")
                .setSymbol("NEW_SYMBOL");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL + "/123456",
                HttpMethod.PUT,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(responseEntity.getBody())
                .extracting(
                        JsonApiError::getStatus,
                        JsonApiError::getMessage
                ).containsExactly(
                        HttpStatus.NOT_FOUND,
                        "Unable to find cryptocurrency with id: 123456"
                );
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenPutCryptocurrencyNoUniqueName_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName(ETH_NAME)
                .setSymbol("NEW_SYMBOL");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL + "/" + bitcoinID,
                HttpMethod.PUT,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody())
                .extracting(
                        JsonApiError::getStatus,
                        JsonApiError::getMessage
                ).containsExactly(
                        HttpStatus.BAD_REQUEST,
                        "Duplicate entry 'Ethereum' for key 'cryptocurrency.UniqueName'"
                );
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenPutCryptocurrencyNoUniqueSymbol_thenReturnStatus400AndBody() {
        var body = new CryptocurrencyPostDTO()
                .setName("NEW NAME")
                .setSymbol(ETH_SYMBOL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CryptocurrencyPostDTO> entity = new HttpEntity<>(body, headers);
        var responseEntity = template.exchange(API_BASE_URL + "/" + bitcoinID,
                HttpMethod.PUT,
                entity,
                JsonApiError.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(responseEntity.getBody())
                .extracting(
                        JsonApiError::getStatus,
                        JsonApiError::getMessage
                ).containsExactly(
                        HttpStatus.BAD_REQUEST,
                        "Duplicate entry 'ETH' for key 'cryptocurrency.UniqueSymbol'"
                );
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptocurrencyByContractAddress_thenReturn200AndJsonBody() {
        var responseEntity = template.getForEntity(API_BASE_URL + "/contract-address/" + TOKEN_ADDRESS_BTC,
                CryptocurrencyDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        var crypto = requireNonNull(responseEntity.getBody());

        assertThat(crypto).extracting(
                CryptocurrencyDTO::getId,
                CryptocurrencyDTO::getName,
                CryptocurrencyDTO::getSymbol,
                CryptocurrencyDTO::getCoinMarketId
        ).containsExactly(
                bitcoinID,
                BTC_NAME,
                BTC_SYMBOL,
                BTC_MARKET_ID
        );

        assertThat(crypto.getLastUpdate())
                .isBefore(LocalDateTime.now());

        assertThat(crypto.getPriceDTO()).extracting(
                PriceDTO::getPriceCurrent,
                PriceDTO::getPercentChange1h,
                PriceDTO::getPercentChange24h,
                PriceDTO::getPercentChange7d,
                PriceDTO::getPercentChange30d,
                PriceDTO::getPercentChange60d,
                PriceDTO::getPercentChange90d
        ).containsExactly(
                BTC_PRICE,
                BTC_PERCENT_1H,
                BTC_PERCENT_24H,
                BTC_PERCENT_7D,
                BTC_PERCENT_30D,
                BTC_PERCENT_60D,
                BTC_PERCENT_90D
        );

        assertThat(crypto.getPlatformDTO())
                .extracting(
                        PlatformDTO::getPlatform,
                        PlatformDTO::getTokenAddress
                ).containsExactly(
                        PLATFORM_BTC,
                        TOKEN_ADDRESS_BTC
                );
    }

    @Test
    @Sql({"/delete_data.sql", "/insert_data.sql"})
    void whenGetCryptocurrencyByNotExistContractAddress_thenReturn404AndJsonBody() {
        var responseEntity = template.getForEntity(API_BASE_URL + "/contract-address/1234",
                JsonApiError.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(responseEntity.getBody())
                .extracting(
                        JsonApiError::getStatus,
                        JsonApiError::getMessage
                ).containsExactly(
                        HttpStatus.NOT_FOUND,
                        "Provided contract address is not exist, contractAddress: 1234"
                );
    }

    @Test
    @Sql({"/delete_data.sql"})
    void whenGetCryptocurrencyByContractAddressEmptyDB_thenReturn404AndJsonBody() {
        var responseEntity = template.getForEntity(API_BASE_URL + "/contract-address/1234",
                JsonApiError.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(responseEntity.getBody())
                .extracting(
                        JsonApiError::getStatus,
                        JsonApiError::getMessage
                ).containsExactly(
                        HttpStatus.NOT_FOUND,
                        "Provided contract address is not exist, contractAddress: 1234"
                );
    }
}
