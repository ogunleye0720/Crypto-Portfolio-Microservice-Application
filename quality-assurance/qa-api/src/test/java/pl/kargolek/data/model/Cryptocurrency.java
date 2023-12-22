package pl.kargolek.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Karol Kuta-Orlowicz
 */
@Builder
@Getter
@ToString
public class Cryptocurrency {
    private Long id;
    private String name;
    private String symbol;
    private Long coinMarketId;
    private String platform;
    private String tokenAddress;
    private LocalDateTime lastUpdate;
}
