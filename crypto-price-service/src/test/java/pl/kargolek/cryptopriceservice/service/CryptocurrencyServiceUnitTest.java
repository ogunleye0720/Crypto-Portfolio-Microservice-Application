package pl.kargolek.cryptopriceservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import pl.kargolek.cryptopriceservice.dto.client.CryptocurrencyMapDTO;
import pl.kargolek.cryptopriceservice.dto.client.MapDataDTO;
import pl.kargolek.cryptopriceservice.dto.client.PlatformMapDTO;
import pl.kargolek.cryptopriceservice.exception.CryptocurrencyNotFoundException;
import pl.kargolek.cryptopriceservice.exception.NoMatchCryptoMapException;
import pl.kargolek.cryptopriceservice.exception.NoSmartContractAddressException;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.repository.CryptocurrencyRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
class CryptocurrencyServiceUnitTest {

    private static final String PLATFORM_NAME = "platform";
    private static final String TOKEN_ADDRESS = "tokenAddress";
    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Mock
    private MarketApiClientService marketApiClientService;

    @InjectMocks
    private CryptocurrencyService underTestService;

    private Cryptocurrency cryptocurrency;
    private MapDataDTO mapDataDTO;

    @BeforeEach
    public void setup() {
        cryptocurrency = Cryptocurrency.builder()
                .id(1L)
                .name("Bitcoin")
                .symbol("BTC")
                .coinMarketId(1L)
                .platform(PLATFORM_NAME)
                .tokenAddress(TOKEN_ADDRESS)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        var cryptocurrencyMapDTO = new CryptocurrencyMapDTO()
                .setName("Bitcoin")
                .setSymbol("BTC")
                .setPlatformMapDTO(new PlatformMapDTO()
                        .setPlatformName(PLATFORM_NAME)
                        .setTokenAddress(TOKEN_ADDRESS)
                )
                .setCoinMarketId(1L);

        mapDataDTO = new MapDataDTO()
                .setData(List.of(cryptocurrencyMapDTO));
    }

    @Test
    void whenAddCryptoCurrency_thenReturnEntity() {
        when(cryptocurrencyRepository.save(cryptocurrency))
                .thenReturn(cryptocurrency);
        when(marketApiClientService.getMapCryptocurrencyInfo(anyString()))
                .thenReturn(mapDataDTO);

        var expected = underTestService.addCryptocurrency(cryptocurrency);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress,
                        Cryptocurrency::getLastUpdate)
                .containsExactly(
                        cryptocurrency.getId(),
                        cryptocurrency.getName(),
                        cryptocurrency.getSymbol(),
                        cryptocurrency.getCoinMarketId(),
                        PLATFORM_NAME,
                        TOKEN_ADDRESS,
                        cryptocurrency.getLastUpdate()
                );
    }

    @Test
    void whenAddCryptoCurrencyWithoutMarketId_thenReturnEntity() {
        cryptocurrency.setCoinMarketId(null);
        when(cryptocurrencyRepository.save(cryptocurrency))
                .thenReturn(cryptocurrency);

        when(marketApiClientService.getMapCryptocurrencyInfo(anyString()))
                .thenReturn(mapDataDTO);

        var expected = underTestService.addCryptocurrency(cryptocurrency);

        assertThat(expected)
                .extracting(Cryptocurrency::getCoinMarketId).isEqualTo(
                        cryptocurrency.getCoinMarketId()
                );
    }

    @Test
    void whenAddCryptoCurrencyWithoutPlatformTokenAddress_thenReturnEntity() {
        cryptocurrency.setPlatform(null);
        cryptocurrency.setTokenAddress(null);

        when(cryptocurrencyRepository.save(cryptocurrency))
                .thenReturn(cryptocurrency);

        when(marketApiClientService.getMapCryptocurrencyInfo(anyString()))
                .thenReturn(mapDataDTO);

        var expected = underTestService.addCryptocurrency(cryptocurrency);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress)
                .containsExactly(
                        PLATFORM_NAME,
                        TOKEN_ADDRESS
                );
    }

    @Test
    void whenAddCryptoCurrencyNoMatchNameMarketService_thenThrowExc() {
        var data = mapDataDTO.getData()
                .stream()
                .peek(cryptocurrencyMapDTO -> cryptocurrencyMapDTO.setName("Another name"))
                .toList();
        mapDataDTO.setData(data);
        when(marketApiClientService.getMapCryptocurrencyInfo(anyString()))
                .thenReturn(mapDataDTO);

     assertThatThrownBy(() -> underTestService.addCryptocurrency(cryptocurrency))
             .isInstanceOf(NoMatchCryptoMapException.class);
    }

    @Test
    void whenAddCryptoCurrencyNullMarketService_thenThrowExc() {
        when(marketApiClientService.getMapCryptocurrencyInfo(anyString()))
                .thenReturn(new MapDataDTO());

        assertThatThrownBy(() -> underTestService.addCryptocurrency(cryptocurrency))
                .isInstanceOf(NoMatchCryptoMapException.class);
    }

    @Test
    void whenGetAllCryptocurrencies_thenReturnListCryptocurrencies() {
        when(cryptocurrencyRepository.findAll())
                .thenReturn(Collections.singletonList(cryptocurrency));

        var expected = underTestService.getCryptocurrencies();

        assertThat(expected)
                .hasSize(1)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress,
                        Cryptocurrency::getLastUpdate)
                .containsExactly(
                        tuple(cryptocurrency.getId(),
                                cryptocurrency.getName(),
                                cryptocurrency.getSymbol(),
                                cryptocurrency.getCoinMarketId(),
                                cryptocurrency.getPlatform(),
                                cryptocurrency.getTokenAddress(),
                                cryptocurrency.getLastUpdate()
                        )
                );
    }

    @Test
    void whenGetAllCryptocurrenciesWhenEmpty_thenReturnListCryptocurrencies() {
        when(cryptocurrencyRepository.findAll())
                .thenReturn(List.of());

        var expected = underTestService.getCryptocurrencies();

        assertThat(expected)
                .hasSize(0);
    }

    @Test
    void whenGetCryptocurrencyById_thenReturnExpectedCryptocurrency() {
        when(cryptocurrencyRepository.findById(1L))
                .thenReturn(Optional.of(cryptocurrency));

        var expected = underTestService.getById(1L);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress,
                        Cryptocurrency::getLastUpdate)
                .containsExactly(
                        cryptocurrency.getId(),
                        cryptocurrency.getName(),
                        cryptocurrency.getSymbol(),
                        cryptocurrency.getCoinMarketId(),
                        cryptocurrency.getPlatform(),
                        cryptocurrency.getTokenAddress(),
                        cryptocurrency.getLastUpdate()
                );
    }

    @Test
    void whenGetCryptocurrencyByNotExistId_thenThrowCryptocurrencyNotFoundExc() {
        var id = 1L;
        when(cryptocurrencyRepository.findById(id))
                .thenThrow(new CryptocurrencyNotFoundException(id));

        assertThatThrownBy(() -> underTestService.getById(id))
                .isInstanceOf(CryptocurrencyNotFoundException.class)
                .hasMessage("Unable to find cryptocurrency with id: " + id);
    }

    @Test
    void whenUpdateCryptocurrency_thenShouldUpdateProperValuesOfEntity() {
        var cryptoCurrencyUpdate = Cryptocurrency.builder()
                .name("NEW_NAME")
                .symbol("NEW_SYMBOL")
                .platform("NEW_PLATFORM")
                .tokenAddress("NEW_TOKEN")
                .coinMarketId(RandomUtils.nextLong())
                .build();

        when(cryptocurrencyRepository.findById(1L))
                .thenReturn(Optional.of(cryptocurrency));
        when(cryptocurrencyRepository.save(cryptocurrency))
                .thenReturn(cryptocurrency);

        var expected = underTestService.updateCryptocurrency(1L, cryptoCurrencyUpdate);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress
                ).containsExactly(
                        cryptocurrency.getId(),
                        cryptoCurrencyUpdate.getName(),
                        cryptoCurrencyUpdate.getSymbol(),
                        cryptocurrency.getCoinMarketId(),
                        cryptoCurrencyUpdate.getPlatform(),
                        cryptoCurrencyUpdate.getTokenAddress()
                );

        assertThat(expected.getLastUpdate())
                .isAfter(cryptocurrency.getLastUpdate().minusDays(1L));
    }

    @Test
    void whenNotUpdateCryptoById_thenThrowCryptocurrencyNotFoundExc() {
        var cryptoCurrencyUpdate = Cryptocurrency.builder()
                .name("Ethereum")
                .symbol("ETH")
                .coinMarketId(RandomUtils.nextLong())
                .build();

        assertThatThrownBy(() -> underTestService.updateCryptocurrency(1L, cryptoCurrencyUpdate))
                .isInstanceOf(CryptocurrencyNotFoundException.class)
                .hasMessage("Unable to find cryptocurrency with id: 1");
    }

    @Test
    void whenDeleteCryptocurrencyById_thenDeleteByIdRepositoryMethodPerform() {
        var id = 1L;

        underTestService.deleteCryptocurrency(id);

        verify(cryptocurrencyRepository).deleteById(id);
    }

    @Test
    void whenGetCryptocurrencyByName_thenReturnCryptocurrencySuccessful() {
        var cryptoName = "Bitcoin";
        when(cryptocurrencyRepository.findByName(List.of(cryptoName)))
                .thenReturn(List.of(cryptocurrency));

        var expected = underTestService.getByName(List.of(cryptoName));

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getLastUpdate)
                .containsExactly(
                        tuple(
                                cryptocurrency.getId(),
                                cryptocurrency.getName(),
                                cryptocurrency.getSymbol(),
                                cryptocurrency.getCoinMarketId(),
                                cryptocurrency.getLastUpdate()
                        ));
    }

    @Test
    void whenNotFoundCryptocurrencyByName_thenThrowCryptocurrencyNotFoundExc() {
        var cryptoName = "Ethereum";

        when(cryptocurrencyRepository.findByName(List.of(cryptoName)))
                .thenThrow(new CryptocurrencyNotFoundException(cryptoName));

        assertThatThrownBy(() -> underTestService.getByName(List.of(cryptoName)))
                .isInstanceOf(CryptocurrencyNotFoundException.class)
                .hasMessage("Unable to find cryptocurrency with name: " + cryptoName);
    }

    @Test
    void whenGetByContractAddress_thenReturnProperEntity() {
        when(cryptocurrencyRepository.findByContractAddress(anyString()))
                .thenReturn(Optional.of(cryptocurrency));

        var expected = underTestService.getBySmartContractAddress(TOKEN_ADDRESS);

        assertThat(expected)
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPlatform,
                        Cryptocurrency::getTokenAddress,
                        Cryptocurrency::getLastUpdate
                ).containsExactly(
                        cryptocurrency.getId(),
                        cryptocurrency.getName(),
                        cryptocurrency.getSymbol(),
                        cryptocurrency.getCoinMarketId(),
                        cryptocurrency.getPlatform(),
                        cryptocurrency.getTokenAddress(),
                        cryptocurrency.getLastUpdate()
                );
    }

    @Test
    void whenGetByNoExistContractAddress_thenReturnProperEntity() {
        var no_exist_address = "NO_EXIST_CONTRACT_ADDRESS";

        when(cryptocurrencyRepository.findByContractAddress(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTestService.getBySmartContractAddress(no_exist_address))
                .isInstanceOf(NoSmartContractAddressException.class)
                .hasMessageContaining(no_exist_address);
    }

}