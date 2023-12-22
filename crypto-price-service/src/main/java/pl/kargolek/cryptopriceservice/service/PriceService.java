package pl.kargolek.cryptopriceservice.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kargolek.cryptopriceservice.exception.PriceNotFoundException;
import pl.kargolek.cryptopriceservice.model.Price;
import pl.kargolek.cryptopriceservice.repository.PriceRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service
@Slf4j
@AllArgsConstructor
public class PriceService {

    private PriceRepository priceRepository;

    public List<Price> updatePrices(List<Price> prices) {
        var isPriceNotExist = prices.stream()
                .anyMatch(price -> priceRepository.findById(price.getId()).isEmpty());
        if (isPriceNotExist)
            throw new PriceNotFoundException("Unable to find price entity for a new price");
        var pricesToUpdate = prices.stream()
                .peek(price -> price.setLastUpdate(LocalDateTime.now(ZoneOffset.UTC)))
                .peek(price -> log.info("Updating price id:{}, new price: {}", price.getId(), price.getPriceCurrent()))
                .toList();
        return priceRepository.saveAll(pricesToUpdate);
    }
}
