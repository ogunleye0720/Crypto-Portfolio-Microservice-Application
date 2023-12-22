package pl.kargolek.walletservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
public class TokenDTO {
    private Long id;
    private String name;
    private String symbol;
    private Long coinMarketId;
    private TokenPlatformDTO platform;
    private TokenPriceDTO price;
    private LocalDateTime lastUpdate;
}
