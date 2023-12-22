package pl.kargolek.walletservice.validation;

import reactor.core.publisher.Mono;

/**
 * @author Karol Kuta-Orlowicz
 */
public interface RequestBodyValidator {

    <T> Mono<T> validateBodyField(T bodyWrapped);
}
