package pl.kargolek.steps;

import com.google.gson.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import pl.kargolek.data.dto.CryptocurrencyTableDTO;
import pl.kargolek.mapper.DataTableMapper;
import pl.kargolek.utils.TestProperty;
import pl.kargolek.validation.DefaultAssertion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author Karol Kuta-Orlowicz
 */

@Slf4j
public class ServicesSteps {

    private final String baseURL = TestProperty.getInstance().getBaseURL();
    private RequestSpecification request;
    private Response response;
    private JsonObject jsonObject;

    @Before
    public void beforeScenario() {
        jsonObject = new JsonObject();
    }

    @After
    public void afterScenario() {
        log.info("Response:\nStatus code:{}\nData:{}",
                this.response.getStatusCode(),
                this.response.getBody().asString());
        Allure.addAttachment(
                "Response JSON Data",
                "application/json",
                this.response.getBody().asString());
    }

    @Given("valid API endpoint")
    public void validApiEndpoint() {
        RestAssured.baseURI = this.baseURL;
        this.request = RestAssured.given();
    }

    @When("send GET request to path {string}")
    public void sendGetRequestToPath(String path) {
        this.response = this.request.get(path);
    }

    @Then("receive status code {int}")
    public void receiveStatusCode(int statusCode) {
        this.response.then()
                .assertThat()
                .statusCode(statusCode);
    }

    @And("receive empty body")
    public void receiveEmptyBody() {
        this.response.then()
                .assertThat()
                .body(equalTo("[]"));
    }

    @And("receive data is json format")
    public void receiveJsonFormat() {
        this.response.then()
                .assertThat()
                .contentType(ContentType.JSON);
    }

    @And("receive valid json data")
    public void receiveValidJsonData(DataTable table) {
        SoftAssertions assertions = new SoftAssertions();
        table.asMap()
                .forEach((key, value) -> {
                    var jsonValue = this.response.jsonPath().get(key).toString();
                    assertions.assertThat(jsonValue)
                            .isEqualTo(value);
                });
        assertions.assertAll();
    }

    @When("send DELETE request to path {string}")
    public void sendDELETERequestToPath(String path) {
        this.response = this.request.delete(path);
    }

    @When("send PUT json data to path {string}")
    public void sendPUTJsonDataToPath(String path, DataTable table) {
        var dataMap = table.asMap();

        if (jsonObject.get("name") == null) {
            jsonObject.addProperty("name", dataMap.get("name"));
        }

        if (jsonObject.get("symbol") == null) {
            jsonObject.addProperty("symbol", dataMap.get("symbol"));
        }

        if (jsonObject.get("platform") == null) {
            jsonObject.addProperty("platform", dataMap.get("platform"));
        }

        if (jsonObject.get("tokenAddress") == null) {
            jsonObject.addProperty("tokenAddress", dataMap.get("tokenAddress"));
        }

        this.response = this.request.contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .put(path);
    }

    @When("send POST json data to path {string}")
    public void sendPOSTJsonDataToPath(String path, DataTable table) {
        var dataMap = table.asMap();

        if (jsonObject.get("name") == null) {
            jsonObject.addProperty("name", dataMap.get("name"));
        }

        if (jsonObject.get("symbol") == null) {
            jsonObject.addProperty("symbol", dataMap.get("symbol"));
        }

        if (jsonObject.get("platform") == null) {
            jsonObject.addProperty("platform", dataMap.get("platform"));
        }

        if (jsonObject.get("tokenAddress") == null) {
            jsonObject.addProperty("tokenAddress", dataMap.get("tokenAddress"));
        }

        this.response = this.request.contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .post(path);
    }

    @And("receive valid json data for crypto-price {string}")
    public void receiveValidJsonDataForCryptoPrice(String jsonDataName) {
        var response = this.response.body().asString();
        DefaultAssertion.assertJsonDataCryptoPriceService(
                response,
                jsonDataName,
                CryptocurrencyTableDTO.builder().build());
    }

    @And("receive valid array json data for crypto-price")
    public void receiveValidArrayJsonDataForCryptoPrice(List<Map<String, String>> tableData) {
        var response = this.response.body().asString();
        DefaultAssertion.assertArrayJsonDataCryptoPriceService(
                response,
                "tokens-table",
                DataTableMapper.mapCryptocurrencyTableDto(tableData));
    }

    @And("receive valid json data for crypto-price")
    public void receiveValidJsonDataForCryptoPrice(List<Map<String, String>> tableData) {
        var response = this.response.body().asString();
        var tokenDataTable = DataTableMapper.mapCryptocurrencyTableDto(tableData).stream()
                .findFirst()
                .orElse(CryptocurrencyTableDTO.builder().build());
        DefaultAssertion.assertJsonDataCryptoPriceService(
                response,
                "token-table",
                tokenDataTable);
    }

    @When("add payload property {string} as random alphanumeric {int}")
    public void addPayloadPropertyAsRandomAlphanumeric(String property, int length) {
        jsonObject.addProperty(property, RandomStringUtils.randomAlphabetic(length));
    }

    @When("send GET request to path {string} with query params")
    public void sendGETRequestToPathWithQueryParams(String path, DataTable table) {
        var queries = table.asMaps()
                .stream()
                .map(item -> String.format("%s=%s", item.get("query"), item.get("value")))
                .collect(Collectors.joining("&"));
        this.response = this.request.get(String.format("%s?%s", path, queries));
    }

    @And("receive wallet json data name and symbol")
    public void receiveWalletJsonDataNameAndSymbol(DataTable table) {
        var response = this.response.body().asString();
        DefaultAssertion.assertJsonWalletServiceNameSymbol(response, table);
    }

    @And("receive wallets json data totals quantity")
    public void receiveWalletsJsonDataTotalsQuantity(DataTable table) {
        var response = this.response.body().asString();
        DefaultAssertion.assertJsonWalletServiceTotalQuantity(response, table);
    }

    @And("receive wallets json data balances")
    public void receiveWalletsJsonDataBalances(DataTable table) {
        var response = this.response.body().asString();
        DefaultAssertion.assertJsonWalletServiceBalances(response, table);
    }
}
