package pl.kargolek.validation;

import io.cucumber.datatable.DataTable;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.groups.Tuple;
import pl.kargolek.data.TestData;
import pl.kargolek.data.dto.*;
import pl.kargolek.mapper.DataTableMapper;
import pl.kargolek.mapper.JsonDataMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author Karol Kuta-Orlowicz
 */
public class DefaultAssertion {

    private static final TestData data = new TestData();

    public static void assertJsonDataCryptoPriceService(String jsonData,
                                                        String jsonDataName,
                                                        CryptocurrencyTableDTO tokenTableData) {
        var jsonCryptocurrencyDTO = JsonDataMapper.mapCryptocurrencyDtoJsonData(jsonData);
        switch (jsonDataName.toLowerCase()) {
            case "ethereum-default" -> {
                assertEthereumDefault(jsonCryptocurrencyDTO);
                assertPriceDataDefault(jsonCryptocurrencyDTO);
            }
            case "token-table" -> {
                if (tokenTableData.getId() == -1 || tokenTableData.getPriceId() == -1) {
                    assertTokenTableNoIds(jsonCryptocurrencyDTO, tokenTableData);
                } else
                    assertTokenTable(jsonCryptocurrencyDTO, tokenTableData);
            }
            default -> throw new RuntimeException(
                    String.format("Unable to find provided %s json data name to make assertion", jsonData));
        }
    }

    public static void assertArrayJsonDataCryptoPriceService(String response,
                                                             String jsonDataName,
                                                             List<CryptocurrencyTableDTO> tableData) {
        var cryptocurrencyDTOS = JsonDataMapper.mapCryptocurrencyDtoArrayJsonData(response);
        switch (jsonDataName.toLowerCase()) {
            case "tokens-table" -> assertArrayTokenTable(cryptocurrencyDTOS, tableData);
            default -> throw new RuntimeException(
                    String.format("Unable to find provided %s json data name to make assertion", response));
        }
    }

    public static void assertJsonWalletServiceNameSymbol(String response, DataTable table) {
        var wallet = JsonDataMapper.mapWalletDtoJsonData(response);
        var mappedTable = table.asMaps(String.class, String.class).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No data in table symbol name"));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(wallet.getName()).isEqualTo(mappedTable.get("name"));
        softAssertions.assertThat(wallet.getSymbol()).isEqualTo(mappedTable.get("symbol"));
        softAssertions.assertAll();
    }

    public static void assertJsonWalletServiceTotalQuantity(String response, DataTable table) {
        var wallet = JsonDataMapper.mapWalletDtoJsonData(response);
        var tableTotalDto = DataTableMapper.mapTotalDto(table.asMaps());

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(wallet.getTotal().getTotalQuantity())
                .isEqualTo(tableTotalDto.getTotalQuantity());
        softAssertions.assertThat(wallet.getTotal().getTotalBalance())
                .isEqualTo(tableTotalDto.getTotalBalance());
        softAssertions.assertThat(wallet.getTotal().getTotalBalance1h())
                .isEqualTo(tableTotalDto.getTotalBalance1h());
        softAssertions.assertThat(wallet.getTotal().getTotalBalance24h())
                .isEqualTo(tableTotalDto.getTotalBalance24h());
        softAssertions.assertThat(wallet.getTotal().getTotalBalance7d())
                .isEqualTo(tableTotalDto.getTotalBalance7d());
        softAssertions.assertAll();
    }

    public static void assertJsonWalletServiceBalances(String response, DataTable table) {
        var wallet = JsonDataMapper.mapWalletDtoJsonData(response);
        var balances = DataTableMapper.mapBalanceDto(table.asMaps());
        var tupleBalances = balances.stream()
                .map(balance -> tuple(
                        balance.getWalletAddress(),
                        balance.getQuantity(),
                        balance.getBalance(),
                        balance.getBalance1h(),
                        balance.getBalance24h(),
                        balance.getBalance7d(),
                        balance.getBalance30d(),
                        balance.getBalance60d(),
                        balance.getBalance90d(),
                        balance.getWalletExplorer()
                ))
                .toList();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(wallet.getBalance())
                .extracting(
                        BalanceDTO::getWalletAddress,
                        BalanceDTO::getQuantity,
                        BalanceDTO::getBalance,
                        BalanceDTO::getBalance1h,
                        BalanceDTO::getBalance24h,
                        BalanceDTO::getBalance7d,
                        BalanceDTO::getBalance30d,
                        BalanceDTO::getBalance60d,
                        BalanceDTO::getBalance90d,
                        BalanceDTO::getWalletExplorer
                ).containsExactlyElementsOf(tupleBalances);
        softAssertions.assertAll();
    }

    private static void assertEthereumDefault(CryptocurrencyDTO cryptocurrencyDTO) {
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(cryptocurrencyDTO.getId())
                .isPositive();

        assertions.assertThat(cryptocurrencyDTO.getName())
                .isEqualTo(data.getEthereum().getName());

        assertions.assertThat(cryptocurrencyDTO.getSymbol())
                .isEqualTo(data.getEthereum().getSymbol());

        assertions.assertThat(cryptocurrencyDTO.getCoinMarketId())
                .isEqualTo(data.getEthereum().getCoinMarketId());

        assertions.assertThat(cryptocurrencyDTO.getPlatform().getPlatform())
                .isEqualTo(data.getEthereum().getPlatform());

        assertions.assertThat(cryptocurrencyDTO.getPlatform().getTokenAddress())
                .isEqualTo(data.getEthereum().getTokenAddress());

        assertions.assertThat(cryptocurrencyDTO.getLastUpdate())
                .isBeforeOrEqualTo(data.getEthereum().getLastUpdate());

        assertions.assertAll();
    }

    private static void assertPriceDataDefault(CryptocurrencyDTO cryptocurrencyDTO) {
        var priceDTO = cryptocurrencyDTO.getPrice();

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(priceDTO.getId())
                .isPositive();

        assertions.assertThat(priceDTO.getPriceCurrent())
                .isNull();

        assertions.assertThat(priceDTO.getPercentChange1h())
                .isNull();

        assertions.assertThat(priceDTO.getPercentChange24h())
                .isNull();

        assertions.assertThat(priceDTO.getPercentChange7d())
                .isNull();

        assertions.assertThat(priceDTO.getPercentChange30d())
                .isNull();

        assertions.assertThat(priceDTO.getPercentChange60d())
                .isNull();

        assertions.assertThat(priceDTO.getPercentChange90d())
                .isNull();

        assertions.assertThat(priceDTO.getLastUpdate())
                .isBefore(LocalDateTime.now(ZoneOffset.UTC));

        assertions.assertAll();
    }

    private static void assertArrayTokenTable(CryptocurrencyDTO[] cryptocurrencyDTOS,
                                              List<CryptocurrencyTableDTO> tableData) {

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(cryptocurrencyDTOS)
                .extracting(
                        CryptocurrencyDTO::getId,
                        CryptocurrencyDTO::getName,
                        CryptocurrencyDTO::getSymbol,
                        CryptocurrencyDTO::getCoinMarketId
                ).containsExactlyElementsOf(
                        mapCryptocurrencyInfoTableData(tableData)
                );

        assertions.assertThat(cryptocurrencyDTOS)
                .extracting(
                        CryptocurrencyDTO::getPlatform
                ).extracting(
                        PlatformDTO::getPlatform,
                        PlatformDTO::getTokenAddress
                ).containsExactlyElementsOf(
                        mapCryptocurrencyPlatformTableData(tableData)
                );

        assertions.assertThat(cryptocurrencyDTOS)
                .extracting(
                        CryptocurrencyDTO::getPrice
                ).extracting(
                        PriceDTO::getId,
                        PriceDTO::getPriceCurrent,
                        PriceDTO::getPercentChange1h,
                        PriceDTO::getPercentChange24h,
                        PriceDTO::getPercentChange7d,
                        PriceDTO::getPercentChange30d,
                        PriceDTO::getPercentChange60d,
                        PriceDTO::getPercentChange90d
                ).containsExactlyElementsOf(
                        mapCryptocurrencyPriceTableData(tableData)
                );

        assertions.assertAll();
    }

    private static List<Tuple> mapCryptocurrencyInfoTableData(List<CryptocurrencyTableDTO> tableDTOS) {
        return tableDTOS.stream()
                .map(item -> tuple(
                        item.getId(),
                        item.getName(),
                        item.getSymbol(),
                        item.getCoinMarketId()
                )).collect(Collectors.toList());
    }

    private static List<Tuple> mapCryptocurrencyPlatformTableData(List<CryptocurrencyTableDTO> tableDTOS) {
        return tableDTOS.stream()
                .map(item -> tuple(
                        item.getPlatform(),
                        item.getTokenAddress()
                )).collect(Collectors.toList());
    }

    private static List<Tuple> mapCryptocurrencyPriceTableData(List<CryptocurrencyTableDTO> tableDTOS) {
        return tableDTOS.stream()
                .map(item -> tuple(
                        item.getPriceId(),
                        item.getPriceCurrent(),
                        item.getPercentChange1h(),
                        item.getPercentChange24h(),
                        item.getPercentChange7d(),
                        item.getPercentChange30d(),
                        item.getPercentChange60d(),
                        item.getPercentChange90d()
                )).collect(Collectors.toList());
    }

    private static void assertTokenTable(CryptocurrencyDTO cryptocurrencyDTO,
                                         CryptocurrencyTableDTO cryptocurrencyTableDTO) {
        SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(cryptocurrencyDTO)
                .extracting(
                        CryptocurrencyDTO::getId,
                        CryptocurrencyDTO::getName,
                        CryptocurrencyDTO::getSymbol,
                        CryptocurrencyDTO::getCoinMarketId
                ).containsExactly(
                        cryptocurrencyTableDTO.getId(),
                        cryptocurrencyTableDTO.getName(),
                        cryptocurrencyTableDTO.getSymbol(),
                        cryptocurrencyTableDTO.getCoinMarketId()
                );

        assertions.assertThat(cryptocurrencyDTO)
                .extracting(
                        CryptocurrencyDTO::getPlatform
                ).extracting(
                        PlatformDTO::getPlatform,
                        PlatformDTO::getTokenAddress
                ).containsExactly(
                        cryptocurrencyTableDTO.getPlatform(),
                        cryptocurrencyTableDTO.getTokenAddress()
                );

        assertions.assertThat(cryptocurrencyDTO)
                .extracting(
                        CryptocurrencyDTO::getPrice
                ).extracting(
                        PriceDTO::getId,
                        PriceDTO::getPriceCurrent,
                        PriceDTO::getPercentChange1h,
                        PriceDTO::getPercentChange24h,
                        PriceDTO::getPercentChange7d,
                        PriceDTO::getPercentChange30d,
                        PriceDTO::getPercentChange60d,
                        PriceDTO::getPercentChange90d
                ).containsExactly(
                        cryptocurrencyTableDTO.getPriceId(),
                        cryptocurrencyTableDTO.getPriceCurrent(),
                        cryptocurrencyTableDTO.getPercentChange1h(),
                        cryptocurrencyTableDTO.getPercentChange24h(),
                        cryptocurrencyTableDTO.getPercentChange7d(),
                        cryptocurrencyTableDTO.getPercentChange30d(),
                        cryptocurrencyTableDTO.getPercentChange60d(),
                        cryptocurrencyTableDTO.getPercentChange90d()
                );

        assertions.assertAll();

    }

    private static void assertTokenTableNoIds(CryptocurrencyDTO cryptocurrencyDTO,
                                              CryptocurrencyTableDTO cryptocurrencyTableDTO) {
        SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(cryptocurrencyDTO.getId())
                .isPositive();

        assertions.assertThat(cryptocurrencyDTO)
                .extracting(
                        CryptocurrencyDTO::getName,
                        CryptocurrencyDTO::getSymbol,
                        CryptocurrencyDTO::getCoinMarketId
                ).containsExactly(
                        cryptocurrencyTableDTO.getName(),
                        cryptocurrencyTableDTO.getSymbol(),
                        cryptocurrencyTableDTO.getCoinMarketId()
                );

        assertions.assertThat(cryptocurrencyDTO)
                .extracting(
                        CryptocurrencyDTO::getPlatform
                ).extracting(
                        PlatformDTO::getPlatform,
                        PlatformDTO::getTokenAddress
                ).containsExactly(
                        cryptocurrencyTableDTO.getPlatform(),
                        cryptocurrencyTableDTO.getTokenAddress()
                );

        assertions.assertThat(cryptocurrencyDTO.getPrice().getId())
                .isPositive();

        assertions.assertThat(cryptocurrencyDTO)
                .extracting(
                        CryptocurrencyDTO::getPrice
                ).extracting(
                        PriceDTO::getPriceCurrent,
                        PriceDTO::getPercentChange1h,
                        PriceDTO::getPercentChange24h,
                        PriceDTO::getPercentChange7d,
                        PriceDTO::getPercentChange30d,
                        PriceDTO::getPercentChange60d,
                        PriceDTO::getPercentChange90d
                ).containsExactly(
                        cryptocurrencyTableDTO.getPriceCurrent(),
                        cryptocurrencyTableDTO.getPercentChange1h(),
                        cryptocurrencyTableDTO.getPercentChange24h(),
                        cryptocurrencyTableDTO.getPercentChange7d(),
                        cryptocurrencyTableDTO.getPercentChange30d(),
                        cryptocurrencyTableDTO.getPercentChange60d(),
                        cryptocurrencyTableDTO.getPercentChange90d()
                );

        assertions.assertAll();
    }
}
