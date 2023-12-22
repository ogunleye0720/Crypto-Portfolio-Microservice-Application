package pl.kargolek.cryptopriceservice;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kargolek.cryptopriceservice.controller.CryptocurrencyController;
import pl.kargolek.cryptopriceservice.extension.MySqlTestContainerExtension;
import pl.kargolek.cryptopriceservice.repository.CryptocurrencyRepository;
import pl.kargolek.cryptopriceservice.repository.PriceRepository;
import pl.kargolek.cryptopriceservice.service.CryptocurrencyService;
import pl.kargolek.cryptopriceservice.service.PriceService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MySqlTestContainerExtension.class)
@Tag("SmokeTest")
class SmokeAppTest {

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private CryptocurrencyController cryptocurrencyController;

    @Test
    void contextLoad() {
        assertThat(cryptocurrencyRepository).isNotNull();
        assertThat(priceRepository).isNotNull();
        assertThat(cryptocurrencyService).isNotNull();
        assertThat(priceService).isNotNull();
        assertThat(cryptocurrencyController).isNotNull();
    }

}
