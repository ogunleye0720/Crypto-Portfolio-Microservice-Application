package pl.kargolek.cryptopriceservice.extension;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.extension.*;

import java.io.IOException;

/**
 * @author Karol Kuta-Orlowicz
 */
public class MarketMockServerExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    public static MockWebServer mockWebServer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
       mockWebServer.shutdown();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == MockWebServer.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return mockWebServer;
    }
}
