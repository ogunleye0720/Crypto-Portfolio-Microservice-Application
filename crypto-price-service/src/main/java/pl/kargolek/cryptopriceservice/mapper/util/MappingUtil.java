package pl.kargolek.cryptopriceservice.mapper.util;

import org.mapstruct.Qualifier;
import pl.kargolek.cryptopriceservice.dto.client.PriceQuoteDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * @author Karol Kuta-Orlowicz
 */
public class MappingUtil {
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface PriceMap {
    }

    @PriceMap
    public PriceQuoteDTO getPriceResponseDTO(Map<String, PriceQuoteDTO> quote) {
        return quote.get("USD");
    }
}

