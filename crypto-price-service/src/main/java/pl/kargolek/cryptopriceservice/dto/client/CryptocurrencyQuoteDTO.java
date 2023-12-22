package pl.kargolek.cryptopriceservice.dto.client;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptocurrencyQuoteDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("id")
    private Long coinMarketId;

    @JsonProperty("quote")
    private Map<String, PriceQuoteDTO> quote = new HashMap<>();

    public PriceQuoteDTO getPriceResponseDTO() {
        return this.getQuote().get("USD");
    }
}
