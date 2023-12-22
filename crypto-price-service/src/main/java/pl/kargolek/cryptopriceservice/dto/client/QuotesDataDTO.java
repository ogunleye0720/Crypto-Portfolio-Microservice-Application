package pl.kargolek.cryptopriceservice.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class QuotesDataDTO {

    @JsonProperty("data")
    private Map<String, CryptocurrencyQuoteDTO> data = new HashMap<>();

}
