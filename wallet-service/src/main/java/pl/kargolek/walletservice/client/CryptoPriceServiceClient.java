package pl.kargolek.walletservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kargolek.walletservice.client.fallback.CryptoPriceServiceFallbackFactory;
import pl.kargolek.walletservice.dto.TokenDTO;

import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@FeignClient(name = "crypto-price-service", fallbackFactory = CryptoPriceServiceFallbackFactory.class)
public interface CryptoPriceServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "api/v1/cryptocurrency")
    List<TokenDTO> getTokens();

    @RequestMapping(method = RequestMethod.GET, value = "api/v1/cryptocurrency")
    List<TokenDTO> getTokensByName(@RequestParam("name") List<String> name);


}
