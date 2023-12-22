package pl.kargolek.walletservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kargolek.walletservice.exception.ExternalServiceCallException;
import pl.kargolek.walletservice.validation.impl.RequestBodyValidatorImpl;
import reactor.util.retry.RetryBackoffSpec;
import reactor.util.retry.RetrySpec;

import java.time.Duration;
import java.util.Map;

/**
 * @author Karol Kuta-Orlowicz
 */
@Configuration
public class WebClientConfig {

    @Value("${api.etherscan.baseUrl}")
    private String etherscanURL;

    @Value("${api.etherscan.apiKey}")
    String etherscanApiKey;

    @Value("${api.polygonscan.baseUrl}")
    private String polygonURL;

    @Value("${api.polygonscan.apiKey}")
    String polygonscanApiKey;

    @Value("${api.snowtrace.baseUrl}")
    private String avalancheURL;

    @Value("${api.snowtrace.apiKey}")
    String avalancheApiKey;

    @Value("${api.etherscan.maxRetryAttempts}")
    private String maxRetryAttempts;

    @Value("${api.etherscan.fixedDelayMillis}")
    private String fixedDelayMillis;

    @Value("${api.etherscan.validateBodyFieldName}")
    private String validateFieldName;

    @Value("${api.etherscan.validationValue}")
    private String validationValue;

    @Bean("etherscanWebClient")
    public WebClient etherscanWebClient() {
        return WebClient.builder()
                .baseUrl(etherscanURL)
                .defaultUriVariables(Map.of("apiKey", this.etherscanApiKey))
                .build();
    }

    @Bean("polygonscanWebClient")
    public WebClient polygonWebClient() {
        return WebClient.builder()
                .baseUrl(polygonURL)
                .defaultUriVariables(Map.of("apiKey", this.polygonscanApiKey))
                .build();
    }

    @Bean("avalanchescanWebClient")
    public WebClient avalancheWebClient() {
        return WebClient.builder()
                .baseUrl(avalancheURL)
                .defaultUriVariables(Map.of("apiKey", this.avalancheApiKey))
                .build();
    }

    @Bean("etherscanRetrySpec")
    public RetryBackoffSpec etherscanRetrySpec(){
        return RetrySpec.fixedDelay(Long.parseLong(maxRetryAttempts), Duration.ofMillis(Long.parseLong(fixedDelayMillis)))
                .filter(e -> e instanceof RuntimeException)
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) ->
                        new ExternalServiceCallException("Call external service reached out max retries value")));
    }

    @Bean("etherscanValidateBody")
    public RequestBodyValidatorImpl etherscanValidateBody(){
        return new RequestBodyValidatorImpl(validateFieldName, validationValue);
    }
}
