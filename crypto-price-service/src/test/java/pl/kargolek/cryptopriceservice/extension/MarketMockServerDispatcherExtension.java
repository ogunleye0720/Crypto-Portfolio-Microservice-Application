package pl.kargolek.cryptopriceservice.extension;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

/**
 * @author Karol Kuta-Orlowicz
 */
public class MarketMockServerDispatcherExtension extends MarketMockServerExtension {

    private ServerResponses serverResponses;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws IOException {
        super.beforeAll(extensionContext);
        serverResponses = new ServerResponses();
        final Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                return switch (requireNonNull(request.getPath())) {
                    case "/v2/cryptocurrency/quotes/latest?id=1" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200LatestPriceBTC());
                    case "/v2/cryptocurrency/quotes/latest?id=1,1027" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200LatestPriceBTCETH());
                    case "/v2/cryptocurrency/quotes/latest?id=1500" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200LatestPriceNullCryptoData());
                    case "/v2/cryptocurrency/quotes/latest?id=500", "/v1/cryptocurrency/map?symbol=SERVER_500" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(500)
                            .setBody(serverResponses.getBodyRes500ErrorServerNotAvailable());
                    case "/v2/cryptocurrency/quotes/latest?id=401", "/v1/cryptocurrency/map?symbol=SERVER_401" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(401)
                            .setBody(serverResponses.getBodyRes401Unauthorized());
                    case "/v2/cryptocurrency/quotes/latest?id=400" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(400)
                            .setBody(serverResponses.getBodyRes400LatestPrice());
                    case "/v2/cryptocurrency/quotes/latest?id=1000" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200LatestPriceCryptoDataEmpty());
                    case "/v2/cryptocurrency/quotes/latest?id=2000" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200LatestPriceDataPriceNull());
                    case "/v1/cryptocurrency/map?symbol=BTC" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200MapSymbolBTC());
                    case "/v1/cryptocurrency/map?symbol=ETH" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200MapSymbolETH());
                    case "/v1/cryptocurrency/map?symbol=MATIC" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200MapSymbolMatic());
                    case "/v1/cryptocurrency/map?symbol=XEN" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(200)
                            .setBody(serverResponses.getBodyRes200MapSymbolXen());
                    case "/v1/cryptocurrency/map?symbol=UNKNOWN" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(400)
                            .setBody(serverResponses.getBodyRes400MapSymbolUnknown());
                    case "/v1/cryptocurrency/map?symbol=" -> new MockResponse()
                            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setResponseCode(400)
                            .setBody(serverResponses.getBodyRes400MapSymbolEmptyNoAllowed());
                    default -> new MockResponse().setResponseCode(404);
                };
            }
        };
        mockWebServer.setDispatcher(dispatcher);
    }
}