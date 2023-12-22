package pl.kargolek.walletservice.client;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import pl.kargolek.walletservice.validation.RequestBodyValidator;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

/**
 * @author Karol Kuta-Orlowicz
 */
public interface ExternalRequestClient {

    <T> Mono<T> getRequest(WebClient webClient,
                           UriComponents uriComponents,
                           Class<T> response,
                           RequestBodyValidator requestBodyValidator,
                           RetryBackoffSpec retrySpec);

}
