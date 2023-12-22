package pl.kargolek.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserTotalBalance {
    private BigDecimal totalQuantity;
    private BigDecimal totalBalance;
    private BigDecimal totalBalance1h;
    private BigDecimal totalBalance24h;
    private BigDecimal totalBalance7d;
}
