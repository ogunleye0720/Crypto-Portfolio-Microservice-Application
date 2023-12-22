package pl.kargolek.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletDTO {
    private String name;
    private String symbol;
    private TotalDTO total;
    private List<BalanceDTO> balance;
}
