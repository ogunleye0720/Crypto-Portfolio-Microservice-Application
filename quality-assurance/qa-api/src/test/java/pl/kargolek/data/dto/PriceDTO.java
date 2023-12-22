package pl.kargolek.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Karol Kuta-Orlowicz
 */
@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("priceCurrent")
    private BigDecimal priceCurrent;

    @JsonProperty("percentChange1h")
    private BigDecimal percentChange1h;

    @JsonProperty("percentChange24h")
    private BigDecimal percentChange24h;

    @JsonProperty("percentChange7d")
    private BigDecimal percentChange7d;

    @JsonProperty("percentChange30d")
    private BigDecimal percentChange30d;

    @JsonProperty("percentChange60d")
    private BigDecimal percentChange60d;

    @JsonProperty("percentChange90d")
    private BigDecimal percentChange90d;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

}
