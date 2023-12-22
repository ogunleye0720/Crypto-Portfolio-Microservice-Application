package pl.kargolek.cryptopriceservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pl.kargolek.cryptopriceservice.exception.*;
import pl.kargolek.cryptopriceservice.extension.MarketMockServerDispatcherExtension;
import pl.kargolek.cryptopriceservice.extension.MySqlTestContainerExtension;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;
import pl.kargolek.cryptopriceservice.repository.CryptocurrencyRepository;
import pl.kargolek.cryptopriceservice.repository.PriceRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@ExtendWith(MySqlTestContainerExtension.class)
@ExtendWith(MarketMockServerDispatcherExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IntegrationTest")
@ActiveProfiles("test")
public class CryptocurrencyServiceIntegrationTest {

    private static final long BTC_MARKET_ID = 1L;
    private static final String XEN_PLATFORM_NAME = "Ethereum";
    private static final String XEN_TOKEN_ADDRESS = "0x06450dEe7FD2Fb8E39061434BAbCFC05599a6Fb8";
    private static final String PLATFORM_USER_INPUT = "PLATFORM";
    private static final String TOKEN_ADDRESS_USER_INPUT = "TOKEN_ADDRESS";
    @Autowired
    private CryptocurrencyService underTestService;
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private PriceRepository priceRepository;

    private Cryptocurrency cryptocurrencyBTC;
    private Cryptocurrency cryptocurrencyXEN;

    @DynamicPropertySource
    static void webclientProperties(DynamicPropertyRegistry registry) {
        registry.add("api.coin.market.cap.baseUrl",
                () -> MarketMockServerDispatcherExtension.mockWebServer.url("/").toString());
    }

    @BeforeEach
    public void setup() {
        cryptocurrencyBTC = Cryptocurrency.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        cryptocurrencyXEN = Cryptocurrency.builder()
                .name("Xen Crypto")
                .symbol("XEN")
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void whenAddCryptocurrency_thenShouldSaveSuccessful() {
        var expected = underTestService.addCryptocurrency(cryptocurrencyBTC);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress
                ).containsExactly(
                        cryptocurrencyBTC.getName(),
                        cryptocurrencyBTC.getSymbol(),
                        BTC_MARKET_ID,
                        cryptocurrencyBTC.getPlatform(),
                        cryptocurrencyBTC.getTokenAddress()
                );

        assertThat(expected.getId())
                .isGreaterThan(0L);

        assertThat(expected.getLastUpdate())
                .isBefore(LocalDateTime.now(ZoneOffset.UTC));
    }

    @Test
    void whenAddCryptocurrencyPlatformTokenNeedToFetch_thenShouldSaveSuccessful() {
        var expected = underTestService.addCryptocurrency(cryptocurrencyXEN);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress
                ).containsExactly(
                        XEN_PLATFORM_NAME,
                        XEN_TOKEN_ADDRESS
                );
    }

    @Test
    void whenAddCryptocurrencyWithPlatformToken_thenShouldSaveSuccessful() {
        cryptocurrencyBTC.setTokenAddress("MY_PLATFORM");
        cryptocurrencyBTC.setTokenAddress("MY_TOKEN");

        var expected = underTestService.addCryptocurrency(cryptocurrencyBTC);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress
                ).containsExactly(
                        cryptocurrencyBTC.getPlatform(),
                        cryptocurrencyBTC.getTokenAddress()
                );
    }

    @Test
    void whenAddCryptocurrency_thenPriceEntityShouldSaveAlso() {
        var expected = underTestService.addCryptocurrency(cryptocurrencyBTC);

        assertThat(expected.getPrice())
                .extracting(
                        Price::getId,
                        Price::getPriceCurrent,
                        Price::getPercentChange1h
                ).containsExactly(
                        cryptocurrencyBTC.getPrice().getId(),
                        null,
                        null
                );
    }

    @Test
    void whenAddCryptoMarketResponse500_thenThrowMarketExc() {
        cryptocurrencyBTC.setSymbol("SERVER_500");

        assertThatThrownBy(() -> underTestService.addCryptocurrency(cryptocurrencyBTC))
                .isInstanceOf(MarketApiClientException.class)
                .hasMessageContaining("serverMessage: An internal server error occurred");
    }

    @Test
    void whenAddCryptoMarketResponse400_thenThrowNoSuchSymbolExc() {
        cryptocurrencyBTC.setSymbol("Unknown");

        assertThatThrownBy(() -> underTestService.addCryptocurrency(cryptocurrencyBTC))
                .isInstanceOf(NoSuchCryptoSymbolException.class)
                .hasMessageContaining("Unable to fetch crypto map from coin market api for provided symbol: Unknown");
    }

    @Test
    void whenAddCryptoNameNoMatchWithFetchedMapMarket_thenShouldSave() {
        cryptocurrencyBTC.setName("UNKNOWN_NAME");

        assertThatThrownBy(() -> underTestService.addCryptocurrency(cryptocurrencyBTC))
                .isInstanceOf(NoMatchCryptoMapException.class)
                .hasMessageContaining("Received coin market cap crypto name, no match with provided name:UNKNOWN_NAME");
    }

    @Test
    void whenUpdateCrypto_thenShouldUpdateSuccessful() {
        cryptocurrencyXEN.setCoinMarketId(1200L);
        var cryptoSaved = cryptocurrencyRepository.save(cryptocurrencyXEN);

        var cryptoUpdate = Cryptocurrency.builder()
                .name("NEW_NAME")
                .symbol("NEW_SYMBOL")
                .platform("NEW_PLATFORM")
                .tokenAddress("NEW_TOKEN_ADDRESS")
                .build();

        var expected = underTestService.updateCryptocurrency(cryptoSaved.getId(), cryptoUpdate);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress
                ).containsExactly(
                        cryptoSaved.getId(),
                        cryptoUpdate.getName(),
                        cryptoUpdate.getSymbol(),
                        cryptoUpdate.getPlatform(),
                        cryptoUpdate.getTokenAddress()
                );
    }

    @Test
    void whenUpdateByIncorrectId_thenShouldThrowAnException() {
        var crypto = Cryptocurrency.builder()
                .name(XEN_PLATFORM_NAME)
                .symbol("ETH")
                .coinMarketId(BTC_MARKET_ID)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        var cryptoToUpdate = cryptocurrencyRepository.save(crypto);

        var cryptoUpdate = Cryptocurrency.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .coinMarketId(2L)
                .build();

        var searchedId = cryptoToUpdate.getId() + 10L;

        assertThatThrownBy(() ->
                underTestService.updateCryptocurrency(
                        searchedId,
                        cryptoUpdate))
                .isInstanceOf(CryptocurrencyNotFoundException.class)
                .hasMessageContaining("id: " + searchedId);
    }

    @Test
    void whenDeleteById_thenShouldDeleteCryptocurrency() {
        var crypto = Cryptocurrency.builder()
                .name(XEN_PLATFORM_NAME)
                .symbol("ETH")
                .coinMarketId(BTC_MARKET_ID)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        var id = cryptocurrencyRepository.save(crypto).getId();

        underTestService.deleteCryptocurrency(id);

        assertThat(cryptocurrencyRepository.findAll())
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName
                ).doesNotContain(
                        tuple(id, XEN_PLATFORM_NAME)
                );
    }

    @Test
    void whenDeleteByIdNotExisted_thenThrowEmptyResultDataAccessExc() {
        var crypto = Cryptocurrency.builder()
                .name(XEN_PLATFORM_NAME)
                .symbol("ETH")
                .coinMarketId(BTC_MARKET_ID)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        var id = cryptocurrencyRepository.save(crypto);

        assertThatThrownBy(() -> underTestService.deleteCryptocurrency(100L))
                .isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessageContaining("No class")
                .hasMessageContaining("entity with id 100 exists");
    }

    @Test
    void whenFindByName_thenReturnListWithSearchCrypto() {
        cryptocurrencyXEN.setPlatform(PLATFORM_USER_INPUT);
        cryptocurrencyXEN.setTokenAddress(TOKEN_ADDRESS_USER_INPUT);
        cryptocurrencyXEN.setCoinMarketId(1000L);
        var cryptoSaved = cryptocurrencyRepository.save(cryptocurrencyXEN);

        var expected = underTestService.getByName(List.of(cryptoSaved.getName()));

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress
                ).containsExactly(
                        tuple(
                                cryptoSaved.getId(),
                                cryptoSaved.getName(),
                                cryptoSaved.getSymbol(),
                                cryptoSaved.getCoinMarketId(),
                                cryptoSaved.getPlatform(),
                                cryptoSaved.getTokenAddress()
                        )
                );

        assertThat(expected.get(0).getLastUpdate())
                .isEqualToIgnoringNanos(cryptoSaved.getLastUpdate());
    }

    @Test
    void whenFindByNameUnknownName_thenReturnListEmptyList() {
        cryptocurrencyXEN.setPlatform(PLATFORM_USER_INPUT);
        cryptocurrencyXEN.setTokenAddress(TOKEN_ADDRESS_USER_INPUT);
        cryptocurrencyXEN.setCoinMarketId(1000L);
        cryptocurrencyRepository.save(cryptocurrencyXEN);

        var expected = underTestService.getByName(List.of("Unknown_name"));

        assertThat(expected)
                .hasSize(0);
    }

    @Test
    void whenGetCryptocurrencyByContractAddress_theReturnProperEntity(){
        cryptocurrencyBTC.setCoinMarketId(1L);
        cryptocurrencyBTC.setPlatform(PLATFORM_USER_INPUT);
        cryptocurrencyBTC.setTokenAddress(TOKEN_ADDRESS_USER_INPUT);

        cryptocurrencyRepository.save(cryptocurrencyBTC);

        var expected = underTestService.getBySmartContractAddress(TOKEN_ADDRESS_USER_INPUT);

        assertThat(expected)
                .extracting(Cryptocurrency::getTokenAddress)
                .isEqualTo(TOKEN_ADDRESS_USER_INPUT);
    }

    @Test
    void whenGetCryptocurrencyByNoExistContractAddress_theThrowNoSmartContractAddressExc(){
        cryptocurrencyBTC.setCoinMarketId(1L);
        cryptocurrencyBTC.setPlatform(PLATFORM_USER_INPUT);
        cryptocurrencyBTC.setTokenAddress(TOKEN_ADDRESS_USER_INPUT);

        cryptocurrencyRepository.save(cryptocurrencyBTC);

        var unknown_address = "UNKNOWN_TOKEN_ADDRESS";

        assertThatThrownBy(() -> underTestService.getBySmartContractAddress(unknown_address))
                .isInstanceOf(NoSmartContractAddressException.class)
                .hasMessageContaining(unknown_address);
    }
}
