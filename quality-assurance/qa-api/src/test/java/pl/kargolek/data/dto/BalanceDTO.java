package pl.kargolek.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {
    private String walletAddress;
    private BigDecimal quantity;
    private BigDecimal balance;
    private BigDecimal balance1h;
    private BigDecimal balance24h;
    private BigDecimal balance7d;
    private BigDecimal balance30d;
    private BigDecimal balance60d;
    private BigDecimal balance90d;
    private String walletExplorer;
}
