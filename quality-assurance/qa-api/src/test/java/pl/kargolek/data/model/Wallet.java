package pl.kargolek.data.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author Karol Kuta-Orlowicz
 */
@Builder
@Getter
public class Wallet {
    private String address;
    private BigDecimal amount;
}
