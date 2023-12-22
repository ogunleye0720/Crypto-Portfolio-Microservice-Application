package pl.kargolek.walletservice.service.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kargolek.walletservice.client.CryptoPriceServiceClient;
import pl.kargolek.walletservice.dto.TokenDTO;
import pl.kargolek.walletservice.exception.NoSuchCryptoPriceDataException;
import pl.kargolek.walletservice.util.CryptoType;

import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Service
public class PriceService {

    @Autowired
    private CryptoPriceServiceClient cryptoPriceServiceClient;

    public TokenDTO getCryptoPrice(CryptoType cryptoType) {
        var name = cryptoType.getName();
        return cryptoPriceServiceClient.getTokensByName(List.of(name)).stream()
                .filter(tokenDTO -> tokenDTO.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchCryptoPriceDataException(name));
    }

}
