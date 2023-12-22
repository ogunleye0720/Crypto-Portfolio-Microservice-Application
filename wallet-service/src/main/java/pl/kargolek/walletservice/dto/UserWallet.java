package pl.kargolek.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserWallet {
    private String name;
    private String symbol;
    private UserTotalBalance total;
    private List<UserBalance> balance;
}
