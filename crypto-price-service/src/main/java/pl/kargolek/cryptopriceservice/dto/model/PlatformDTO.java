package pl.kargolek.cryptopriceservice.dto.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
public class PlatformDTO {

    private String platform;
    private String tokenAddress;

}