package pl.kargolek.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */
@Getter
@ToString
@Builder
public class CryptocurrencyTableDTO {

    private Long id;
    private String name;
    private String symbol;
    private Long coinMarketId;
    private String platform;
    private String tokenAddress;
    private Long priceId;
    private BigDecimal priceCurrent;
    private BigDecimal percentChange1h;
    private BigDecimal percentChange24h;
    private BigDecimal percentChange7d;
    private BigDecimal percentChange30d;
    private BigDecimal percentChange60d;
    private BigDecimal percentChange90d;

}
