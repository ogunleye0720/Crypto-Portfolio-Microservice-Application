package pl.kargolek.mapper;

import pl.kargolek.data.dto.BalanceDTO;
import pl.kargolek.data.dto.CryptocurrencyTableDTO;
import pl.kargolek.data.dto.TotalDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Karol Kuta-Orlowicz
 */
public class DataTableMapper {

    public static List<CryptocurrencyTableDTO> mapCryptocurrencyTableDto(List<Map<String, String>> entries) {
        return entries.stream()
                .map(DataTableMapper::mapTableToCryptocurrencyDto)
                .toList();
    }

    public static TotalDTO mapTotalDto(List<Map<String, String>> entries) {
        return entries.stream()
                .map(DataTableMapper::mapTableToTotalDto)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to map total dto"));
    }

    public static List<BalanceDTO> mapBalanceDto(List<Map<String, String>> entries){
        return entries.stream()
                .map(DataTableMapper::mapTableToBalanceDto)
                .collect(Collectors.toList());
    }

    private static CryptocurrencyTableDTO mapTableToCryptocurrencyDto(Map<String, String> entry) {
        var cryptocurrencyId = (entry.get("id") == null) ? -1L : Long.parseLong(entry.get("id"));

        var priceId = (entry.get("priceId") == null) ? -1L : Long.parseLong(entry.get("priceId"));

        var priceCurrent = (entry.get("priceCurrent") == null) ? null :
                new BigDecimal(entry.get("priceCurrent")).setScale(12, RoundingMode.HALF_EVEN);

        var percentChange1h = (entry.get("percentChange1h") == null) ? null :
                new BigDecimal(entry.get("percentChange1h")).setScale(12, RoundingMode.HALF_EVEN);

        var percentChange24h = (entry.get("percentChange24h") == null) ? null :
                new BigDecimal(entry.get("percentChange24h")).setScale(12, RoundingMode.HALF_EVEN);

        var percentChange7d = (entry.get("percentChange7d") == null) ? null :
                new BigDecimal(entry.get("percentChange7d")).setScale(12, RoundingMode.HALF_EVEN);

        var percentChange30d = (entry.get("percentChange30d") == null) ? null :
                new BigDecimal(entry.get("percentChange30d")).setScale(12, RoundingMode.HALF_EVEN);

        var percentChange60d = (entry.get("percentChange60d") == null) ? null :
                new BigDecimal(entry.get("percentChange60d")).setScale(12, RoundingMode.HALF_EVEN);

        var percentChange90d = (entry.get("percentChange90d") == null) ? null :
                new BigDecimal(entry.get("percentChange90d")).setScale(12, RoundingMode.HALF_EVEN);

        return CryptocurrencyTableDTO.builder()
                .id(cryptocurrencyId)
                .name(entry.get("name"))
                .symbol(entry.get("symbol"))
                .coinMarketId(Long.parseLong(entry.get("coinMarketId")))
                .platform(entry.get("platform"))
                .tokenAddress(entry.get("tokenAddress"))
                .priceId(priceId)
                .priceCurrent(priceCurrent)
                .percentChange1h(percentChange1h)
                .percentChange24h(percentChange24h)
                .percentChange7d(percentChange7d)
                .percentChange30d(percentChange30d)
                .percentChange60d(percentChange60d)
                .percentChange90d(percentChange90d)
                .build();
    }

    private static TotalDTO mapTableToTotalDto(Map<String, String> data) {
        return new TotalDTO(
                new BigDecimal(data.get("totalQuantity")),
                new BigDecimal(data.get("totalBalance")),
                new BigDecimal(data.get("totalBalance1h")),
                new BigDecimal(data.get("totalBalance24h")),
                new BigDecimal(data.get("totalBalance7d"))
        );
    }

    private static BalanceDTO mapTableToBalanceDto(Map<String, String> data) {
        return new BalanceDTO(
                data.get("walletAddress"),
                new BigDecimal(data.get("quantity")),
                new BigDecimal(data.get("balance")),
                new BigDecimal(data.get("balance1h")),
                new BigDecimal(data.get("balance24h")),
                new BigDecimal(data.get("balance7d")),
                new BigDecimal(data.get("balance30d")),
                new BigDecimal(data.get("balance60d")),
                new BigDecimal(data.get("balance90d")),
                data.get("walletExplorer")
        );
    }


}
