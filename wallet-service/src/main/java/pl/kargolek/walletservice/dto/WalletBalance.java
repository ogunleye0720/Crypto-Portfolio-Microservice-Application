package pl.kargolek.walletservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
public class WalletBalance {
    private String account;

    @JsonProperty("balance")
    private BigDecimal quantity;
}
