package pl.kargolek.walletservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.dto.WalletMultiBalance;
import pl.kargolek.walletservice.testutils.fixture.ResponseEtherscanData;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("UnitTest")
class DeserializerWalletBalanceTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenResponseIsAsExpected_thenObjectDeserializedProperly() throws JsonProcessingException {
        var expected = mapper.readValue(ResponseEtherscanData.ETHERSCAN_MULTI_WALLET_RES_200_AS_EXPECTED,
                WalletMultiBalance.class);

        assertThat(expected).extracting(
                WalletMultiBalance::getStatus,
                WalletMultiBalance::getMessage
        ).allMatch(Objects::nonNull);

        assertThat(expected.getResult()).isNotNull();
    }

    @Test
    void whenResponseResultEmptyArray_thenObjectResultFieldEmpty() throws JsonProcessingException {
        var expected = mapper.readValue(ResponseEtherscanData.ETHERSCAN_MULTI_WALLET_RES_200_RESULT_EMPTY_ARRAY,
                WalletMultiBalance.class);

        assertThat(expected.getResult()).isEmpty();
    }

    @Test
    void whenResponseResultString_thenObjectResultIsNull() throws JsonProcessingException {
        var expected = mapper.readValue(ResponseEtherscanData.ETHERSCAN_MULTI_WALLET_RES_200_RESULT_AS_STRING,
                WalletMultiBalance.class);

        assertThat(expected.getResult()).isNull();
    }

    @Test
    void whenResponseResultIsNull_thenObjectResultIsNull() throws JsonProcessingException {
        var expected = mapper.readValue(ResponseEtherscanData.ETHERSCAN_MULTI_WALLET_RES_200_RESULT_IS_NULL,
                WalletMultiBalance.class);

        assertThat(expected.getResult()).isNull();
    }
}