package pl.kargolek.walletservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
public class UserBalance {
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
