package pl.kargolek.cryptopriceservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kargolek.cryptopriceservice.dto.client.CryptocurrencyMapDTO;
import pl.kargolek.cryptopriceservice.exception.CryptocurrencyNotFoundException;
import pl.kargolek.cryptopriceservice.exception.NoMatchCryptoMapException;
import pl.kargolek.cryptopriceservice.exception.NoSmartContractAddressException;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;
import pl.kargolek.cryptopriceservice.repository.CryptocurrencyRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CryptocurrencyService {

    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final MarketApiClientService marketApiClientService;

    public Cryptocurrency addCryptocurrency(Cryptocurrency cryptocurrency) {

        CryptocurrencyMapDTO cryptocurrencyMapDTO = getCryptocurrencyInfo(cryptocurrency);

        var isPlatformNoExist = cryptocurrency.getPlatform() == null;
        var isTokenAddressNoExist = cryptocurrency.getTokenAddress() == null;

        var platformDTO = cryptocurrencyMapDTO.getPlatformMapDTO();

        if (isPlatformNoExist && platformDTO != null)
            cryptocurrency.setPlatform(platformDTO.getPlatformName());

        if (isTokenAddressNoExist && platformDTO != null)
            cryptocurrency.setTokenAddress(platformDTO.getTokenAddress());

        cryptocurrency.setCoinMarketId(cryptocurrencyMapDTO.getCoinMarketId());

        var price = Price.builder()
                .cryptocurrency(cryptocurrency)
                .lastUpdate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        cryptocurrency.setPrice(price);
        cryptocurrency.setLastUpdate(LocalDateTime.now(ZoneOffset.UTC));

        log.info(String.format("Adding new cryptocurrency name:%s, symbol:%s, coinMarketId:%s, platform:%s, tokenAddress:%s",
                cryptocurrency.getName(),
                cryptocurrency.getSymbol(),
                cryptocurrency.getCoinMarketId(),
                cryptocurrency.getPlatform(),
                cryptocurrency.getTokenAddress()));
        return cryptocurrencyRepository.save(cryptocurrency);
    }

    public List<Cryptocurrency> getCryptocurrencies() {
        log.info("Finding all cryptocurrencies");
        return cryptocurrencyRepository.findAll();
    }

    public Cryptocurrency getById(Long id) {
        log.info(String.format("Finding cryptocurrency by id: %d", id));
        return cryptocurrencyRepository.findById(id)
                .orElseThrow(() -> new CryptocurrencyNotFoundException(id));
    }

    public Cryptocurrency getBySmartContractAddress(String contractAddress){
        return cryptocurrencyRepository.findByContractAddress(contractAddress)
                .orElseThrow(() -> new NoSmartContractAddressException(contractAddress));
    }

    public Cryptocurrency updateCryptocurrency(Long id, Cryptocurrency cryptocurrency) {
        var cryptocurrencyToUpdate = cryptocurrencyRepository.findById(id)
                .orElseThrow(() -> new CryptocurrencyNotFoundException(id));

        cryptocurrencyToUpdate.setName(cryptocurrency.getName());
        cryptocurrencyToUpdate.setSymbol(cryptocurrency.getSymbol());
        cryptocurrencyToUpdate.setPlatform(cryptocurrency.getPlatform());
        cryptocurrencyToUpdate.setTokenAddress(cryptocurrency.getTokenAddress());
        cryptocurrencyToUpdate.setLastUpdate(LocalDateTime.now(ZoneOffset.UTC));

        log.info(String.format("Updating cryptocurrency with id:%d, name:%s, symbol:%s, platform:%s, tokenAddress:%s",
                cryptocurrencyToUpdate.getId(),
                cryptocurrency.getName(),
                cryptocurrency.getSymbol(),
                cryptocurrency.getPlatform(),
                cryptocurrency.getTokenAddress()));
        return cryptocurrencyRepository.save(cryptocurrencyToUpdate);
    }

    public void deleteCryptocurrency(Long id) {
        log.info(String.format("Deleting cryptocurrency id: %d", id));
        cryptocurrencyRepository.deleteById(id);
    }

    public List<Cryptocurrency> getByName(List<String> name) {
        log.info(String.format("Finding cryptocurrencies by name: %s", name));
        return cryptocurrencyRepository.findByName(name);
    }

    private CryptocurrencyMapDTO getCryptocurrencyInfo(Cryptocurrency cryptocurrency) {
        return marketApiClientService.getMapCryptocurrencyInfo(cryptocurrency.getSymbol()).getData()
                .stream()
                .filter(cryptocurrencyMapDTO -> cryptocurrencyMapDTO
                        .getName()
                        .equalsIgnoreCase(cryptocurrency.getName()))
                .findFirst()
                .orElseThrow(() -> new NoMatchCryptoMapException(cryptocurrency.getName()));
    }
}
