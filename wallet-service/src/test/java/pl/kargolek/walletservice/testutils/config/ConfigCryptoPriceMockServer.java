package pl.kargolek.walletservice.testutils.config;

import okhttp3.mockwebserver.MockWebServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.ServiceInstanceListSuppliers;
import org.springframework.context.annotation.Bean;

/**
 * @author Karol Kuta-Orlowicz
 */
@TestConfiguration
public class ConfigCryptoPriceMockServer {

    @Autowired
    private MockWebServer mockWebServer;

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return ServiceInstanceListSuppliers.from("server",
                new DefaultServiceInstance(
                        "instance_1",
                        "crypto-price-service",
                        mockWebServer.getHostName(),
                        mockWebServer.getPort(),
                        false
                ));
    }
}
