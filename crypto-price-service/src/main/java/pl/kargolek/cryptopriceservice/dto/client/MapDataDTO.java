package pl.kargolek.cryptopriceservice.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapDataDTO {

    @JsonProperty("data")
    private List<CryptocurrencyMapDTO> data = new ArrayList<>();

}
