package pl.kargolek.walletservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.kargolek.walletservice.dto.UserBalance;
import pl.kargolek.walletservice.dto.WalletBalance;

import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Mapper
public interface UserBalanceMapper {

    UserBalanceMapper INSTANCE = Mappers.getMapper(UserBalanceMapper.class);

    @Mapping(target = "walletAddress", source = "account")
    @Mapping(target = "quantity", source = "quantity")
    UserBalance toUserBalance(WalletBalance walletBalance);

    @Named("toUserBalances")
    List<UserBalance> toUserBalances(List<WalletBalance> walletBalances);

}
