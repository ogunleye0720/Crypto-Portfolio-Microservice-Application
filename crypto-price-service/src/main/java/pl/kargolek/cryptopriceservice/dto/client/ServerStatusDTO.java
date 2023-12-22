package pl.kargolek.cryptopriceservice.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerStatusDTO {

    @JsonProperty("error_message")
    private String message;
}
