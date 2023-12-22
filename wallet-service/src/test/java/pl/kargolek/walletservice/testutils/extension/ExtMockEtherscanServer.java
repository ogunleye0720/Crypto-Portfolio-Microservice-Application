package pl.kargolek.walletservice.testutils.extension;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.extension.*;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ExtMockEtherscanServer implements BeforeAllCallback, AfterAllCallback{

    public static MockWebServer etherscanMockWebServer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        etherscanMockWebServer = new MockWebServer();
        etherscanMockWebServer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        etherscanMockWebServer.close();
    }

}
