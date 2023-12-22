package pl.kargolek.walletservice.util;

import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Karol Kuta-Orlowicz
 */
@Component
public class WalletDataResolver {

    public Stream<List<String>> splitWalletsData(String wallets, int maxWalletsPerRequest) {
        var walletsList = Arrays.asList(wallets.trim().split(","));
        return Streams.stream(Iterables.partition(walletsList, maxWalletsPerRequest))
                .map(mapped -> List.of(String.join(",", mapped)));
    }
}
