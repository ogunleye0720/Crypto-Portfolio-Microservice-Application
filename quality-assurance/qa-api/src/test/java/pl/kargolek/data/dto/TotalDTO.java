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
@AllArgsConstructor
@NoArgsConstructor
public class TotalDTO {
    private BigDecimal totalQuantity;
    private BigDecimal totalBalance;
    private BigDecimal totalBalance1h;
    private BigDecimal totalBalance24h;
    private BigDecimal totalBalance7d;
}
