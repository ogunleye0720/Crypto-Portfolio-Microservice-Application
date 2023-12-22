package pl.kargolek.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Karol Kuta-Orlowicz
 */
@Builder
@Getter
@ToString
public class Price {
    private Long id;
    private Long cryptocurrencyId;
    private BigDecimal priceCurrent;
    private BigDecimal percentChange1h;
    private BigDecimal percentChange24h;
    private BigDecimal percentChange7d;
    private BigDecimal percentChange30d;
    private BigDecimal percentChange60d;
    private BigDecimal percentChange90d;
    private LocalDateTime lastUpdate;
}
