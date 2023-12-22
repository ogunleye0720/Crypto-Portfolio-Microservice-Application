package pl.kargolek.walletservice.testutils.fixture;

import okhttp3.mockwebserver.MockResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ResponseEtherscanService {

    public MockResponse getMockedResStatus200() {
        return new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(ResponseEtherscanData.ETHERSCAN_MULTI_WALLET_RES_200_AS_EXPECTED);
    }

    public MockResponse getMockedResStatus200Valid20Addresses() {
        return new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(ResponseEtherscanData.ETHERSCAN_20_MULTI_WALLET_RES_200_AS_EXPECTED);
    }

    public MockResponse getMockedResStatus200ResultEmptyArray() {
        return new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(ResponseEtherscanData.ETHERSCAN_MULTI_WALLET_RES_200_RESULT_EMPTY_ARRAY);
    }

    public MockResponse getMockedResStatus200MaxLimitReached() {
        return new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(ResponseEtherscanData.ETHERSCAN_RES_200_MAX_LIMIT_REACHED);
    }

    public MockResponse getMockedResStatus500() {
        return new MockResponse()
                .setResponseCode(500)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }

    public MockResponse getMockedResStatus400() {
        return new MockResponse()
                .setResponseCode(400)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }

}
