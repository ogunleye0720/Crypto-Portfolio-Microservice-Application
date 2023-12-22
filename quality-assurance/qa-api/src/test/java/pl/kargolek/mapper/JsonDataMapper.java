package pl.kargolek.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.kargolek.data.dto.CryptocurrencyDTO;
import pl.kargolek.data.dto.WalletDTO;

/**
 * @author Karol Kuta-Orlowicz
 */
public class JsonDataMapper {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());


    public static CryptocurrencyDTO mapCryptocurrencyDtoJsonData(String json) {
        try {
            return mapper.readValue(json, CryptocurrencyDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Unable to map json data from response:%s", json));
        }
    }

    public static CryptocurrencyDTO[] mapCryptocurrencyDtoArrayJsonData(String json) {
        try {
            return mapper.readValue(json, CryptocurrencyDTO[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Unable to map json data from response:%s", json));
        }
    }

    public static WalletDTO mapWalletDtoJsonData(String json) {
        try {
            return mapper.readValue(json, WalletDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Unable to map json data from response:%s.\nCause: %s", json, e.getMessage()));
        }
    }
}
