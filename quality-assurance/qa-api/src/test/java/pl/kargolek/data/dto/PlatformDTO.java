package pl.kargolek.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Karol Kuta-Orlowicz
 */
@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformDTO {

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("tokenAddress")
    private String tokenAddress;
}
