package pl.kargolek.cryptopriceservice.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceQuoteDTO {

    @JsonProperty("price")
    private BigDecimal priceCurrent;

    @JsonProperty("percent_change_1h")
    private BigDecimal percentChange1h;

    @JsonProperty("percent_change_24h")
    private BigDecimal percentChange24h;

    @JsonProperty("percent_change_7d")
    private BigDecimal percentChange7d;

    @JsonProperty("percent_change_30d")
    private BigDecimal percentChange30d;

    @JsonProperty("percent_change_60d")
    private BigDecimal percentChange60d;

    @JsonProperty("percent_change_90d")
    private BigDecimal percentChange90d;

}
