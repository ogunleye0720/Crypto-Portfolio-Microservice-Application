package pl.kargolek.walletservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.kargolek.walletservice.dto.TokenDTO;
import pl.kargolek.walletservice.dto.UserWallet;
import pl.kargolek.walletservice.dto.WalletMultiBalance;

/**
 * @author Karol Kuta-Orlowicz
 */
@Mapper(uses = {UserBalanceMapper.class})
public interface UserWalletMapper {

    UserWalletMapper INSTANCE = Mappers.getMapper(UserWalletMapper.class);

    @Mapping(target = "balance", source = "result", qualifiedByName = "toUserBalances")
    UserWallet toUserWallet(WalletMultiBalance walletMultiBalance);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "symbol", source = "symbol")
    UserWallet updateUserWallet(@MappingTarget UserWallet userWallet, TokenDTO tokenDTO);
}
