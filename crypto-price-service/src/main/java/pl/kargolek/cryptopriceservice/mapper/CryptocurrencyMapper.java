package pl.kargolek.cryptopriceservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.kargolek.cryptopriceservice.dto.client.CryptocurrencyQuoteDTO;
import pl.kargolek.cryptopriceservice.dto.client.PriceQuoteDTO;
import pl.kargolek.cryptopriceservice.dto.controller.CryptocurrencyPostDTO;
import pl.kargolek.cryptopriceservice.dto.model.CryptocurrencyDTO;
import pl.kargolek.cryptopriceservice.dto.model.PriceDTO;
import pl.kargolek.cryptopriceservice.mapper.util.MappingUtil;
import pl.kargolek.cryptopriceservice.model.Cryptocurrency;
import pl.kargolek.cryptopriceservice.model.Price;

/**
 * @author Karol Kuta-Orlowicz
 */

@Mapper(uses = MappingUtil.class)
public interface CryptocurrencyMapper {

    CryptocurrencyMapper INSTANCE = Mappers.getMapper(CryptocurrencyMapper.class);

    @Mapping(target = "cryptocurrency.id", source = "cryptocurrencyId")
    Price convertDtoEntity(PriceDTO priceDTO);

    @Mapping(target = "cryptocurrencyId", source = "cryptocurrency.id")
    PriceDTO convertEntityDto(Price price);

    @Mapping(target = "price", source = "priceDTO")
    @Mapping(target = "platform", source = "platformDTO.platform")
    @Mapping(target = "tokenAddress", source = "platformDTO.tokenAddress")
    Cryptocurrency convertDtoEntity(CryptocurrencyDTO cryptocurrencyDTO);

    Cryptocurrency convertPostEntity(CryptocurrencyPostDTO cryptocurrencyPostDTO);

    @Mapping(target = "priceDTO", source = "price")
    @Mapping(target = "platformDTO.platform", source = "platform")
    @Mapping(target = "platformDTO.tokenAddress", source = "tokenAddress")
    CryptocurrencyDTO convertEntityDto(Cryptocurrency cryptocurrency);

    @Mapping(target = "id", ignore = true)
    PriceDTO updateDtoByPriceQuoteDto(@MappingTarget PriceDTO priceDTO, PriceQuoteDTO priceQuoteDTO);

    @Mapping(target = "priceDTO", source = "quote", qualifiedBy = MappingUtil.PriceMap.class)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "symbol", ignore = true)
    @Mapping(target = "coinMarketId", ignore = true)
    CryptocurrencyDTO updateDtoByCryptocurrencyQuoteDto(@MappingTarget CryptocurrencyDTO cryptocurrencyDTO,
                                                        CryptocurrencyQuoteDTO cryptocurrencyQuoteDTO);
}