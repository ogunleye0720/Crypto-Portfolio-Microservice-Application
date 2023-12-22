package pl.kargolek.cryptopriceservice.repository;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import pl.kargolek.cryptopriceservice.extension.MySqlTestContainerExtension;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MySqlTestContainerExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Tag("IntegrationTest")
@ActiveProfiles("test")
class CryptocurrencyRepositoryIntegrationTest {

    @Autowired
    private CryptocurrencyRepository underTestRepository;

    private Cryptocurrency cryptocurrency;

    @BeforeEach
    public void setup() {
        cryptocurrency = Cryptocurrency.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .coinMarketId(RandomUtils.nextLong())
                .platform("platform")
                .tokenAddress("tokenAddress")
                .lastUpdate(LocalDateTime.now())
                .build();
    }

    @Test
    void whenSaveCryptocurrencyEntity_thenEntityShouldExist() {
        var crypto = underTestRepository.save(cryptocurrency);

        var expected = underTestRepository.findOne(Example.of(crypto));

        assertThat(expected)
                .get()
                .extracting(
                        Cryptocurrency::getId,
                        Cryptocurrency::getName,
                        Cryptocurrency::getSymbol,
                        Cryptocurrency::getCoinMarketId,
                        Cryptocurrency::getPrice
                ).containsExactly(
                        cryptocurrency.getId(),
                        cryptocurrency.getName(),
                        cryptocurrency.getSymbol(),
                        cryptocurrency.getCoinMarketId(),
                        cryptocurrency.getPrice()
                );
    }

    @Test
    void shouldReturnEntityById() {
        underTestRepository.save(cryptocurrency);

        var expected = underTestRepository.findById(cryptocurrency.getId());

        assertThat(expected.isPresent()).isTrue();
    }

    @Test
    void whenDeleteEntity_thenEntityShouldBeDeleted() {
        underTestRepository.save(cryptocurrency);

        underTestRepository.delete(cryptocurrency);

        assertThat(underTestRepository.findById(cryptocurrency.getId()).isPresent())
                .isFalse();
    }

    @Test
    void whenSaveCryptocurrencyWithSameCoinMarketId_thenThrowConstraintExc() {
        underTestRepository.save(cryptocurrency);
        var cryptoGiven = Cryptocurrency.builder()
                .name("Ethereum")
                .symbol("ETH")
                .coinMarketId(cryptocurrency.getCoinMarketId())
                .lastUpdate(LocalDateTime.now())
                .build();

        assertThatThrownBy(() -> underTestRepository.save(cryptoGiven))
                .hasCauseInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("cryptocurrency.UniqueCoinMarketCapId");
    }

    @Test
    void whenFindByNameCryptocurrency_thenShouldReturnListCryptocurrency() {
        underTestRepository.save(cryptocurrency);

        var expected = underTestRepository.findByName(List.of("bitcoin"));

        assertThat(expected).extracting(
                Cryptocurrency::getId,
                Cryptocurrency::getName,
                Cryptocurrency::getSymbol,
                Cryptocurrency::getCoinMarketId,
                Cryptocurrency::getPrice
        ).containsExactly(
                tuple(
                        cryptocurrency.getId(),
                        cryptocurrency.getName(),
                        cryptocurrency.getSymbol(),
                        cryptocurrency.getCoinMarketId(),
                        cryptocurrency.getPrice()
                )
        );
    }

    @Test
    void whenGetBySmartContractAddress_thenReturnProperEntityOptional() {
        underTestRepository.save(cryptocurrency);

        var expected = underTestRepository.findByContractAddress("tokenAddress");

        assertThat(expected)
                .get()
                .extracting(
                        Cryptocurrency::getTokenAddress
                ).isEqualTo(
                        cryptocurrency.getTokenAddress()
                );
    }

    @Test
    void whenGetByNotExistSmartContractAddress_thenReturnEmptyOptional() {
        underTestRepository.save(cryptocurrency);

        var expected = underTestRepository.findByContractAddress("no_exist_tokenAddress");

        assertThat(expected).isEmpty();
    }
}