package pl.kargolek.cryptopriceservice.service;

import java.net.URI;
import java.util.Optional;

/**
 * @author Karol Kuta-Orlowicz
 */
public interface MarketApiClient {
   <T> Optional<T> getRequest(URI uri, Class<T> map);
}
