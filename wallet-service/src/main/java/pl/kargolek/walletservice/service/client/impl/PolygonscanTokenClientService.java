package pl.kargolek.walletservice.service.client.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kargolek.walletservice.validation.RequestBodyValidator;
import reactor.util.retry.RetryBackoffSpec;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service("polygonscanTokenClientService")
public class PolygonscanTokenClientService extends EtherscanTokenClientService {

    public PolygonscanTokenClientService(@Qualifier("polygonscanWebClient") WebClient webClient,
                                         @Qualifier("etherscanRetrySpec") RetryBackoffSpec etherscanRetrySpec,
                                         @Qualifier("etherscanValidateBody") RequestBodyValidator requestBodyValidator) {
        super(webClient, etherscanRetrySpec, requestBodyValidator);
    }
}
