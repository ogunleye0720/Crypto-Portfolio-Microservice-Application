package pl.kargolek.extension.visual;

import org.junit.jupiter.api.extension.*;
import pl.kargolek.util.VisualCompareService;

/**
 * @author Karol Kuta-Orlowicz
 */
public class VisualCompareExtension implements BeforeAllCallback, ParameterResolver {

    private VisualCompareService service;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        this.service = VisualCompareService
                .builder()
                .build();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(VisualCompareService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return this.service;
    }
}
