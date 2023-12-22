package pl.kargolek.walletservice.testutils.fixture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ResponseCryptoPriceService {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final DataCryptocurrency dataCryptocurrency = new DataCryptocurrency();

    public MockResponse getAllCryptocurrenciesHttpStatusOK() throws JsonProcessingException {
        return getMockedResStatus200()
                .setBody(objectMapper.writeValueAsString(dataCryptocurrency.getCryptocurrencies()));
    }

    public MockResponse getAllCryptocurrenciesHttpStatus500() {
        return getMockedResStatus500();
    }

    private MockResponse getMockedResStatus200() {
        return new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }

    private MockResponse getMockedResStatus500() {
        return new MockResponse()
                .setResponseCode(500);
    }
}
