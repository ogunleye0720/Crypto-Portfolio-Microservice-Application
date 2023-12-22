package pl.kargolek.walletservice.service.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kargolek.walletservice.client.ExternalRequestClient;
import pl.kargolek.walletservice.dto.WalletMultiBalance;
import pl.kargolek.walletservice.service.client.TokenClientService;
import pl.kargolek.walletservice.validation.RequestBodyValidator;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.RetryBackoffSpec;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service("etherscanTokenClientService")
public class EtherscanTokenClientService implements TokenClientService {
    @Autowired
    private ExternalRequestClient externalRequestClient;
    private final WebClient webClient;
    private final RetryBackoffSpec retryBackoffSpec;
    private final RequestBodyValidator requestBodyValidator;

    public EtherscanTokenClientService(@Qualifier("etherscanWebClient") WebClient webClient,
                                       @Qualifier("etherscanRetrySpec") RetryBackoffSpec etherscanRetrySpec,
                                       @Qualifier("etherscanValidateBody") RequestBodyValidator requestBodyValidator) {
        this.webClient = webClient;
        this.retryBackoffSpec = etherscanRetrySpec;
        this.requestBodyValidator = requestBodyValidator;
    }

    @Override
    public WalletMultiBalance getWalletMultiBalance(String wallets) {
        var scheduler = Schedulers.boundedElastic();
        return this.createMultiBalanceRequest(wallets, WalletMultiBalance.class)
                .subscribeOn(scheduler)
                .block();
    }

    private <T> Mono<T> createMultiBalanceRequest(String wallets, Class<T> clazz) {
        return this.externalRequestClient.getRequest(
                this.webClient,
                this.setupUriComponents(wallets),
                clazz,
                this.requestBodyValidator,
                this.retryBackoffSpec
        );
    }

    private UriComponents setupUriComponents(String wallets) {
        return UriComponentsBuilder.newInstance()
                .queryParam("module", "account")
                .queryParam("action", "balancemulti")
                .queryParam("address", wallets)
                .queryParam("apiKey", "{apiKey}")
                .build();
    }
}
