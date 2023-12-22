package pl.kargolek.walletservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.experimental.Accessors;
import pl.kargolek.walletservice.mapper.DeserializerWalletBalance;

import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
@JsonDeserialize(using = DeserializerWalletBalance.class)
public class WalletMultiBalance {
    private String status;
    private String message;
    private List<WalletBalance> result;
}
