package pl.kargolek.cryptopriceservice.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
public class PlatformMapDTO {

    @JsonProperty("name")
    private String platformName;

    @JsonProperty("token_address")
    private String tokenAddress;
}