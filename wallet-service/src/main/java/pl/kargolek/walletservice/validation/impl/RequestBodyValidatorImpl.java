package pl.kargolek.walletservice.validation.impl;

import lombok.extern.slf4j.Slf4j;
import pl.kargolek.walletservice.exception.ResponseFieldException;
import pl.kargolek.walletservice.validation.RequestBodyValidator;
import reactor.core.publisher.Mono;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class RequestBodyValidatorImpl implements RequestBodyValidator {

    private final String bodyFieldName;
    private final Object requiredFieldValue;

    public RequestBodyValidatorImpl(String bodyFieldName, Object requiredFieldValue) {
        this.bodyFieldName = bodyFieldName;
        this.requiredFieldValue = requiredFieldValue;
    }

    @Override
    public <T> Mono<T> validateBodyField(T bodyFetched) {
        try {
            var field = bodyFetched.getClass().getDeclaredField(bodyFieldName);
            field.setAccessible(true);
            var declaredField = field.get(bodyFetched);
            if (!declaredField.equals(requiredFieldValue)) {
                log.error("Field: {} is not equal to given required value: {}", declaredField, requiredFieldValue);
                return Mono.error(new ResponseFieldException(bodyFieldName, declaredField.toString()));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(bodyFetched);
    }
}
