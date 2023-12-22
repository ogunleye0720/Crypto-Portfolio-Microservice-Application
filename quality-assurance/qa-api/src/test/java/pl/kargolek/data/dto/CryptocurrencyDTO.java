package pl.kargolek.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Karol Kuta-Orlowicz
 */
@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptocurrencyDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("coinMarketId")
    private Long coinMarketId;

    @JsonProperty("platform")
    private PlatformDTO platform;

    @JsonProperty("price")
    private PriceDTO price;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonProperty("example")
    private LocalDateTime example;

}
