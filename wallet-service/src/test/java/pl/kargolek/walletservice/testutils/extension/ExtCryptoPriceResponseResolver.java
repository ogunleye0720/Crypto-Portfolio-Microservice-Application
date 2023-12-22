package pl.kargolek.walletservice.testutils.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import pl.kargolek.walletservice.testutils.fixture.ResponseCryptoPriceService;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ExtCryptoPriceResponseResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == ResponseCryptoPriceService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new ResponseCryptoPriceService();
    }
}
