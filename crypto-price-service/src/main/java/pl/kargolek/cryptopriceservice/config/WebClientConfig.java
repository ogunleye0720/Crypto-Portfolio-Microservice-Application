package pl.kargolek.cryptopriceservice.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan({"pl.kargolek.cryptopriceservice"})
@EntityScan(basePackages = "pl.kargolek.cryptopriceservice")
@EnableJpaRepositories(basePackages = "pl.kargolek.cryptopriceservice")
@EnableScheduling
public class WebClientConfig {
    @Value("${api.coin.market.cap.baseUrl}")
    private String baseUrl;
    @Value("${api.coin.market.cap.key.header.name}")
    private String headerKeyName;
    @Value("${api.coin.market.cap.key.header.value}")
    private String headerKeyValue;

    @Bean
    public WebClient getCoinMarketCapWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
                    httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "deflate, gzip");
                    httpHeaders.add(headerKeyName, headerKeyValue);
                })
                .build();
    }
}
