package pl.kargolek.walletservice.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kargolek.walletservice.exception.*;
import pl.kargolek.walletservice.service.balance.polygon.PolygonBalanceService;
import pl.kargolek.walletservice.testutils.BaseParamTest;
import pl.kargolek.walletservice.testutils.fixture.DataEthereumWallets;
import pl.kargolek.walletservice.testutils.fixture.DataUserWallet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Karol Kuta-Orlowicz
 */
@WebMvcTest(PolygonWalletController.class)
@Tag("UnitTest")
class PolygonWalletControllerTest extends BaseParamTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PolygonBalanceService polygonBalanceService;

    private final String basePath = "/api/v1/wallet/matic";

    @Test
    void whenMaticBalanceCalcReturnUserWallet_thenReturn200AndBody(DataUserWallet dataUserWallet, DataEthereumWallets dataEthereumWallets) throws Exception {
        var userWallet = dataUserWallet.getUserWalletOnePolygon();
        var userBalance = userWallet.getBalance().get(0);
        var total = userWallet.getTotal();

        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenReturn(userWallet);

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.name").value(userWallet.getName()))
                .andExpect(jsonPath("$.symbol").value(userWallet.getSymbol()))
                .andExpect(jsonPath("$.balance[0].walletAddress").value(userBalance.getWalletAddress()))
                .andExpect(jsonPath("$.balance[0].quantity").value(userBalance.getQuantity()))
                .andExpect(jsonPath("$.balance[0].balance").value(userBalance.getBalance()))
                .andExpect(jsonPath("$.balance[0].balance1h").value(userBalance.getBalance1h()))
                .andExpect(jsonPath("$.balance[0].balance24h").value(userBalance.getBalance24h()))
                .andExpect(jsonPath("$.balance[0].balance7d").value(userBalance.getBalance7d()))
                .andExpect(jsonPath("$.balance[0].balance30d").value(userBalance.getBalance30d()))
                .andExpect(jsonPath("$.balance[0].balance60d").value(userBalance.getBalance60d()))
                .andExpect(jsonPath("$.balance[0].balance90d").value(userBalance.getBalance90d()))
                .andExpect(jsonPath("$.balance[0].walletExplorer").value(userBalance.getWalletExplorer()))
                .andExpect(jsonPath("$.total.totalQuantity").value(total.getTotalQuantity()))
                .andExpect(jsonPath("$.total.totalBalance").value(total.getTotalBalance()))
                .andExpect(jsonPath("$.total.totalBalance1h").value(total.getTotalBalance()))
                .andExpect(jsonPath("$.total.totalBalance24h").value(total.getTotalBalance()))
                .andExpect(jsonPath("$.total.totalBalance7d").value(total.getTotalBalance()));
    }

    @Test
    void whenMaticBalanceCalcReturnExcInvalidWalletAddress_thenReturn400AndBody(DataEthereumWallets dataEthereumWallets) throws Exception {
        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenThrow(new InvalidAddressException("Polygon", dataEthereumWallets.WALLETS_1_VALID, "Invalid"));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message")
                        .value("Address is invalid for crypto Polygon and address 0x742d35Cc6634C0532925a3b844Bc454e4438f44e, message: Invalid"));
    }

    @Test
    void whenMaticBalanceCalcReturnExcNoSuchCryptoPrice_thenReturn500AndBody(DataEthereumWallets dataEthereumWallets) throws Exception {
        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenThrow(new NoSuchCryptoPriceDataException());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Unable to get price for crypto"));
    }

    @Test
    void whenMaticBalanceCalcReturnResponseFieldExc_thenReturn500AndBody(DataEthereumWallets dataEthereumWallets) throws Exception {
        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenThrow(new ResponseFieldException("status", "1"));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Verification of field status failed, expected: 1"));
    }

    @Test
    void whenMaticBalanceCalcWebClientStatusExc_thenReturn500AndBody(DataEthereumWallets dataEthereumWallets) throws Exception {
        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenThrow(new WebClientStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message")
                        .value("Unexpected status code occurred after call request. Status code: 500"));
    }

    @Test
    void whenMaticBalanceCalcExternalServiceCallExceptionExc_thenReturn500AndBody(DataEthereumWallets dataEthereumWallets) throws Exception {
        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenThrow(new ExternalServiceCallException("Call external service reached out max retries value"));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message")
                        .value("Call external service reached out max retries value"));
    }

    @Test
    void whenMaticBalanceCalcNoSuchCryptocurrencyExc_thenReturn500AndBody(DataEthereumWallets dataEthereumWallets) throws Exception {
        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenThrow(new NoSuchCryptocurrencyException("Unable to make conversion for cryptocurrency for given crypto type: Polygon"));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message")
                        .value("Unable to make conversion for cryptocurrency for given crypto type: Polygon"));
    }

    @Test
    void whenMaticBalanceCalcNoSuchWalletDataExc_thenReturn500AndBody(DataEthereumWallets dataEthereumWallets) throws Exception {
        when(polygonBalanceService.getMultiBalance(dataEthereumWallets.WALLETS_1_VALID))
                .thenThrow(new NoSuchWalletDataException());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/balance")
                        .param("wallets", dataEthereumWallets.WALLETS_1_VALID))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }
}