package pl.kargolek.walletservice.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import pl.kargolek.walletservice.client.CryptoPriceServiceClient;
import pl.kargolek.walletservice.dto.TokenDTO;

import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Component
@Slf4j
public class CryptoPriceServiceFallbackFactory implements FallbackFactory<CryptoPriceServiceClient> {

    @Override
    public CryptoPriceServiceClient create(Throwable cause) {
        return new CryptoPriceServiceClient() {
            @Override
            public List<TokenDTO> getTokens() {
                log.error("Error occurred in cryptocurrency price service client, cause:{}", cause.getMessage());
                return List.of();
            }

            @Override
            public List<TokenDTO> getTokensByName(List<String> name) {
                log.error("Error occurred in cryptocurrency price service client, cause:{}", cause.getMessage());
                return List.of();
            }
        };
    }
}
